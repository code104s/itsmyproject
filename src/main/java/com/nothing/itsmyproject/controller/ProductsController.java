package com.nothing.itsmyproject.controller;

import com.nothing.itsmyproject.entity.Products;
import com.nothing.itsmyproject.grpc.GrpcClient;
import com.nothing.itsmyproject.service.ProductsService;
import com.nothing.itsmyproject.service.kafka.KafkaProducerService;
import com.nothing.itsmyproject.service.kafka.ProductLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.google.protobuf.util.JsonFormat;

import java.util.List;
import java.util.Optional;
import search.Search;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

  @Autowired
  private ProductsService productsService;

  @Autowired
  private StreamBridge streamBridge;

  @Autowired
  private KafkaProducerService kafkaProducerService;

  @GetMapping
  public List<Products> getAllProducts(@AuthenticationPrincipal String username) {
    System.out.println("Authenticated user: " + username);
    List<Products> products = productsService.getAllProducts();
    System.out.println("Number of products fetched: " + products.size());
    return products;
  }

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
    Products savedProduct = productsService.saveProduct(product);
    kafkaProducerService.sendLog("product_logs",
        new ProductLog("Product added", savedProduct.getProductId(),
            savedProduct.getProductName(), savedProduct.getProductPrice(),
            savedProduct.getProductQuantity(), savedProduct.getDescription()));
    return savedProduct;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Products> updateProduct(@PathVariable Long id,
      @RequestBody Products productDetails) {
    Optional<Products> product = productsService.getProductById(id);
    if (product.isPresent()) {
      Products updatedProduct = product.get();
      updatedProduct.setProductName(productDetails.getProductName());
      updatedProduct.setProductPrice(productDetails.getProductPrice());
      updatedProduct.setProductQuantity(productDetails.getProductQuantity());
      updatedProduct.setDescription(productDetails.getDescription());
      Products savedProduct = productsService.saveProduct(updatedProduct);
      kafkaProducerService.sendLog("product_logs",
          new ProductLog("Product updated", savedProduct.getProductId(),
              savedProduct.getProductName(), savedProduct.getProductPrice(),
              savedProduct.getProductQuantity(), savedProduct.getDescription()));
      return ResponseEntity.ok(savedProduct);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    Optional<Products> product = productsService.getProductById(id);
    if (product.isPresent()) {
      Products deletedProduct = product.get();
      productsService.deleteProduct(id);
      kafkaProducerService.sendLog("product_logs",
          new ProductLog("Product deleted", deletedProduct.getProductId(),
              deletedProduct.getProductName(), deletedProduct.getProductPrice(),
              deletedProduct.getProductQuantity(), deletedProduct.getDescription()));
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/incrementViewCount/{id}")
  public void incrementViewCount(@PathVariable Long id) {
    kafkaProducerService.sendMessage("products_viewed", id.toString());
  }

  @Autowired
  private GrpcClient grpcClient;

  @GetMapping("/searchProducts")
  public ResponseEntity<?> searchProducts(@RequestParam String query) {
    try {
      Search.SearchProductResponse response = grpcClient.grpcSearchProduct(query);
      String jsonResponse = JsonFormat.printer().print(response);
      return ResponseEntity.ok(jsonResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}