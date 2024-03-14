package com.htw.basket.config;

import com.htw.basket.MQBasketConsumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.util.ErrorHandler;
import org.springframework.web.ErrorResponseException;

@Configuration
public class MQConfig {
    public static final String BASKET_EXCHANGE_NAME = "basket-exchange";
    public static final String BASKET_ADD_QUEUE_NAME = "basket-add-queue";
    public static final String BASKET_REMOVE_QUEUE_NAME = "basket-remove-queue";
    public static final String BASKET_REMOVE_ALL_QUEUE_NAME = "basket-remove-all-queue";
    public static final String BASKET_VIEW_QUEUE_NAME = "basket-view-queue";

    @Bean
    public MQBasketConsumer rabbitController() {
        return new MQBasketConsumer();
    }
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
        rabbitTemplate.setExchange(BASKET_EXCHANGE_NAME);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
