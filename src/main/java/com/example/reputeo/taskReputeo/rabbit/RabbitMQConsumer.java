package com.example.reputeo.taskReputeo.rabbit;

import com.example.reputeo.taskReputeo.entity.Post;
import com.example.reputeo.taskReputeo.service.api.PostServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {
    private final PostServiceApi postServiceApi;

    public RabbitMQConsumer(PostServiceApi postServiceApi) {
        this.postServiceApi = postServiceApi;
    }


    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(Post post){
        log.info(String.format("Received message -> %s", post.toString()));
        postServiceApi.create(post);
    }
}
