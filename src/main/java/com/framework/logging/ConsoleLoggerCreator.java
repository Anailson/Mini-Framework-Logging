package com.framework.logging;

import java.util.List;

public class ConsoleLoggerCreator extends LoggerCreator{

    @Override
    protected Formatter createFormatter() {
        return new TimestampTextFormatter();
    }

    @Override
    protected List<Appender> createAppenders() {
        return List.of(new ConsoleAppender());
    }
}
