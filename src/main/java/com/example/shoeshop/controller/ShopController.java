package com.example.shoeshop.controller;

import com.example.shoeshop.model.Product;
import com.example.shoeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("")
public class ShopController {
    @Autowired
    private ProductService productService;

    @GetMapping("/shop")
    public String shopPage(
        @RequestParam(value = "price", required = false) String price,
        @RequestParam(value = "color", required = false) String color,
        @RequestParam(value = "size", required = false) String size,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "stock", required = false) String stock,
        @RequestParam(value = "categoryId", required = false) String categoryId,
        @RequestParam(value = "brandId", required = false) String brandId,
        Model model) {
        List<Product> products = productService.getFilteredProducts(price, color, size, name, stock, categoryId, brandId);
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/shop/detail/{id}")
    public String shopDetail(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "shop-detail";
    }
}
