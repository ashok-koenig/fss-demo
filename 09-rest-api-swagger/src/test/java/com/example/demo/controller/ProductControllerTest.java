package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class ProductControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetAllProducts() throws  Exception {
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "iPhone 16", "Smartphone", 16234.2 ),
                new Product(1L, "iPhone 15", "Smartphone", 15234.2 )
        );
        when(productService.getAllProducts()).thenReturn(mockProducts);

        mockMvc.perform(get("/api/products")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetAllProducts_failure() throws  Exception {
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "iPhone 16", "Smartphone", 16234.2 )
        );
        when(productService.getAllProducts()).thenReturn(mockProducts);

        mockMvc.perform(get("/api/products")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2));
    }
}
