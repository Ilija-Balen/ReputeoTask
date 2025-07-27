package com.example.reputeo.taskReputeo.rabbit;

import com.example.reputeo.taskReputeo.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key.name}")
    private String routing_key;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Post post){
        log.info(String.format("Message sent -> %s ", post.toString()));
        rabbitTemplate.convertAndSend(exchange, routing_key, post);
    }
}
