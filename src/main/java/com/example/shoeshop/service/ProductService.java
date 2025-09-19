package com.example.shoeshop.service;

import com.example.shoeshop.model.Product;
import com.example.shoeshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        System.out.println("ProductService initialized");
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("Loaded products: " + products.size());
        return products;
    }
}