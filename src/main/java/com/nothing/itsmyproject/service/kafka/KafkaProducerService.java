package com.nothing.itsmyproject.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private KafkaTemplate<String, ProductLog> kafkaTemplateObject;

  @Autowired
  private KafkaTemplate<String, UserLog> kafkaTemplateUserLog;

  public void sendMessage(String topic, String message) {
    kafkaTemplate.send(topic, message);
  }
  public void sendLog(String topic, ProductLog log) {
    kafkaTemplateObject.send(topic, log);
  }
  public void sendUserLog(String topic, UserLog log) {
    kafkaTemplateUserLog.send(topic, log);
  }
}
