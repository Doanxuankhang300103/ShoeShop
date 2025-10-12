package com.example.shoeshop.service;

import com.example.shoeshop.model.Category;
import com.example.shoeshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        System.out.println("CategoryService initialized");
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        System.out.println("Loaded categories: " + categories.size());
        return categories;
    }
}
