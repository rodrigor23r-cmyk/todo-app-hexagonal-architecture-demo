package com.example.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.example.application.port.out.TaskRepositoryPort;
import com.example.domain.model.Task;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaTaskRepositoryAdapter implements TaskRepositoryPort {

    private final SpringDataTaskRepository springDataTaskRepository;
    private final TaskPersistenceMapper taskPersistenceMapper;

    @Override

    public Task save(Task task) {

        task.initDefaults();
        TaskJpaEntity taskJpaEntity = taskPersistenceMapper.toJpaEntity(task);
        TaskJpaEntity saved = springDataTaskRepository.save(taskJpaEntity);

        return taskPersistenceMapper.toDomain(saved);
    }

}
