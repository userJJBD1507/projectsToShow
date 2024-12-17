package com.example.repeating;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.metrics.Stat;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.UUID;
@org.springframework.stereotype.Service
public class Service {
    @Autowired
    @Qualifier("kafkaTemplate111")
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private NewTopic topic;
    @Autowired
    private StatusRepository statusRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void implementation(Entity entity) throws Exception {
        String stringify = objectMapper.writeValueAsString(entity);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                topic.name(),
                UUID.randomUUID().toString(),
                stringify
        );
        producerRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        kafkaTemplate.send(producerRecord);
        statusRepository.save(new Status("ok"));
        if (entity.getSaved().length() > 10) {
            throw new Exception(" ");
        }
    }
}