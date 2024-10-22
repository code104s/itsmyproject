package com.nothing.itsmyproject.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "comments")
public class Comments {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "comment")
  private String comment;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Products product;

  // Getter and setter
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Products getProduct() {
    return product;
  }

  public void setProduct(Products product) {
    this.product = product;
  }

  // toString
  @Override
  public String toString() {
    return "Comments{" +
        "id=" + id +
        ", comment='" + comment + '\'' +
        ", product=" + product +
        '}';
  }
}
