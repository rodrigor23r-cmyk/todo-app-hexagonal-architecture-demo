package com.example.infrastructure.adapter.in.rest;

import org.mapstruct.Mapper;

import com.example.domain.model.Task;
import com.example.infrastructure.adapter.in.rest.dto.CreateTaskRequest;
import com.example.infrastructure.adapter.in.rest.dto.TaskResponse;
import com.example.infrastructure.adapter.in.rest.dto.UpdateTaskRequest;

@Mapper(componentModel = "spring")
public interface TaskRestMapper {

    
    Task toDomain(CreateTaskRequest createTaskRequest);

    Task toDomain(UpdateTaskRequest updateTaskRequest);
    
    TaskResponse toResponse(Task task);

}
