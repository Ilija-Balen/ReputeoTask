package com.example.reputeo.taskReputeo.mapper;

import com.example.reputeo.taskReputeo.dto.PostDto;
import com.example.reputeo.taskReputeo.entity.Post;
import com.example.reputeo.taskReputeo.exception.ExceptionMessages;
import com.example.reputeo.taskReputeo.exception.InvalidData;
import com.example.reputeo.taskReputeo.exception.ResourceNotFoundException;
import com.example.reputeo.taskReputeo.repository.UserRepository;
import com.example.reputeo.taskReputeo.utils.PostType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PostMapper extends BaseMapper<Post, PostDto>{

    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    public PostMapper(CommentMapper commentMapper, UserRepository userRepository) {
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
    }

    @Override
    public PostDto toDtoShort(Post fromEntity) {
        PostDto dto = new PostDto();
        dto.setId(fromEntity.getId());
        dto.setContent(fromEntity.getContent());
        dto.setType(fromEntity.getType());
        dto.setCreatedAt(fromEntity.getCreatedAt());
        dto.setCreatedByName(fromEntity.getCreatedByName());
        dto.setUserId(fromEntity.getUser()!= null ? fromEntity.getUser().getId(): null);
        dto.setUrl(fromEntity.getUrl());
        if(fromEntity.getComments() != null){
            dto.setComments(commentMapper.toDto(fromEntity.getComments()));
        }

        return dto;
    }

    @Override
    public Post toEntity(PostDto dto) {
        return populateEntityFromDto(dto, new Post());
    }

    @Override
    public Post populateEntityFromDto(PostDto fromDto, Post toEntity) {

        if(fromDto.getType() != null) toEntity.setType(fromDto.getType());
        if(fromDto.getUrl() != null) toEntity.setUrl(fromDto.getUrl());
        if(fromDto.getContent() != null) toEntity.setContent(fromDto.getContent());
        if(fromDto.getCreatedByName() != null) toEntity.setCreatedByName(fromDto.getCreatedByName());
        if(fromDto.getUserId() != null) toEntity.setUser(userRepository.findById(fromDto.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.toString())));
        if(fromDto.getComments() != null) toEntity.setComments(fromDto.getComments().stream().map(commentMapper::toEntity).toList());
        return toEntity;
    }
}
