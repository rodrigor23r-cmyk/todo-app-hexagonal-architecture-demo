package com.example.infrastructure.adapter.in.rest.dto;

import java.time.LocalDateTime;

import com.example.domain.model.TaskStatus;

public record TaskResponse(

        long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        String imagePath) {}

    /*
     * @Getter
     * 
     * @Setter
     * 
     * @Builder
     * public class TaskResponse {
     * 
     * private long id;
     * private String title;
     * private String description;
     * private TaskStatus status;
     * private LocalDateTime createdAt;
     * private LocalDateTime completedAt;
     * 
     * 
     * public static TaskResponse from(Task task) {
     * 
     * 
     * return TaskResponse.builder()
     * 
     * .id(task.getId())
     * .title(task.getTitle())
     * .description(task.getDescription())
     * .status(task.getStatus())
     * .createdAt(task.getCreatedAt())
     * .completedAt(task.getCompletedAt())
     * .build();
     * }
     */
