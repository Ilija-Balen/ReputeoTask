package com.example.reputeo.taskReputeo.service.api;

import com.example.reputeo.taskReputeo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CommentServiceApi {

    Comment respondToPost(Long idPost, Comment comment);
    Comment respondToComment(Long idComment, Comment comment);
    Comment get(long id);
    Page<Comment> getAll(Map<String, Object> params, Pageable pageable);
    Comment create(Comment comment);
    Comment update(long id, Comment comment);
    void delete(long id);
}
