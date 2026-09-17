package com.example.domain.model;

import java.time.LocalDateTime;

/* Comentar desde aquí para implementar record 
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

    // Los métodos siguientes aportan comportamiento. Las reglas de negocio. 

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
 Fin de comentario para probar record. */

/* */
public record Task(
        long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        String imagePath) {

    // constructor compacto: sustituye a initDefault

    public Task {
        if (status == null)
            status = TaskStatus.PENDING;
        if (createdAt == null)
            createdAt = LocalDateTime.now();
    }

    public Task complete() {
        if (status == TaskStatus.COMPLETED)
            throw new IllegalStateException("la tarea ya estaba completa");
        return new Task(id, title, description, TaskStatus.COMPLETED, createdAt, LocalDateTime.now(), imagePath);

    }

    public Task reopen() {
        if (status == TaskStatus.PENDING)
            throw new IllegalStateException("la tarea ya estaba pendiente");
        return new Task(id, title, description, TaskStatus.PENDING, createdAt, null, imagePath);

    }

    public Task changeStatusTo(TaskStatus newStatus) {
        if (newStatus == null || newStatus == status)
            return this;
        return newStatus == TaskStatus.COMPLETED ? complete() : reopen();
    }

    public Task update(String title, String description) {
        return new Task(id, title, description, status, createdAt, completedAt, imagePath);
    }

    public Task attachImage(String imagePath) {
        return new Task(id, title, description, status, createdAt, completedAt, imagePath);
    }

    /*
     * Task es una entidad: dos instancias con el mismo id son la misma tarea,
     * aunque sus demás campos difieran. Por eso no vale el equals que genera
     * el record, que compara los siete componentes.
     * Para asemejar el comportamiento que tenía con las líneas 18 y 21 de la clase Task
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Task other && this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

}
/* */