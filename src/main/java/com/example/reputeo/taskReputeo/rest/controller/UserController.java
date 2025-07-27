package com.example.reputeo.taskReputeo.rest.controller;

import com.example.reputeo.taskReputeo.entity.User;
import com.example.reputeo.taskReputeo.repository.UserRepository;
import com.example.reputeo.taskReputeo.rest.api.UserControllerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class UserController implements UserControllerApi {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<User> create(User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @Override
    public ResponseEntity<String> uploadProfilePicture(Long id, MultipartFile file) throws IOException {
        User user = userRepository.findById(id).orElseThrow();

        String fileName = "user_" + id + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String url = "http://localhost:8080/images/" + fileName;
        user.setProfilePicture(url);
        userRepository.save(user);

        return ResponseEntity.ok(url);
    }
}
