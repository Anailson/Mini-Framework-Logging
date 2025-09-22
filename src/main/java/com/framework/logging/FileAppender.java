package com.framework.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileAppender implements Appender, AutoCloseable{

    protected final Path path;

    public FileAppender(String filePath) {
        this.path = Path.of(filePath);
        try {
            Path parent = path.getParent();
            if (parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível inicializar o arquivo de log: " + filePath, e);
        }
    }

    @Override
    public synchronized void append(String formattedMessage) {
        try {
            Files.writeString(path, formattedMessage + System.lineSeparator(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao escrever no log: " + path, e);
        }
    }

    @Override
    public void close() throws Exception {
        Appender.super.close();
    }
}
