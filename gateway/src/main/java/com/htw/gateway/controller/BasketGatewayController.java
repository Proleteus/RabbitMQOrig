package com.htw.gateway.controller;

import com.htw.gateway.config.MQBasketConfig;
import com.htw.gateway.mapper.Mapper;
import com.htw.gateway.config.ProductMessage;
import com.htw.gateway.model.Basket;
import com.htw.gateway.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class BasketGatewayController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasketGatewayController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public BasketGatewayController(RabbitTemplate productTemplate) {
        this.rabbitTemplate = productTemplate;
    }

    @PostMapping("/basket/add")
    public void addToBasket(@RequestBody Product product, HttpSession session) {
        ProductMessage productMessage = Mapper.mapToProductMessage(product, session);
        LOGGER.info(String.format("Add requested -> %s", productMessage));
        rabbitTemplate.convertAndSend("basket-exchange", "basket.add", productMessage);
    }

    @DeleteMapping("/basket/remove")
    public void removeFromBasket(@RequestBody Product product, HttpSession session) {
        ProductMessage productMessage = Mapper.mapToProductMessage(product, session);
        LOGGER.info(String.format("Remove requested -> %s", productMessage));
        rabbitTemplate.convertAndSend("basket-exchange", "basket.remove", productMessage);
    }

    @DeleteMapping("/basket/removeAll")
    public void removeAllFromBasket(HttpSession session) {
        LOGGER.info(String.format("Remove All requested -> %s", session));
        rabbitTemplate.convertAndSend("basket-exchange", "basket.removeAll", session.getId());
    }

    @GetMapping("/basket/view")
    public String viewBasket(HttpSession session) {
        LOGGER.info(String.format("Basket requested -> %s", session));
        return rabbitTemplate.convertSendAndReceive("basket-exchange", "basket.view", session.getId()).toString();
    }
}