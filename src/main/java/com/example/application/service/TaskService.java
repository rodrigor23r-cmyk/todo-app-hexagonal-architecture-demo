package com.example.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.in.DeleteTaskUseCase;
import com.example.application.port.in.GetTaskUseCase;
import com.example.application.port.in.ListTaskUseCase;
import com.example.application.port.out.TaskRepositoryPort;
import com.example.domain.exception.TaskNotFoundException;
import com.example.domain.model.Task;

import lombok.RequiredArgsConstructor;

/** ¿Es correcto una anotación Spring aquí? (@Service)
NO si responden los más puristas.
Porque introducimos una dependencia del framework. 
Si mañana migramos a Quarkus va a ser costoso.
TO_DO: ¿Qué debería hacerse para evitar el acoplamiento?
Crear un configuration en la capa de infraestructura donde 
tengamos todos los Bean que se crean cuando se levanta el 
contexto de spring*/
@Service 
@RequiredArgsConstructor 
public class TaskService implements CreateTaskUseCase, GetTaskUseCase, ListTaskUseCase, DeleteTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    public Task create(Task task) {
        
        return taskRepositoryPort.save(task);
    }
    @Override
    public Task getById(long id) {
       
        return taskRepositoryPort.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }
    @Override
    public List<Task> findAll() {
        
        return taskRepositoryPort.findAll();
    }
    @Override
    public void deleteById(long id) {
        
        taskRepositoryPort.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        taskRepositoryPort.deleteById(id);
        
    }



}
