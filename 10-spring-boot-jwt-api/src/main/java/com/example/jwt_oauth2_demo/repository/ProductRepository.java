package com.example.jwt_oauth2_demo.repository;

import com.example.jwt_oauth2_demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
