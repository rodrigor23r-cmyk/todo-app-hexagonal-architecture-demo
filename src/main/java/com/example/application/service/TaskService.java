package com.example.application.service;

import org.springframework.stereotype.Service;

import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.out.TaskRepositoryPort;
import com.example.domain.model.Task;

import lombok.RequiredArgsConstructor;

/** ¿Es correcto una anotación Spring aquí? (@Service)
NO si responden los más puristas.
Porque introducimos una dependencia del framework. 
Si mañana migramos a Quarkus va a ser costoso.
TODO: ¿Qué debería hacerse para evitar el acoplamiento?
Crear un configuration en la capa de infraestructura donde 
tengamos todos los Bean que se crean cuando se levanta el 
contexto de spring*/
@Service 
@RequiredArgsConstructor 
public class TaskService implements CreateTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;
    @Override
    public Task create(Task task) {
        
        return taskRepositoryPort.save(task);
    }

}
