package com.htw.gateway.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQBasketConfig {
    public static final String BASKET_EXCHANGE_NAME = "basket-exchange";
    public static final String BASKET_ADD_QUEUE_NAME = "basket-add-queue";
    public static final String BASKET_REMOVE_QUEUE_NAME = "basket-remove-queue";
    public static final String BASKET_REMOVE_ALL_QUEUE_NAME = "basket-remove-all-queue";
    public static final String BASKET_VIEW_QUEUE_NAME = "basket-view-queue";

    @Bean
    public Exchange basketExchange() {
        return ExchangeBuilder.directExchange(BASKET_EXCHANGE_NAME).durable(true).build();
    }
    @Bean
    public Queue basketAddQueue() {
        return QueueBuilder.durable(BASKET_ADD_QUEUE_NAME).build();
    }

    @Bean
    public Queue basketRemoveQueue() {
        return QueueBuilder.durable(BASKET_REMOVE_QUEUE_NAME).build();
    }

    @Bean
    public Queue basketRemoveAllQueue() {
        return QueueBuilder.durable(BASKET_REMOVE_ALL_QUEUE_NAME).build();
    }

    @Bean
    public Queue basketViewQueue() {
        return QueueBuilder.durable(BASKET_VIEW_QUEUE_NAME).build();
    }

    @Bean
    public Binding basketAddBinding(Queue basketAddQueue, Exchange basketExchange) {
        return BindingBuilder.bind(basketAddQueue).to(basketExchange).with("basket.add").noargs();
    }

    @Bean
    public Binding basketRemoveBinding(Queue basketRemoveQueue, Exchange basketExchange) {
        return BindingBuilder.bind(basketRemoveQueue).to(basketExchange).with("basket.remove").noargs();
    }

    @Bean
    public Binding basketRemoveAllBinding(Queue basketRemoveAllQueue, Exchange basketExchange) {
        return BindingBuilder.bind(basketRemoveAllQueue).to(basketExchange).with("basket.removeAll").noargs();
    }

    @Bean
    public Binding basketViewBinding(Queue basketViewQueue, Exchange basketExchange) {
        return BindingBuilder.bind(basketViewQueue).to(basketExchange).with("basket.view").noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
