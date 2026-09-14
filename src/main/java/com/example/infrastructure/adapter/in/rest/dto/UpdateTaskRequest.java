package com.example.infrastructure.adapter.in.rest.dto;

import com.example.domain.model.TaskStatus;

import jakarta.validation.constraints.NotBlank;

public record UpdateTaskRequest(

    @NotBlank(message = "El título es obligatorio")
    String title,
    
    @NotBlank(message = "La descripción es obligatoria")
    String description,

    TaskStatus status

) {

}
