package com.framework.logging;

public interface Formatter {

    String format(String message); // compatível com log(String)
    default String format(Level level, String message) { return format(message); }
}
