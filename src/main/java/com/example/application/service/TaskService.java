package com.example.application.service;

import java.util.List;


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
 * contexto de spring:
 * 1. Quitar @Service (y su import) de TaskService — dos líneas menos.
   2. Crear una clase nueva @Configuration en infraestructura, con un método @Bean 
      que construya el TaskService pasándole los puertos por constructor.
   3. Nada más. El controller sigue inyectando las interfaces de caso de uso igual que ahora.
 */


// Quitamos la anotación de springboot: @Service
@RequiredArgsConstructor
public class TaskService implements CreateTaskUseCase, GetTaskUseCase, ListTaskUseCase, DeleteTaskUseCase,
        UpdateTaskUseCase, UploadTaskImageUseCase {

    private final TaskRepositoryPort taskRepositoryPort;
    private final FileStoragePort fileStoragePort;

    @Override
    public Task create(Task task) {
        // task.initDefaults(); // comentar para la versión record de Task
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

        Task task = taskRepositoryPort.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        taskRepositoryPort.deleteById(id);

        // fileStoragePort.delete(task.getImagePath()); // comentar para record.
         fileStoragePort.delete(task.imagePath());

    }

    @Override
    public Task update(long id, Task task) {

        Task foundedTask = taskRepositoryPort.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        // foundedTask.update(task.getTitle(), task.getDescription()); // comentar para record.
         Task updatedTask = foundedTask.update(task.title(), task.description()).changeStatusTo(task.status());
        
        // foundedTask.changeStatusTo(task.getStatus()); // comentar para record.

        // return taskRepositoryPort.save(foundedTask); // comentar para record.
         return taskRepositoryPort.save(updatedTask);

    }

    @Override
    public Task uploadImage(long id, String fileName, byte[] content) {

        Task task = taskRepositoryPort.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        // String previousImage = task.getImagePath(); // comentar para record.
         String previousImage = task.imagePath();

        String imagePath = fileStoragePort.store(fileName, content);

        task.attachImage(imagePath);

        // Task saved = taskRepositoryPort.save(task); // comentar para record.
         Task saved = taskRepositoryPort.save(task.attachImage(imagePath));

        fileStoragePort.delete(previousImage);

        return saved;
    }

}
