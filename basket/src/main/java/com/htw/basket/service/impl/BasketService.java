package com.htw.basket.service.impl;

import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;
import com.htw.basket.service.interfaces.IBasketservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService implements IBasketservice {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasketService.class);
    // Simulating a database for storing baskets
    private List<Basket> baskets = new ArrayList<>();

    @Override
    public void addToBasket(Product product, String session) {
        if (product != null && session != null) {
            Basket basket = getOrCreateBasket(session);
            basket.addProduct(product);
            LOGGER.info(String.format("Message sent -> %s", product));
        } else {
            throw new IllegalArgumentException("Product and session cannot be null.");
        }
    }

    @Override
    public Basket getBasket(String session) {
        if (session != null) {
            Basket basket = findBasket(session);
            if (basket != null) {
                return basket;
            } else {
                //throw new IllegalStateException("Basket does not exist for the current session.");
                return basket;
            }
        } else {
            throw new IllegalArgumentException("Session cannot be null.");
        }
    }


    private Basket findBasket(String sessionId) {
        return baskets.stream()
                .filter(basket -> basket.getSessionId().equals(sessionId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeFromBasket(Product product, String session) {
        if (product != null && session != null) {
            Basket basket = getOrCreateBasket(session);
            basket.removeProduct(product);
        } else {
            throw new IllegalArgumentException("Product and session cannot be null.");
        }
    }

    private Basket getOrCreateBasket(String sessionId) {
        return baskets.stream()
                .filter(basket -> basket.getSessionId().equals(sessionId))
                .findFirst()
                .orElseGet(() -> {
                    Basket newBasket = new Basket(sessionId);
                    baskets.add(newBasket);
                    return newBasket;
                });
    }

    public void clearBasket(String session) {
        if (session != null) {
            baskets.removeIf(basket -> basket.getSessionId().equals(session));
        } else {
            throw new IllegalArgumentException("Session cannot be null.");
        }
    }
    public List<Basket> getAllBaskets() {
        return baskets;
    }
}
