package com.example.reputeo.taskReputeo.service.impl;

import com.example.reputeo.taskReputeo.dto.PostDto;
import com.example.reputeo.taskReputeo.entity.Post;
import com.example.reputeo.taskReputeo.exception.ExceptionMessages;
import com.example.reputeo.taskReputeo.exception.InvalidData;
import com.example.reputeo.taskReputeo.exception.KeanuNotAvailableException;
import com.example.reputeo.taskReputeo.exception.ResourceNotFoundException;
import com.example.reputeo.taskReputeo.repository.PostRepository;
import com.example.reputeo.taskReputeo.service.api.PostServiceApi;
import com.example.reputeo.taskReputeo.utils.LinkUtil;
import com.example.reputeo.taskReputeo.utils.MarkDownUtil;
import com.example.reputeo.taskReputeo.utils.PostType;
import com.example.reputeo.taskReputeo.utils.RandomWhoa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class PostService implements PostServiceApi {

    private static final String RANDOM_KEANU_MOMENT_URL = "https://whoa.onrender.com/whoas/random";

    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    public PostService(PostRepository postRepository, RestTemplate restTemplate) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Post createWithRandomKeanuMoment(Post post) {
        ResponseEntity<List<RandomWhoa>> response = restTemplate.exchange(
                RANDOM_KEANU_MOMENT_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RandomWhoa>>() {}
        );

        List<RandomWhoa> randomWhoaList = response.getBody();
        if(randomWhoaList == null){
            throw new KeanuNotAvailableException(ExceptionMessages.KEANU_NOT_AVAILABLE_EXCEPTION.toString());
        }
        post.setContent(randomWhoaList.getFirst().toString());
        return create(post);
    }

    @Override
    @Async
    public CompletableFuture<Post> createWithRandomKeanuMomentAsync(Post post) {
        ResponseEntity<List<RandomWhoa>> response = restTemplate.exchange(
                RANDOM_KEANU_MOMENT_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RandomWhoa>>() {}
        );

        List<RandomWhoa> randomWhoaList = response.getBody();
        if(randomWhoaList == null){
            throw new KeanuNotAvailableException(ExceptionMessages.KEANU_NOT_AVAILABLE_EXCEPTION.toString());
        }
        post.setContent(randomWhoaList.getFirst().toString());
        Post createdPost = create(post);
        return CompletableFuture.completedFuture(createdPost) ;
    }

    @Override
    public Post provideLinkToPost(Long idPost, Post post) {
        if(post.getUrl() == null){
            throw new InvalidData(ExceptionMessages.INVALID_DATA.toString());
        }
        return update(idPost, post);
    }

    @Override
    public Post get(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.toString()));
    }

    @Override
    public Page<Post> getAll(Map<String, Object> params, Pageable pageable) {
        if(params.containsKey("createdByName")){
            String name = (String) params.get("createdByName");
            return postRepository.findAllByCreatedByNameLike("%"+ name + "%" , pageable);
        }
        return postRepository.findAll(pageable);
    }

    @Override
    public Post create(Post post) {
        validateData(post);
        post.setCreatedAt(LocalDateTime.now());
        if(post.getUrl() != null) {
            post.setUrl(LinkUtil.linkify(post.getUrl()));
        }
        log.info(String.format("Post is created -> %s", post.toString()));
        return postRepository.save(post);
    }

    @Override
    public Post update(long id, Post post) {
        Post selectedPost = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.toString()));

        if(post.getType() != null && (selectedPost.getType() == null || selectedPost.getType() != post.getType())){
            if(post.getType().equals(PostType.MARKDOWN)){
                post.setContent(MarkDownUtil.markdownToHtml(post.getContent()));
            }

            if(post.getType().equals(PostType.PLAIN_TEXT)){
                post.setContent(MarkDownUtil.htmlToMarkdown(post.getContent()));
            }
        }
        if(post.getType() != null) selectedPost.setType(post.getType());
        if(post.getContent() != null) selectedPost.setContent(post.getContent());
        if(post.getCreatedByName() != null) selectedPost.setCreatedByName(post.getCreatedByName());
        if(post.getUrl()!=null){
            selectedPost.setUrl(LinkUtil.linkify(post.getUrl()));
        }

        return postRepository.save(selectedPost);
    }

    @Override
    public void delete(long id) {
        postRepository.deleteById(id);
    }

    private void validateData(Post post){
        if(post.getContent() == null){
            throw new InvalidData(ExceptionMessages.INVALID_DATA.toString());
        }
    }
}
