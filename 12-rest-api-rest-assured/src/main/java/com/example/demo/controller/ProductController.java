package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.EntityReference;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @GetMapping("/async-task")
    public CompletableFuture<String> executeAsyncTask() throws InterruptedException {
        return productService.processLongRunningTask();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id).orElseThrow(()-> new ProductNotFoundException(id));
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@Valid @RequestBody Product product){
        productService.getProductById(id).orElseThrow(()-> new ProductNotFoundException(id));
        product.setId(id);
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProductPrice(@PathVariable Long id, @RequestParam Double price){
       Product product = productService.getProductById(id).orElseThrow(()-> new ProductNotFoundException(id));
        product.setPrice(price);
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        productService.getProductById(id).orElseThrow(()-> new ProductNotFoundException(id));
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
