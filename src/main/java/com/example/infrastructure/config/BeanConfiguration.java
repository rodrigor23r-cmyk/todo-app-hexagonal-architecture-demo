package com.example.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.application.port.out.FileStoragePort;
import com.example.application.port.out.TaskRepositoryPort;
import com.example.application.service.TaskService;

@Configuration
public class BeanConfiguration {

    @Bean
    TaskService taskService(TaskRepositoryPort taskRepositoryPort, FileStoragePort fileStoragePort) {

        return new TaskService(taskRepositoryPort, fileStoragePort);
    }
}
/**
 * Crea un configuration en la capa de infraestructura donde
 * tengamos todos los Bean que se crean cuando se levanta el
 * contexto de spring:
 * 1. Quitar @Service (y su import) de TaskService — dos líneas menos.
   2. Crear una clase nueva @Configuration en infraestructura, con un método @Bean 
      que construya el TaskService pasándole los puertos por constructor.
   3. Nada más. El controller sigue inyectando las interfaces de caso de uso igual que ahora.
 */