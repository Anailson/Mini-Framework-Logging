package com.framework.logging;

import java.util.List;

public class RollingTxtLoggerCreator extends LoggerCreator{

    private final String filePath;
    private final long maxBytes;
    private final Level minLevel;

    public RollingTxtLoggerCreator(String filePath, long maxBytes, Level minLevel) {
        this.filePath = filePath;
        this.maxBytes = maxBytes;
        this.minLevel = minLevel;
    }

    @Override
    protected Formatter createFormatter() {
        return new PatternFormatter("[%d{yyyy-MM-dd HH:mm:ss}] [%level] %msg");
    }

    @Override
    protected List<Appender> createAppenders() {
        return List.of(new RollingFileAppender(filePath, maxBytes));
    }

    @Override
    protected Level minLevel() {
        return minLevel;
    }
}
