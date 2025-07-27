package com.example.reputeo.taskReputeo.mapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMapper<
        E,
        D> {

    public abstract D toDtoShort(final E fromEntity);

    public D toDto(final E fromEntity) {
        return toDtoShort(fromEntity);
    }

    public List<D> toDto(final List<E> entities) {
        return entities.stream()
                .map(this::toDtoShort)
                .collect(Collectors.toList());
    }

    public abstract E toEntity(final D dto);

    public abstract E populateEntityFromDto(final D fromDto, E toEntity);
}
