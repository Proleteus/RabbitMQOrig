package com.htw.basket.service.interfaces;

import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IBasketservice {

    void addToBasket(Product product, String session);
    Basket getBasket(String session);
    void removeFromBasket(Product product, String session);
    public void clearBasket(String session);
    public List<Basket> getAllBaskets();
}