package com.framework.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RollingFileAppender extends FileAppender{

    private final long maxBytes;

    public RollingFileAppender(String filePath, long maxBytes) {
        super(filePath);
        this.maxBytes = maxBytes;
    }

    @Override
    public synchronized void append(String formattedMessage) {
        try {
            rollIfNeeded();
        } catch (IOException e) {
            throw new RuntimeException("Falha no roll do log: " + path, e);
        }
        super.append(formattedMessage);
    }

    private void rollIfNeeded() throws IOException {
        if (Files.size(path) < maxBytes) return;
        String stamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path rotated = Path.of(path.toString() + "." + stamp);
        Files.move(path, rotated, StandardCopyOption.ATOMIC_MOVE);
        Files.createFile(path);
    }
}
