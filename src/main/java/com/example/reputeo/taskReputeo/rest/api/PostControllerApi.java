package com.example.reputeo.taskReputeo.rest.api;

import com.example.reputeo.taskReputeo.dto.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/post")
public interface PostControllerApi extends BaseControllerApi<PostDto>{

    @PostMapping("/randomKeanuMoment")
    ResponseEntity<PostDto> createWithRandomKeanuMoment(@RequestBody PostDto postDto);

    @PostMapping("/randomKeanuMoment/async")
    ResponseEntity<String> createWithRandomKeanuMomentAsync(@RequestBody PostDto postDto);

    @PostMapping("/rabbit")
    ResponseEntity<String> createWithRabbit(@RequestBody PostDto postDto);


    @PutMapping("/provideLink/{idPost}")
    ResponseEntity<PostDto> provideLinkToPost(@PathVariable Long idPost, @RequestBody PostDto postDto);

}
