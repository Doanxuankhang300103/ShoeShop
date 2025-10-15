package com.example.shoeshop.service;

import com.example.shoeshop.model.Product;
import com.example.shoeshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public List<Product> getFilteredProducts(String price, String color, String size, String name, String stock, String categoryId, String brandId) {
        List<Product> products = productRepository.findAll();
        // Lọc theo tên
        if (name != null && !name.isEmpty()) {
            products = products.stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).toList();
        }
        // Lọc theo giá
        if (price != null && !price.isEmpty()) {
            switch (price) {
                case "1": // 0 - 100
                    products = products.stream().filter(p -> p.getPrice().compareTo(BigDecimal.valueOf(0)) >= 0 && p.getPrice().compareTo(BigDecimal.valueOf(100)) <= 0).toList();
                    break;
                case "2": // 100 - 200
                    products = products.stream().filter(p -> p.getPrice().compareTo(BigDecimal.valueOf(100)) > 0 && p.getPrice().compareTo(BigDecimal.valueOf(200)) <= 0).toList();
                    break;
                case "3": // 200 - 300
                    products = products.stream().filter(p -> p.getPrice().compareTo(BigDecimal.valueOf(200)) > 0 && p.getPrice().compareTo(BigDecimal.valueOf(300)) <= 0).toList();
                    break;
                case "4": // 300 - 400
                    products = products.stream().filter(p -> p.getPrice().compareTo(BigDecimal.valueOf(300)) > 0 && p.getPrice().compareTo(BigDecimal.valueOf(400)) <= 0).toList();
                    break;
                case "5": // 400 - 500
                    products = products.stream().filter(p -> p.getPrice().compareTo(BigDecimal.valueOf(400)) > 0 && p.getPrice().compareTo(BigDecimal.valueOf(500)) <= 0).toList();
                    break;
            }
        }
        // Lọc theo màu sắc
        if (color != null && !color.isEmpty() && !color.equals("all")) {
            products = products.stream().filter(p -> color.equalsIgnoreCase(p.getColor())).toList();
        }
        // Lọc theo kích thước
        if (size != null && !size.isEmpty() && !size.equals("all")) {
            products = products.stream().filter(p -> size.equalsIgnoreCase(p.getSize())).toList();
        }
        // Lọc theo stock
        if (stock != null && !stock.isEmpty()) {
            try {
                int stockVal = Integer.parseInt(stock);
                products = products.stream().filter(p -> p.getStock() == stockVal).toList();
            } catch (NumberFormatException ignored) {}
        }
        // Lọc theo categoryId
        if (categoryId != null && !categoryId.isEmpty()) {
            try {
                Long catId = Long.parseLong(categoryId);
                products = products.stream().filter(p -> p.getCategory().getId().equals(catId)).toList();
            } catch (NumberFormatException | NullPointerException ignored) {}
        }
        // Lọc theo brandId
        if (brandId != null && !brandId.isEmpty()) {
            try {
                Long bId = Long.parseLong(brandId);
                products = products.stream().filter(p -> p.getBrandId() != null && p.getBrandId().equals(bId)).toList();
            } catch (NumberFormatException ignored) {}
        }
        return products;
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}