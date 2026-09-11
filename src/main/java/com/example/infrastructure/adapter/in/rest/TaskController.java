package com.example.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.port.in.CreateTaskUseCase;
import com.example.domain.model.Task;
import com.example.infrastructure.adapter.in.rest.dto.CreateTaskRequest;
import com.example.infrastructure.adapter.in.rest.dto.TaskResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor 
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;


    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest createTaskRequest) {

        Task task = Task.builder()
            .title(createTaskRequest.getTitle())
            .description(createTaskRequest.getDescription())
            .build();

        Task saved = createTaskUseCase.create(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponse.from(saved));
    }
}
