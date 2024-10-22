package com.nothing.itsmyproject.service;

import com.nothing.itsmyproject.entity.Products;
import com.nothing.itsmyproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

  @Autowired
  private ProductRepository productRepository;

  @Cacheable(value = "products")
  public List<Products> getAllProducts() {
    return productRepository.findAll();
  }

  @Cacheable(value = "products", key = "#id")
  public Optional<Products> getProductById(Long id) {
    return productRepository.findById(id);
  }

  @CachePut(value = "products", key = "#product.productId")
  public Products saveProduct(Products product) {
    return productRepository.save(product);
  }

  @CacheEvict(value = "products", key = "#id")
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}