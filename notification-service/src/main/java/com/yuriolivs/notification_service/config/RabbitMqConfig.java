package com.yuriolivs.notification_service.config;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String EXCHANGE =
            "notification.exchange";
    public static final String MAIL_QUEUE =
            "notification.email.queue";
    public static final String TELEGRAM_QUEUE =
            "notification.telegram.queue";
    public static final String RESULT_QUEUE =
            "notification.result.queue";
    public static final String MAIL_ROUTING_KEY =
            "email";
    public static final String TELEGRAM_ROUTING_KEY =
            "telegram";
    public static final String RESULT_ROUTING_KEY =
            "result";

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());

        return factory;
    }

    @Bean
    public Queue mailQueue() { return new Queue(MAIL_QUEUE); }

    @Bean
    public Queue telegramQueue() { return new Queue(TELEGRAM_QUEUE); }

    @Bean
    public Queue resultQueue() { return new Queue(RESULT_QUEUE); }

    @Bean
    public Binding mailBinding() {
        return BindingBuilder
                .bind(mailQueue())
                .to(exchange())
                .with(MAIL_ROUTING_KEY);
    }

    @Bean
    public Binding telegramBinding() {
        return BindingBuilder
                .bind(telegramQueue())
                .to(exchange())
                .with(TELEGRAM_ROUTING_KEY);
    }

    @Bean
    public Binding resultBinding() {
        return BindingBuilder
                .bind(resultQueue())
                .to(exchange())
                .with(RESULT_ROUTING_KEY);
    }
}
