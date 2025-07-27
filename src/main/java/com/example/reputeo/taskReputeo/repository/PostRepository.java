package com.example.reputeo.taskReputeo.repository;

import com.example.reputeo.taskReputeo.entity.Post;
import com.example.reputeo.taskReputeo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{

    Page<Post> findAllByCreatedByNameLike(String name, Pageable pageable);

    @Query("SELECT p From Post p where LOWER(p.user.name) LIKE LOWER(:name)")
    Page<Post> findAllByNameOfAUser(@Param("name") String name, Pageable pageable);
}
