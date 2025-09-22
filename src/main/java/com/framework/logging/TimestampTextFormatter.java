package com.framework.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampTextFormatter implements Formatter{

    private final DateTimeFormatter dtf;

    public TimestampTextFormatter() {
        this(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public TimestampTextFormatter(DateTimeFormatter dtf) {
        this.dtf = dtf;
    }

    @Override
    public String format(String message) {
        String ts = LocalDateTime.now().format(dtf);
        return "[" + ts + "] " + message;
    }

    @Override
    public String format(Level level, String message) {
        String ts = LocalDateTime.now().format(dtf);
        return "[" + ts + "] [" + level + "] " + message;
    }
}
