package com.example.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @EqualsAndHashCode.Include
    private long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String imagePath;

    /* Los métodos siguientes aportan comportamiento. Las reglas de negocio. */

    public void complete() {
        if (this.status == TaskStatus.COMPLETED) {
            throw new IllegalStateException("La tarea ya estaba completada");
        }
        this.status = TaskStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public void reopen() {
        if (this.status == TaskStatus.PENDING) {
            throw new IllegalStateException("La tarea ya estaba pendiente");
        }
        this.status = TaskStatus.PENDING;
        this.completedAt = null;
    }

    public void initDefaults() {
        if (this.status == null)
            this.status = TaskStatus.PENDING;
        if (this.createdAt == null)
            this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String description) {

        this.title = title;
        this.description = description;
    }

    public void changeStatusTo(TaskStatus newStatus) {

        if (newStatus == null || newStatus == this.status)
            return ;

        if (newStatus == TaskStatus.COMPLETED)
            complete();
        else
            reopen();
    }

        public void attachImage(String imagePath) {

        this.imagePath = imagePath;
    }
}
