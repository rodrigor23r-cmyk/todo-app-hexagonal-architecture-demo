package com.example.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.in.DeleteTaskUseCase;
import com.example.application.port.in.GetTaskUseCase;
import com.example.application.port.in.ListTaskUseCase;
import com.example.application.port.in.UpdateTaskUseCase;
import com.example.application.port.in.UploadTaskImageUseCase;
import com.example.application.port.out.FileStoragePort;
import com.example.application.port.out.TaskRepositoryPort;
import com.example.domain.exception.TaskNotFoundException;
import com.example.domain.model.Task;

import lombok.RequiredArgsConstructor;

/**
 * ¿Es correcto una anotación Spring aquí? (@Service)
 * NO si responden los más puristas.
 * Porque introducimos una dependencia del framework.
 * Si mañana migramos a Quarkus va a ser costoso.
 * TO_DO: ¿Qué debería hacerse para evitar el acoplamiento?
 * Crear un configuration en la capa de infraestructura donde
 * tengamos todos los Bean que se crean cuando se levanta el
 * contexto de spring
 */
@Service
@RequiredArgsConstructor
public class TaskService implements CreateTaskUseCase, GetTaskUseCase, ListTaskUseCase, DeleteTaskUseCase,
        UpdateTaskUseCase, UploadTaskImageUseCase {

    private final TaskRepositoryPort taskRepositoryPort;
    private final FileStoragePort fileStoragePort;

    @Override
    public Task create(Task task) {
        task.initDefaults(); // de motu proprio.
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

    @Override
    public Task update(long id, Task task) {

        Task foundedTask = taskRepositoryPort.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        foundedTask.update(task.getTitle(), task.getDescription());

        foundedTask.changeStatusTo(task.getStatus());

        return taskRepositoryPort.save(foundedTask);

    }

    @Override
    public Task uploadImage(long id, String fileName, byte[] content) {

        Task task = taskRepositoryPort.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        
        String imagePath = fileStoragePort.store(fileName, content);

        task.attachImage(imagePath);

        return taskRepositoryPort.save(task);
    }

}
