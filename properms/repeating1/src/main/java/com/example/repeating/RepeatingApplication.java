package com.example.repeating;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableTransactionManagement
public class RepeatingApplication {
	@Autowired
	private Environment environment;
	public static void main(String[] args) {
		SpringApplication.run(RepeatingApplication.class, args);
	}
	@Bean
	public ConsumerFactory<String, Object> consumerFactory() {
		System.out.println("4");
		Map<String, Object> map = new HashMap<>();
		map.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, environment.getProperty("spring.kafka.consumer.isolation-level",
				"READ_COMMITTED").toLowerCase());
		map.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));
		map.put(JsonDeserializer.TRUSTED_PACKAGES, environment.getProperty("spring.kafka.consumer.properties.spring.json.request.trusted.packages"));
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"));
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		map.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(map);
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
			KafkaTemplate kafkaTemplate, ConsumerFactory<String, Object> consumerFactory
	) {
		System.out.println("3");
		DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate),
				new FixedBackOff(3000, 3));
		defaultErrorHandler.addNotRetryableExceptions(NonRetryableException.class);
		defaultErrorHandler.addRetryableExceptions(RetryableException.class);
		ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory =
				new ConcurrentKafkaListenerContainerFactory<>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
		concurrentKafkaListenerContainerFactory.setCommonErrorHandler(defaultErrorHandler);
		return concurrentKafkaListenerContainerFactory;
	}
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		System.out.println("2");
		return new KafkaTemplate<>(producerFactory());
	}
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		System.out.println("1");
		Map<String, Object> map = new HashMap<>();
				map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"));
		map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(map);
	}
}