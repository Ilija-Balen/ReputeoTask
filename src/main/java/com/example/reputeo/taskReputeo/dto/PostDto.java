package com.example.reputeo.taskReputeo.dto;

import com.example.reputeo.taskReputeo.utils.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String createdByName;
    private String url;
    private PostType type;
    private Long userId;
    private List<CommentDto> comments;
}
