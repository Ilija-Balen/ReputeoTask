package com.example.reputeo.taskReputeo.rest.api;

import com.example.reputeo.taskReputeo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1/user")
public interface UserControllerApi {
    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user);

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long id, @RequestParam("file")MultipartFile file) throws IOException;
}
