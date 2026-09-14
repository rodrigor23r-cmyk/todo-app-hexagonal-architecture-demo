package com.example.infrastructure.adapter.in.rest;

import org.mapstruct.Mapper;

import com.example.domain.model.Task;
import com.example.infrastructure.adapter.in.rest.dto.CreateTaskRequest;
import com.example.infrastructure.adapter.in.rest.dto.TaskResponse;

@Mapper(componentModel = "spring")
public interface TaskRestMapper {

    Task toDomain(CreateTaskRequest createTaskRequest);

    TaskResponse toResponse(Task task);
}
