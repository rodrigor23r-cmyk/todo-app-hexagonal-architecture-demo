package com.example.infrastructure.adapter.out.persistence;

import org.mapstruct.Mapper;

import com.example.domain.model.Task;


@Mapper(componentModel = "spring")
public interface TaskPersistenceMapper {

    Task toDomain(TaskJpaEntity taskJpaEntity);

    TaskJpaEntity toJpaEntity(Task task);
}

/*
@Component 
public class TaskPersistenceMapper {

    public Task toDomain(TaskJpaEntity taskJpaEntity) {
        
        if(taskJpaEntity == null)
            return null;
        return Task.builder()
            .id(taskJpaEntity.getId())
            .title(taskJpaEntity.getTitle())
            .description(taskJpaEntity.getDescription())
            .status(taskJpaEntity.getStatus())
            .createdAt(taskJpaEntity.getCreatedAt())
            .completedAt(taskJpaEntity.getCompletedAt())
            .build();
    }

    public TaskJpaEntity toJpaEntity(Task task) {

        if (task == null)
            return null;
        
        return TaskJpaEntity.builder()
            .id(task.getId())
            .title(task.getTitle())
            .description(task.getDescription())
            .status(task.getStatus())
            .createdAt(task.getCreatedAt())
            .completedAt(task.getCompletedAt())
            .build();
    }
}
*/