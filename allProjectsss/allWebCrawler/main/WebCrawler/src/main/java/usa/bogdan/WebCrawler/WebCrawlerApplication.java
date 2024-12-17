package usa.bogdan.WebCrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import usa.bogdan.WebCrawler.HTMLParser.HTMLParser;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class WebCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawlerApplication.class, args)
				.getBean(HTMLParser.class)
				.parseHtml();
	}

}
