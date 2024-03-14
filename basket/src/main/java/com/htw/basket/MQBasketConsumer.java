package com.htw.basket;

import com.htw.basket.config.ProductMessage;
import com.htw.basket.mapper.Mapper;
import com.htw.basket.model.Basket;
import com.htw.basket.model.Product;
import com.htw.basket.config.MQConfig;
import com.htw.basket.service.interfaces.IBasketservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

@Component
public class MQBasketConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MQBasketConsumer.class);
    @Autowired
    private IBasketservice basketService;

    @RabbitListener(queues = MQConfig.BASKET_ADD_QUEUE_NAME)
    public void handleAddToBasket(ProductMessage productMessage) {
        LOGGER.info(String.format("Add received -> %s", productMessage));
        basketService.addToBasket(Mapper.toProduct(productMessage), productMessage.getSessionId());
    }

    @RabbitListener(queues = MQConfig.BASKET_REMOVE_QUEUE_NAME)
    public void handleRemoveFromBasket(ProductMessage productMessage) {
        LOGGER.info(String.format("Remove received -> %s", productMessage));
        basketService.removeFromBasket(Mapper.toProduct(productMessage), productMessage.getSessionId());
    }

    @RabbitListener(queues = MQConfig.BASKET_REMOVE_ALL_QUEUE_NAME)
    public void handleRemoveAllFromBasket(String session) {
        LOGGER.info(String.format("Remove All received -> %s", session));
        basketService.clearBasket(session);
    }

    @RabbitListener(queues = MQConfig.BASKET_VIEW_QUEUE_NAME)
    public String handleViewBasket(String session) {
        LOGGER.info(String.format("Basket request received -> %s", session));
        return new Gson().toJson(basketService.getBasket(session));
    }

}