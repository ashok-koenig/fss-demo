package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "Product name is required")
    String title;
    @NotEmpty(message = "Product category is required")
    String category;
    @PositiveOrZero(message = "Product price is invalid")
    Double price;

    // Entity class should have default constructor, getter / setter methods for all filed.
    public Product() {
    }

    public Product( Long id, String title, String category, Double price) {
        this.title = title;
        this.id = id;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
