package com.htw.basket.mapper;

import com.htw.basket.config.ProductMessage;
import com.htw.basket.model.Product;
import com.htw.basket.model.Session;
import jakarta.servlet.http.HttpSession;

public class Mapper {
    public static Product toProduct(ProductMessage message) {
        return new Product(message.getName(), message.getId(), message.getPrice());
    }
}
