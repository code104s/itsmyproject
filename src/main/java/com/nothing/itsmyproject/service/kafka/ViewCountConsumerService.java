package com.nothing.itsmyproject.service.kafka;

import com.nothing.itsmyproject.service.impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ViewCountConsumerService {

  @Autowired
  private ProductsServiceImpl productService;

  @KafkaListener(topics = "increment_view_count", groupId = "main_project_group")
  public void consume(String message) {
    try {
      Long productId = Long.parseLong(message.trim());
      productService.incrementViewCount(productId);
      System.out.println("Successfully incremented view count for product ID: " + productId);
    } catch (Exception e) {
      System.err.println("Failed to increment view count for product ID: " + message);
      e.printStackTrace();
    }
  }
}