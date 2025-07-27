package com.example.reputeo.taskReputeo.mapper;

import com.example.reputeo.taskReputeo.dto.CommentDto;
import com.example.reputeo.taskReputeo.entity.Comment;
import com.example.reputeo.taskReputeo.exception.ExceptionMessages;
import com.example.reputeo.taskReputeo.exception.ResourceNotFoundException;
import com.example.reputeo.taskReputeo.repository.CommentRepository;
import com.example.reputeo.taskReputeo.repository.UserRepository;
import org.springframework.stereotype.Component;


@Component
public class CommentMapper extends BaseMapper<Comment, CommentDto>{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentMapper(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto toDtoShort(Comment fromEntity) {
        CommentDto dto = new CommentDto();

        dto.setId(fromEntity.getId());
        dto.setContent(fromEntity.getContent());
        dto.setParentId(fromEntity.getParent()!= null ? fromEntity.getParent().getId() : null);
        dto.setCreatedAt(fromEntity.getCreatedAt());
        dto.setUserId(fromEntity.getUser()!= null ? fromEntity.getUser().getId(): null);
        dto.setReplies(
                fromEntity.getReplies().stream()
                        .map(this::toDtoShort)
                        .toList()
        );


        return dto;
    }

    @Override
    public Comment toEntity(CommentDto dto) {
        return populateEntityFromDto(dto, new Comment());
    }

    @Override
    public Comment populateEntityFromDto(CommentDto fromDto, Comment toEntity) {

        if(fromDto.getContent() != null) toEntity.setContent(fromDto.getContent());
        if(fromDto.getParentId() != null) toEntity.setParent(commentRepository.findById(fromDto.getParentId()).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.toString())));
        if(fromDto.getUserId() != null) toEntity.setUser(userRepository.findById(fromDto.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.toString())));
        if(fromDto.getReplies() != null){
            toEntity.setReplies(fromDto.getReplies().stream()
                    .map(this::toEntity)
                    .toList());
        }

        return toEntity;
    }
}
