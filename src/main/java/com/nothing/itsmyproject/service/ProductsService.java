package com.nothing.itsmyproject.service;

import com.nothing.itsmyproject.entity.Products;
import java.util.List;
import java.util.Optional;

public interface ProductsService {

  List<Products> getAllProducts();

  Optional<Products> getProductById(Long id);

  Products saveProduct(Products product);

  void deleteProduct(Long id);

  // Increment view count
  void incrementViewCount(Long id);
}
