package com.example.domain.exception;

// @SuppressWarnings("serial")
public class TaskNotFoundException extends RuntimeException {


    /* TaskNotFoundException es una regla de negocio y
    RuntimeException no lleva Spring se puede usar en el dominio */
    public TaskNotFoundException(long id) {
        super("no se ha encontrado la tarea con id: " + id);
    }
    
}
