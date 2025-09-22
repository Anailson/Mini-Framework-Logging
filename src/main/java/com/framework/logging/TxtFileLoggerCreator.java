package com.framework.logging;

import java.util.List;

public class TxtFileLoggerCreator extends LoggerCreator{

    private final String filePath;
    private final Level minLevel;

    public TxtFileLoggerCreator(String filePath) {
        this(filePath, Level.DEBUG);
    }

    public TxtFileLoggerCreator(String filePath, Level minLevel) {
        this.filePath = filePath;
        this.minLevel = minLevel;
    }

    @Override
    protected Formatter createFormatter() {
        // Padrão com timestamp e nível
        return new PatternFormatter("[%d{yyyy-MM-dd HH:mm:ss}] [%level] %msg");
    }

    @Override
    protected List<Appender> createAppenders() {
        return List.of(new FileAppender(filePath));
    }

    @Override
    protected Level minLevel() {
        return minLevel;
    }
}
