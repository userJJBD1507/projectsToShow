package usa.bogdan.pastebin.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import usa.bogdan.pastebin.HashGenerator.HashGenerator;
import usa.bogdan.pastebin.entities.HashEntity;
import usa.bogdan.pastebin.entities.MetadataEntity;
import usa.bogdan.pastebin.libraries.JSONO;
import usa.bogdan.pastebin.repositories.Repository1;
import usa.bogdan.pastebin.repositories.Repository2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class Service1 {
    @Autowired
    Repository1 repository1;
    @Autowired
    MetadataEntity metadataEntity;
    @Autowired
    HashGenerator hashGenerator;
    @Autowired
    Repository2 repository2;
    private static final String SERVICE_ENDPOINT = "s3.wasabisys.com";
    private static final String REGION = "us-east-1";
    private static final String ACCESS_KEY = "CKJZ27AMBOTRL0T9AS0A";
    private static final String SECRET_KEY = "reXduJz7Y3zSRuj379JD9lvDQyXvydCS3RY7zCmj";
    private static final String BUCKET_NAME = "bucket-777";

    private static final AmazonS3 AMAZON_S3_CLIENT = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, REGION))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
            .build();

    public String uploadText(String text, String deletion_time) throws Exception {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            String filename = "textfile" + System.currentTimeMillis() + ".txt";  // Генерируем уникальное имя файла

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(data.length);
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, filename, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            AMAZON_S3_CLIENT.putObject(putObjectRequest);

            MetadataEntity metadataEntity = new MetadataEntity(filename, deletion_time);
            String name = metadataEntity.getMetadata_link();
            repository1.save(metadataEntity);

            int entityhashCode = getMetadataCache(name).hashCode();

            System.out.println(entityhashCode);

            deleteFile(filename, deletion_time);

            int id = getMetadataCache(name).getId();

            HashEntity hashEntity = repository2.getByMainEntityId(id);

            String generatedHash;
            if (hashEntity != null) {
                generatedHash = hashEntity.getHash();
            } else {
                generatedHash = hashGenerator.generateHash(id);
            }

            return "http://localhost:8081/get/" + generatedHash;
        }
    }
    @Cacheable(value = "redis_cache", key = "#key")
    public String getObject(String key) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        GetObjectRequest getObjectRequest = new GetObjectRequest(BUCKET_NAME, key);
        String result = new String(AMAZON_S3_CLIENT.getObject(getObjectRequest).getObjectContent().readAllBytes());

        if (result.startsWith("\"") && result.endsWith("\"")) {
            result = result.substring(1, result.length() - 1);
        }
        result = result.replace("\\n", "")
                .replace("\\", "");
        return result;
    }
    @CacheEvict(value = "redis_cache", key = "#key")
    public String deleteFile(String key, String deleteTime) {
        MetadataEntity metadata = getMetadataCache(key);
        LocalDateTime localDateTime = LocalDateTime.parse(deleteTime);
        Date deletionDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BUCKET_NAME, key);
                AMAZON_S3_CLIENT.deleteObject(deleteObjectRequest);
                repository1.delete(key);
                repository2.delete(metadata.getId());
            }
        };
        timer.schedule(timerTask, deletionDate);
        return "deleted " + key;
    }
    @Cacheable(value = "redis_cache", key = "#metadata_link")
    public MetadataEntity getMetadataCache(String metadata_link) {
        return repository1.functionGetting(metadata_link);
    }
}
