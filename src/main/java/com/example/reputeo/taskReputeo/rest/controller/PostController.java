package com.example.reputeo.taskReputeo.rest.controller;

import com.example.reputeo.taskReputeo.dto.PostDto;
import com.example.reputeo.taskReputeo.entity.Post;
import com.example.reputeo.taskReputeo.exception.ResourceNotFoundException;
import com.example.reputeo.taskReputeo.mapper.PostMapper;
import com.example.reputeo.taskReputeo.rabbit.RabbitMqProducer;
import com.example.reputeo.taskReputeo.rest.api.PostControllerApi;
import com.example.reputeo.taskReputeo.service.api.PostServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class PostController implements PostControllerApi {

    private final PostMapper postMapper;
    private final PostServiceApi postServiceApi;
    private final RabbitMqProducer rabbitMqProducer;

    public PostController(PostMapper postMapper, PostServiceApi postServiceApi, RabbitMqProducer rabbitMqProducer) {
        this.postMapper = postMapper;
        this.postServiceApi = postServiceApi;
        this.rabbitMqProducer = rabbitMqProducer;
    }

    @Override
    public PostDto get(long id) throws ResourceNotFoundException {
        return postMapper.toDto(postServiceApi.get(id));
    }

    @Override
    public Page<PostDto> getAll(Map<String, Object> params, Pageable pageable) {
        Page<Post> posts = postServiceApi.getAll(params, pageable);
        return posts.map(postMapper::toDtoShort);
    }

    @Override
    public PostDto create(PostDto postDto) {
        Post createdPost = postServiceApi.create(postMapper.toEntity(postDto));
        return postMapper.toDto(createdPost);
    }

    @Override
    public PostDto update(long id, PostDto postDto) {
        Post updatedPost = postServiceApi.update(id, postMapper.toEntity(postDto));
        return postMapper.toDto(updatedPost);
    }

    @Override
    public void delete(long id) {
        postServiceApi.delete(id);
    }

    @Override
    public ResponseEntity<PostDto> createWithRandomKeanuMoment(PostDto postDto) {
        Post post = postServiceApi.createWithRandomKeanuMoment(postMapper.toEntity(postDto));
        return ResponseEntity.ok(postMapper.toDto(post));
    }

    @Override
    public ResponseEntity<String> createWithRandomKeanuMomentAsync(PostDto postDto) {
        postServiceApi.createWithRandomKeanuMomentAsync(postMapper.toEntity(postDto));
        return ResponseEntity.ok("Post creatin started Asynchronously");
    }

    @Override
    public ResponseEntity<String> createWithRabbit(PostDto postDto) {
        rabbitMqProducer.sendMessage(postMapper.toEntity(postDto));
        return ResponseEntity.ok("Post will be created via RabbitMQ");
    }

    @Override
    public ResponseEntity<PostDto> provideLinkToPost(Long idPost, PostDto postDto) {
        Post updatedPost = postServiceApi.provideLinkToPost(idPost, postMapper.toEntity(postDto));
        return ResponseEntity.ok(postMapper.toDto(updatedPost));
    }
}
