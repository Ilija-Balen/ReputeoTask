package com.example.reputeo.taskReputeo.service.impl;

import com.example.reputeo.taskReputeo.entity.Comment;
import com.example.reputeo.taskReputeo.entity.Post;
import com.example.reputeo.taskReputeo.exception.ExceptionMessages;
import com.example.reputeo.taskReputeo.exception.InvalidData;
import com.example.reputeo.taskReputeo.exception.ResourceNotFoundException;
import com.example.reputeo.taskReputeo.repository.CommentRepository;
import com.example.reputeo.taskReputeo.service.api.CommentServiceApi;
import com.example.reputeo.taskReputeo.service.api.PostServiceApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CommentService implements CommentServiceApi {

    private final CommentRepository commentRepository;
    private final PostServiceApi postServiceApi;

    public CommentService(CommentRepository commentRepository, PostServiceApi postServiceApi) {
        this.commentRepository = commentRepository;
        this.postServiceApi = postServiceApi;
    }

    @Override
    public Comment respondToPost(Long idPost, Comment comment) {
        Post post = postServiceApi.get(idPost);
        comment.setPost(post);
        post.getComments().add(comment);

        return create(comment);
    }

    @Override
    public Comment respondToComment(Long idComment, Comment comment) {
        Comment parent = get(idComment);

        comment.setParent(parent);
        parent.getReplies().add(comment);
        comment.setPost(parent.getPost());

        return create(comment);
    }

    @Override
    public Comment get(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.toString()));
    }

    @Override
    public Page<Comment> getAll(Map<String, Object> params, Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment create(Comment comment) {
        validateData(comment);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(long id, Comment comment) {
        Comment selectedComment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.toString()));

        selectedComment.setContent(comment.getContent());
        return commentRepository.save(selectedComment);
    }

    @Override
    public void delete(long id) {
        commentRepository.findById(id);
    }

    private void validateData(Comment comment){
        if(comment.getContent() == null){
            throw new InvalidData(ExceptionMessages.INVALID_DATA.toString());
        }
    }
}
