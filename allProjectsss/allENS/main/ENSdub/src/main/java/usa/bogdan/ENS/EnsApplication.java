package usa.bogdan.ENS;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class EnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnsApplication.class, args);
	}
	@Bean
	public NewTopic newTopic() {
		return new NewTopic("kafka_topic1",
				1,
				(short) 1);
	}
	@Bean
	public NewTopic newTopic2() {
		return new NewTopic("kafka_topic2",
				1,
				(short) 1);
	}
}
