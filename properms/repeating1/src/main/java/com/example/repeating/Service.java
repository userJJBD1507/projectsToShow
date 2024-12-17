package com.example.repeating;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private CredenRepository credenRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Reposiotry reposiotry;
    Logger logger = LoggerFactory.getLogger(Service.class);
    ObjectMapper objectMapper = new ObjectMapper();
    @Transactional
    @KafkaListener(topics = "top", groupId = "sth")
    public void implementation(@Payload String mainMessage, @Header("messageId") String messageId,
                               @Header(KafkaHeaders.RECEIVED_KEY) String receivedKey) throws Exception {
        System.out.println("for " + mainMessage);
        Creden creden = credenRepository.findByMessageId(messageId);
        if (creden != null) {
            System.out.println("duplicate for " + messageId);
            return;
        }
//        try {
//            Entity entity = objectMapper.readValue(mainMessage, Entity.class);
//
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setTo(entity.getEmail());
//            simpleMailMessage.setSubject("saved");
//            simpleMailMessage.setText(entity.getSaved());
//            javaMailSender.send(simpleMailMessage);
//
//            reposiotry.save(entity);
//            logger.warn("saved");
//
//        } catch (ResourceAccessException e) {
//            e.printStackTrace();
//            throw new RetryableException(e);
//        } catch (HttpServerErrorException e) {
//            e.printStackTrace();
//            throw new NonRetryableException(e);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new NonRetryableException(e);
//        }
        if (true) {
            throw new RetryableException("dsvfgdvgfvsdghfv");
        }
        try {
            credenRepository.save(new Creden(messageId, mainMessage));
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new NonRetryableException(e);
        }
    }
}