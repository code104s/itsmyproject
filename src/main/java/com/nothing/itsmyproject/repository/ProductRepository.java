package com.nothing.itsmyproject.repository;


import com.nothing.itsmyproject.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {

}
