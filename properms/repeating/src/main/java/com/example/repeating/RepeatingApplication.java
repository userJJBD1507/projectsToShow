package com.example.repeating;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import jakarta.persistence.EntityManagerFactory;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@SpringBootApplication
@EnableTransactionManagement
public class RepeatingApplication {
	@Autowired
	private Environment environment;
	public static void main(String[] args) {
		SpringApplication.run(RepeatingApplication.class, args);
	}
	@Bean
	public Map<String, Object> producerConfig() {
		System.out.println("6");
		Map<String, Object> map = new HashMap<>();
		map.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, environment.getProperty("spring.kafka.producer.properties.enable.idempotence"));
		map.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.request.timeout.ms"));
		map.put(ProducerConfig.LINGER_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.linger.ms"));
		map.put(ProducerConfig.ACKS_CONFIG, environment.getProperty("spring.kafka.producer.acks"));
		map.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, environment.getProperty("spring.kafka.producer.transaction-id-prefix"));
		map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"));
		map.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, environment.getProperty("spring.kafka.producer.properties.max.in.flight.requests.per.connection"));
		map.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
		return map;
	}
	@Bean
	public NewTopic topic() {
		System.out.println("5");
		return TopicBuilder
				.name("top")
				.partitions(3)
				.replicas(3)
				.configs(Map.of("min.insync.replicas", "2"))
				.build();
	}
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		System.out.println("4");
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate111() {
		System.out.println("3");
		return new KafkaTemplate<>(producerFactory());
	}
	@Bean
	public KafkaTransactionManager<String, String> kafkaTransactionManager() {
		System.out.println("2");
		return new KafkaTransactionManager<>(producerFactory());
	}
	@Bean("transactionManager")
	public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
		System.out.println("1");
		return new JpaTransactionManager(entityManagerFactory);
	}
}