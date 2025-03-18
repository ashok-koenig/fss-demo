package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Page<Product> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Page<Product> filterProducts(String title, Pageable pageable){
        return productRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    @Transactional
    public Product updateProduct(Product product){
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }
}
