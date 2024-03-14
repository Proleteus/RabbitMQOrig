package com.htw.gateway.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.htw.gateway.config.ProductMessage;
import com.htw.gateway.model.Basket;
import com.htw.gateway.model.Product;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;

public class Mapper {
    public static ProductMessage mapToProductMessage(Product product, HttpSession session) {
        ProductMessage productMessage = new ProductMessage();
        productMessage.setId(product.getId());
        productMessage.setName(product.getName());
        productMessage.setPrice(product.getPrice());
        productMessage.setSessionId(session.toString());
        return productMessage;
    }

}