package com.framework.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampXmlFormatter implements Formatter{

   private final DateTimeFormatter dtf;

    public TimestampXmlFormatter() {
        this(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    public TimestampXmlFormatter(DateTimeFormatter dtf) {
        this.dtf = dtf;
    }

    @Override
    public String format(String message) {
        return format(Level.INFO, message);
    }

    @Override
    public String format(Level level, String message) {
        String ts = LocalDateTime.now().format(dtf);
        return String.format("<log timestamp=\"%s\" level=\"%s\"><message>%s</message></log>",
                ts, level, escape(message));
    }

    private String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
