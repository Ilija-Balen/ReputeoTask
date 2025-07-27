package com.example.reputeo.taskReputeo.service.impl;


import com.example.reputeo.taskReputeo.entity.Post;
import com.example.reputeo.taskReputeo.exception.ExceptionMessages;
import com.example.reputeo.taskReputeo.exception.KeanuNotAvailableException;
import com.example.reputeo.taskReputeo.exception.ResourceNotFoundException;
import com.example.reputeo.taskReputeo.repository.PostRepository;
import com.example.reputeo.taskReputeo.utils.RandomWhoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void createWithRandomKeanuMoment_shouldSetContentFromKeanuApiAndSavePost() {
        Post post = new Post();
        List<RandomWhoa> whoaList = List.of(new RandomWhoa());

        ResponseEntity<List<RandomWhoa>> responseEntity = new ResponseEntity<>(whoaList, HttpStatus.OK);
        when(restTemplate.exchange(
                eq("https://whoa.onrender.com/whoas/random"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<RandomWhoa>>() {})
        )).thenReturn(responseEntity);

        Post postWithContent = new Post();
        postWithContent.setContent("RandomWhoa{text='You're Breathtaking'}");

        when(postRepository.save(any(Post.class))).thenReturn(postWithContent);

        Post result = postService.createWithRandomKeanuMoment(post);

        assertNotNull(result);
        assertEquals("RandomWhoa{text='You're Breathtaking'}", result.getContent());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void createWithRandomKeanuMoment_shouldThrowErrorRandomWhoaListEmpty() {
        Post post = new Post();
        List<RandomWhoa> whoaList = null;

        ResponseEntity<List<RandomWhoa>> responseEntity = new ResponseEntity<>(whoaList, HttpStatus.OK);
        when(restTemplate.exchange(
                eq("https://whoa.onrender.com/whoas/random"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<RandomWhoa>>() {})
        )).thenReturn(responseEntity);

        Assertions.assertThrows(KeanuNotAvailableException.class,() -> postService.createWithRandomKeanuMoment(post));
    }

    @Test
    void getReturnsGoodValue(){
        Optional<Post> post = Optional.of(new Post());
        post.get().setId(1L);

        when(postRepository.findById(1L)).thenReturn(post);

        Post returnedPost = postService.get(1L);

        assertEquals(returnedPost.getId(), post.get().getId());
    }

    @Test
    void getThrowsExceptionResourceNotFound(){
        when(postRepository.findById(1L)).thenThrow(new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.toString()));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> postService.get(1L));
    }
}
