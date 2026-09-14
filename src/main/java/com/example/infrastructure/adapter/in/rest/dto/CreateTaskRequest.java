package com.example.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(

    @NotBlank(message = "El título es obligatorio")
    String title,
    @NotBlank(message = "La descripción es obligatoria")
    String description
    ) {}

/*
@Getter 
@Setter
public class CreateTaskRequest {

    @NotBlank(message = "El título es obligatorio") 
    private String title;
    @NotBlank(message = "descripcion obligatoria")
    private String description;

}
    
*/
