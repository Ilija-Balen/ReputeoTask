package com.example.reputeo.taskReputeo.repository;

import com.example.reputeo.taskReputeo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
