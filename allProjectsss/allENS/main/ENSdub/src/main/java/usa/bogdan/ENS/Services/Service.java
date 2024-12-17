package usa.bogdan.ENS.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.multipart.MultipartFile;
import usa.bogdan.ENS.Entity.CSVuser;
import usa.bogdan.ENS.Entity.ENSentity;
import usa.bogdan.ENS.Entity.KafkaResend;
import usa.bogdan.ENS.Repository.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@org.springframework.stereotype.Service
public class Service {
    @Autowired
    Repository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Service(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String parser(MultipartFile multipartFile) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            ObjectMapper objectMapper = new ObjectMapper();
            CsvToBean<CSVuser> csvToBean = new CsvToBeanBuilder<CSVuser>(reader)
                    .withType(CSVuser.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<CSVuser> users = csvToBean.parse();
            String s = objectMapper.writeValueAsString(users);
            return s;
        }
    }
    @Scheduled(fixedDelay = 60, timeUnit = TimeUnit.SECONDS)
    public void sendToFailed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ENSentity> failedUsers = repository.getFailedUsers();
        for (ENSentity go: failedUsers) {
            try {

                KafkaResend message = new KafkaResend(Collections.singletonList(go));
                kafkaTemplate.send("kafka_topic2", objectMapper.writeValueAsString(message));
                try {
                    repository.updateStatus(go.getId(), "OK");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
