package com.framework.logging;

import java.util.List;
import java.util.Objects;

public class DefaultLogger implements Logger, AutoCloseable{

    private final Formatter formatter;
    private final List<Appender> appenders;
    private final Level minLevel;

    public DefaultLogger(Formatter formatter, List<Appender> appenders) {
        this(formatter, appenders, Level.DEBUG);
    }

    public DefaultLogger(Formatter formatter, List<Appender> appenders, Level minLevel) {
        this.formatter = Objects.requireNonNull(formatter, "formatter");
        if (appenders == null || appenders.isEmpty()) {
            throw new IllegalArgumentException("É preciso pelo menos um Appender");
        }
        this.appenders = List.copyOf(appenders);
        this.minLevel = Objects.requireNonNull(minLevel, "minLevel");
    }

    @Override
    public void log(String message) {
        // compatibilidade: usa INFO como default
        log(Level.INFO, message);
    }

    @Override
    public void log(Level level, String message) {
        if (level.weight() < minLevel.weight()) return;
        String formatted = formatter.format(level, message);
        for (Appender a : appenders) {
            a.append(formatted);
        }
    }

    @Override
    public void close() throws Exception {
        for (Appender a : appenders) {
            a.close();
        }
    }
}
