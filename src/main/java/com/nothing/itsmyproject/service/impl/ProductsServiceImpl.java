package com.nothing.itsmyproject.service.impl;

import com.nothing.itsmyproject.entity.Products;
import com.nothing.itsmyproject.repository.ProductRepository;
import com.nothing.itsmyproject.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

  @Autowired
  private ProductRepository productRepository;

  @Cacheable(value = "products", key = "'allProducts'")
  public List<Products> getAllProducts() {
    List<Products> products = productRepository.findAll();
    System.out.println("Number of products fetched: " + products.size());
    return products;
  }

  @Cacheable(value = "products", key = "#id")
  public Optional<Products> getProductById(Long id) {
    System.out.println("Fetching product by id..." + productRepository.findById(id));
    return productRepository.findById(id);
  }

  @CachePut(value = "products", key = "#product.productId")
  public Products saveProduct(Products product) {
    System.out.println("Saving product..." + product + " saved successfully");
    return productRepository.save(product);
  }

  @CacheEvict(value = "products", key = "#id")
  public void deleteProduct(Long id) {
    System.out.println("Deleting product by id..." + id);
    productRepository.deleteById(id);
  }

  @CacheEvict(value = "products", allEntries = true)
  public void evictAllProductCache() {
    System.out.println("Evicting all products from cache...");
  }

  @Override
  public void incrementViewCount(Long productId) {
    Optional<Products> productOpt = productRepository.findById(productId);
    if (productOpt.isPresent()) {
      Products product = productOpt.get();
      product.setViewCount(product.getViewCount() + 1);
      productRepository.save(product);
      System.out.println("Add +1 view count for product: " + productId);
    } else {
      System.err.println("Product not found with id: " + productId);
    }
  }
}