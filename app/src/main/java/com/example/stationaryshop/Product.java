package com.example.stationaryshop;

public class Product {
    private String id;
    private String name;
    private String imageUrl;
    private String price;
    private String description;
    private String category;

    public Product(String productName, double productPrice) {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }

    public Product(String id, String name, String imageUrl, String price, String description, String category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    // Getters and setters
}
