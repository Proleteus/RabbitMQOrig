package com.htw.gateway.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private String sessionId;
    private List<Product> products = new ArrayList<>();

    public Basket(String sessionId) {
        this.sessionId = sessionId;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getSessionId() {
        return sessionId;
    }
}