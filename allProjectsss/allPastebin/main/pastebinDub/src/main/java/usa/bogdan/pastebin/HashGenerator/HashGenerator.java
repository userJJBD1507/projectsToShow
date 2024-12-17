package usa.bogdan.pastebin.HashGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import usa.bogdan.pastebin.entities.HashEntity;
import usa.bogdan.pastebin.repositories.Repository2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HashGenerator {
    @Autowired
    Repository2 repository2;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    public String generateHash(Integer id) throws Exception {

        if (repository2.existsById(id)) {
            return repository2.getByMainEntityId(id).getHash();
        }

        String idString = id.toString();
        byte[] encodeBytes = Base64.getEncoder().encode(
                idString.getBytes(StandardCharsets.UTF_8)
        );
        String result = new String(encodeBytes, StandardCharsets.UTF_8);
        byte[] decodedBytes = Base64.getDecoder().decode(result);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(decodedBytes);
        String base64Hash = Base64.getEncoder().encodeToString(hashBytes);
        String modified = base64Hash.substring(0, 8);
        String finalModified = modified.replace("/", "0");
        HashEntity hashEntity = new HashEntity(finalModified, id);
        repository2.save(hashEntity);
        return getHash(finalModified);
    }
    @Scheduled(cron = "30 * * * * *")
    public void autonomic() throws Exception {
        Integer maximumId = repository2.getMaximumId();
        for (Integer i = maximumId + 1; i <= maximumId + 10; i++) {
            if (!repository2.existsById(i)) {
                final int id = i;
                executorService.submit(() -> {
                    try {
                        generateHash(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
    @Cacheable(value = "redis_cache", key = "#hash")
    public String getHash(String hash) {
        return hash;
    }
}
