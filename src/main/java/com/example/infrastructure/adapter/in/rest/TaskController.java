package com.example.infrastructure.adapter.in.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.in.DeleteTaskUseCase;
import com.example.application.port.in.GetTaskUseCase;
import com.example.application.port.in.ListTaskUseCase;
import com.example.application.port.in.UpdateTaskUseCase;
import com.example.application.port.in.UploadTaskImageUseCase;
import com.example.domain.model.Task;
import com.example.infrastructure.adapter.in.rest.dto.CreateTaskRequest;
import com.example.infrastructure.adapter.in.rest.dto.TaskResponse;
import com.example.infrastructure.adapter.in.rest.dto.UpdateTaskRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController 
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor 
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final ListTaskUseCase listTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final TaskRestMapper taskRestMapper;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final UploadTaskImageUseCase uploadTaskImageUseCase;


    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest createTaskRequest) {

        Task task = taskRestMapper.toDomain(createTaskRequest);
        /*Task task = Task.builder()
            .title(createTaskRequest.getTitle())
            .description(createTaskRequest.getDescription())
            .build();*/

        Task saved = createTaskUseCase.create(task);

     // return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponse.from(saved));
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRestMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable long id) {

        Task got = getTaskUseCase.getById(id);
    
     return ResponseEntity.ok(taskRestMapper.toResponse(got)); // dará un 200 porque no está creado (201)
    
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAll() {

        List<TaskResponse> tasks = listTaskUseCase.findAll().stream().map(taskRestMapper::toResponse).toList();
     // List<TaskResponse> tasks = listTaskUseCase.findAll().stream().map(TaskResponse::from).toList();
    
        return ResponseEntity.ok(tasks);
    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {

        deleteTaskUseCase.deleteById(id);
        
        return ResponseEntity.noContent().build();

    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable long id, @Valid @RequestBody UpdateTaskRequest updateTaskRequest) {

        Task task = taskRestMapper.toDomain(updateTaskRequest);

        Task updated = updateTaskUseCase.update(id, task);

        return ResponseEntity.ok(taskRestMapper.toResponse(updated));
    }

    
    @PostMapping("/{id}/image")
    public ResponseEntity<TaskResponse> uploadImage(@PathVariable long id, @RequestParam("image") MultipartFile image) throws IOException {

        Task task = uploadTaskImageUseCase.uploadImage(id, image.getOriginalFilename(), image.getBytes());

        return ResponseEntity.ok(taskRestMapper.toResponse(task));
    }
}
