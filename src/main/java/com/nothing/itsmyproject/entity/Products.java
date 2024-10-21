package com.nothing.itsmyproject.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
public class Products {

  @Id
  @GeneratedValue
  @Column(name = "product_id")
  private Long productId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "product_price")
  private double productPrice;

  @Column(name = "product_quantity")
  private int productQuantity;

  @Column(name = "decription")
  private String decription;

  // Getters and Setters
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

  public String getDecription() {
    return decription;
  }

  public void setDecription(String decription) {
    this.decription = decription;
  }

  // toString
  @Override
  public String toString() {
    return "Products{" +
        "productId=" + productId +
        ", productName='" + productName + '\'' +
        ", productPrice=" + productPrice +
        ", productQuantity=" + productQuantity +
        ", decription='" + decription + '\'' +
        '}';
  }
}
