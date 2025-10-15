package com.example.shoeshop.controller;

import com.example.shoeshop.model.Category;
import com.example.shoeshop.model.Product;
import com.example.shoeshop.service.CategoryService;
import com.example.shoeshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category-list"; // Render a separate category list page (create this if needed)
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable("id") Long id, Model model) {
        List<Product> products = productService.getProductsByCategoryId(id);
        model.addAttribute("products", products);
        model.addAttribute("categoryId", id);
        return "category-detail"; // Render a category detail page (create this if needed)
    }
}