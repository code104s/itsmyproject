package com.nothing.itsmyproject.service.kafka;

import java.io.Serializable;

public class ProductLog implements Serializable {

  private String action;
  private Long productId;
  private String productName;
  private double productPrice;
  private int productQuantity;
  private String description;

  // Constructors, getters, and setters
  public ProductLog(String action, Long productId, String productName, double productPrice,
      int productQuantity, String description) {
    this.action = action;
    this.productId = productId;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productQuantity = productQuantity;
    this.description = description;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public double getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(double productPrice) {
    this.productPrice = productPrice;
  }

  public int getProductQuantity() {
    return productQuantity;
  }

  public void setProductQuantity(int productQuantity) {
    this.productQuantity = productQuantity;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}