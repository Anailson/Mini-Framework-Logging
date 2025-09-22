package com.framework.logging;

import java.util.List;

public class PatternConsoleLoggerCreator extends LoggerCreator{

    private final String pattern;
    private final Level minLevel;

    public PatternConsoleLoggerCreator(String pattern, Level minLevel) {
        this.pattern = pattern;
        this.minLevel = minLevel;
    }

    public PatternConsoleLoggerCreator(String pattern, java.util.logging.Level info, String pattern1, Level minLevel) {
        super();
        this.pattern = pattern1;
        this.minLevel = minLevel;
    }



    @Override
    protected Formatter createFormatter() {
        return new PatternFormatter(pattern);
    }

    @Override
    protected List<Appender> createAppenders() {
        return List.of(new ConsoleAppender());
    }

    @Override
    protected Level minLevel() {
        return minLevel;
    }
}
