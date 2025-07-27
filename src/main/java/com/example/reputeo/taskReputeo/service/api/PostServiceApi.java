package com.example.reputeo.taskReputeo.service.api;

import com.example.reputeo.taskReputeo.dto.PostDto;
import com.example.reputeo.taskReputeo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PostServiceApi {

    Post createWithRandomKeanuMoment(Post post);
    CompletableFuture<Post> createWithRandomKeanuMomentAsync(Post post);
    Post provideLinkToPost(Long idPost, Post post);
    Post get(long id);
    Page<Post> getAll(Map<String, Object> params, Pageable pageable);
    Post create(Post post);
    Post update(long id, Post post);
    void delete(long id);
}
