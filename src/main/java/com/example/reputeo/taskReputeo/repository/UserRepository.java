package com.example.reputeo.taskReputeo.repository;


import com.example.reputeo.taskReputeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
