package com.example.jwt_oauth2_demo.controller;

import com.example.jwt_oauth2_demo.entity.Product;
import com.example.jwt_oauth2_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private ProductService productService;

    @Autowired
   public ProductController(ProductService productService){
       this.productService = productService;
   }

   @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
   }


   @PostMapping
   public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
   }

   @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product){
        product.setId(id);
        return productService.updateProduct(product);
   }

   @DeleteMapping("/{id}")
   public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
   }

}
