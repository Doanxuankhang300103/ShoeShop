package com.example.shoeshop.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    // Parent category (self-referencing)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // Child categories
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children;

    // One category has many products
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public Category getParent() { return parent; }
    public void setParent(Category parent) { this.parent = parent; }

    public List<Category> getChildren() { return children; }
    public void setChildren(List<Category> children) { this.children = children; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

//    @Transient
//    public int getProductCount() {
//        return (products != null) ? products.size() : 0;
//    }


}
