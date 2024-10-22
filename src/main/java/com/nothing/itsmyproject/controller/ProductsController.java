package com.nothing.itsmyproject.controller;

import com.nothing.itsmyproject.entity.Products;
import com.nothing.itsmyproject.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

  @Autowired
  private ProductsService productsService;

  @GetMapping
  public List<Products> getAllProducts(@AuthenticationPrincipal String username) {
    // using username to get the authenticated user's details
    System.out.println("Authenticated user: " + username);
    return productsService.getAllProducts();
  }

  // get product by id
  @GetMapping("/{id}")
  public ResponseEntity<Products> getProductById(@PathVariable Long id) {
    Optional<Products> product = productsService.getProductById(id);
    if (product.isPresent()) {
      return ResponseEntity.ok(product.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public Products createProduct(@RequestBody Products product) {
    return productsService.saveProduct(product);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody Products productDetails) {
    Optional<Products> product = productsService.getProductById(id);
    if (product.isPresent()) {
      Products updatedProduct = product.get();
      updatedProduct.setProductName(productDetails.getProductName());
      updatedProduct.setProductPrice(productDetails.getProductPrice());
      updatedProduct.setProductQuantity(productDetails.getProductQuantity());
      updatedProduct.setDescription(productDetails.getDescription());
      return ResponseEntity.ok(productsService.saveProduct(updatedProduct));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productsService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}