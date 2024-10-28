package com.nothing.itsmyproject.service.kafka;

import com.nothing.itsmyproject.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

  @Autowired
  private ProductsService productsService;

  @KafkaListener(topics = "products_viewed", groupId = "my_consumer_group")
  public void consume(String message) {
    try {
      System.out.println("Received message: " + message);
      Long productId = Long.parseLong(message.trim()); // chuyển message từ String sang Long
      productsService.incrementViewCount(productId);
    } catch (Exception e) {
      System.err.println("Error processing message: " + message);
      e.printStackTrace();
    }
  }
}