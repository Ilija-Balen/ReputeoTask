package com.example.reputeo.taskReputeo.rest.api;

import com.example.reputeo.taskReputeo.dto.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/comment")
public interface CommentControllerApi extends BaseControllerApi<CommentDto> {

    @PostMapping("/respondToPost/{idPost}")
    ResponseEntity<CommentDto> respondToPost(@PathVariable Long idPost, @RequestBody CommentDto commentDto);

    @PostMapping("/respondToComment/{idComment}")
    ResponseEntity<CommentDto> respondToComment(@PathVariable Long idComment, @RequestBody CommentDto commentDto);
}
