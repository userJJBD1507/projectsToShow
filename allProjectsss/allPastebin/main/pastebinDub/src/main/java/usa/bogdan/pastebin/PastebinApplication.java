package usa.bogdan.pastebin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import usa.bogdan.pastebin.entities.MetadataEntity;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
public class PastebinApplication {

	public static void main(String[] args) {
		SpringApplication.run(PastebinApplication.class, args);
	}
	@Bean
	public MetadataEntity metadataEntity() {
		return new MetadataEntity();
	}

}
