package com.example.reputeo.taskReputeo.rest.controller;

import com.example.reputeo.taskReputeo.dto.CommentDto;
import com.example.reputeo.taskReputeo.entity.Comment;
import com.example.reputeo.taskReputeo.exception.ResourceNotFoundException;
import com.example.reputeo.taskReputeo.mapper.CommentMapper;
import com.example.reputeo.taskReputeo.rest.api.CommentControllerApi;
import com.example.reputeo.taskReputeo.service.api.CommentServiceApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CommentController implements CommentControllerApi {

    private final CommentServiceApi commentServiceApi;
    private final CommentMapper commentMapper;

    public CommentController(CommentServiceApi commentServiceApi, CommentMapper commentMapper) {
        this.commentServiceApi = commentServiceApi;
        this.commentMapper = commentMapper;
    }


    @Override
    public CommentDto get(long id) throws ResourceNotFoundException {
        return commentMapper.toDto(commentServiceApi.get(id));
    }

    @Override
    public Page<CommentDto> getAll(Map<String, Object> params, Pageable pageable) {
        Page<Comment> comments = commentServiceApi.getAll(params, pageable);
        return comments.map(commentMapper::toDtoShort);
    }

    @Override
    public CommentDto create(CommentDto commentDto) {
        Comment createdComment = commentServiceApi.create(commentMapper.toEntity(commentDto));
        return commentMapper.toDto(createdComment);
    }

    @Override
    public CommentDto update(long id, CommentDto commentDto) {
        Comment updatedComment = commentServiceApi.create(commentMapper.toEntity(commentDto));
        return commentMapper.toDto(updatedComment);
    }

    @Override
    public void delete(long id) {
        commentServiceApi.delete(id);
    }

    @Override
    public ResponseEntity<CommentDto> respondToPost(Long idPost, CommentDto commentDto) {
        Comment comment = commentServiceApi.respondToPost(idPost, commentMapper.toEntity(commentDto));
        return ResponseEntity.ok(commentMapper.toDto(comment));
    }

    @Override
    public ResponseEntity<CommentDto> respondToComment(Long idComment, CommentDto commentDto) {
        Comment comment = commentServiceApi.respondToComment(idComment, commentMapper.toEntity(commentDto));
        return ResponseEntity.ok(commentMapper.toDto(comment));
    }
}
