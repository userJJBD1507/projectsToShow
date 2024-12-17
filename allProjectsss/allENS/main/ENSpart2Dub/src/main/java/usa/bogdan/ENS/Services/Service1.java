package usa.bogdan.ENS.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import usa.bogdan.ENS.EMAILunit.Emailservice;
import usa.bogdan.ENS.Entity.CSVuser;
import usa.bogdan.ENS.Entity.ENSentity;
import usa.bogdan.ENS.Entity.KafkaMessage;
import usa.bogdan.ENS.Entity.KafkaResend;
import usa.bogdan.ENS.Repositories.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class Service1 {

    @Autowired
    private Emailservice emailservice;
    @Autowired
    private Repository repository;
    @KafkaListener(topics = "kafka_topic1", groupId = "all")
    public void processMessage(String kafkaMessageJson) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaMessage message = objectMapper.readValue(kafkaMessageJson, KafkaMessage.class);

        List<CSVuser> csvUsers = objectMapper.readValue(message.getFileContent(), new TypeReference<List<CSVuser>>() {});
        String newMessage = message.getShape().replace("\"", "");
        for (CSVuser go: csvUsers) {

            ENSentity ensEntity = new ENSentity(go.getName(),
                    go.getContactType(),
                    newMessage,
                    go.getContactValue(),
                    "FAILED");

            try {
                repository.save(ensEntity);
                ensEntity.setStatus("OK");
                repository.save(ensEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        EmailSend(csvUsers, newMessage);
    }

    public List<CSVuser> csvParser(MultipartFile multipartFile) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            CsvToBean<CSVuser> csvToBean = new CsvToBeanBuilder<CSVuser>(reader)
                    .withType(CSVuser.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<CSVuser> users = csvToBean.parse();
            return users;
        }
    }
    @Cacheable(value = "redis_cache")
    public List<CSVuser> getEMAILusers(List<CSVuser> listOK) {
        return listOK.stream().filter(e -> "email".equals(e.getContactType()))
                .collect(Collectors.toList());
    }
    public String EmailSend(List<CSVuser> emailUsers, String message)
            throws Exception {
        emailservice.sendEmail(emailUsers, message);
        return "The template was sent successfully";
    }
    @Scheduled(fixedDelay = 120, timeUnit = TimeUnit.SECONDS)
    @CacheEvict(value = "redis_cache", allEntries = true)
    public void deleteClearDB() {
        repository.deleteClearDB();
    }
    @KafkaListener(topics = "kafka_topic2", groupId = "resended")
    public void resendMessage(String message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaResend kafkaResend = objectMapper
                .readValue(message, new TypeReference<KafkaResend>() {
                });
        List<ENSentity> listOK = kafkaResend.getListOK();
        List<CSVuser> list = new ArrayList<>();
        for (ENSentity go1: listOK) {
            list.add(new CSVuser(go1.getName(),
                    go1.getContactType(),
                    go1.getEmail()));
            EmailSend(list, go1.getShape());
        }
    }
}
