package com.example.infrastructure.adapter.out.storage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.application.port.out.FileStoragePort;

@Component
public class FileSystemStorageAdapter implements FileStoragePort {

    private final Path uploadsDir;

    public FileSystemStorageAdapter(@Value("${app.uploads.dir}") String uploadsDir) {

        this.uploadsDir = Path.of(uploadsDir);
    }

    @Override
    public String store(String fileName, byte[] content) {

        String storedName = UUID.randomUUID() + extensionOf(fileName);

        try {
            Files.createDirectories(uploadsDir);
            Files.write(uploadsDir.resolve(storedName), content);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo guardar la imagen de la tarea", e);
        }

        return storedName;
    }

    /*
     * El nombre que manda el cliente nunca se usa para construir la ruta:
     * solo se conserva la extensión, y únicamente si es alfanumérica.
     */
    private String extensionOf(String fileName) {

        if (fileName == null)
            return "";

        int dot = fileName.lastIndexOf('.');

        if (dot < 0)
            return "";

        String extension = fileName.substring(dot + 1);

        return extension.matches("[A-Za-z0-9]{1,5}") ? "." + extension.toLowerCase() : "";
    }

    @Override
    public void delete(String storedName) {

        if (storedName == null)
            return;

        try {
            Files.deleteIfExists(uploadsDir.resolve(storedName));
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo borrar la imagen de la tarea", e);
        }
    }
}
