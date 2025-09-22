package com.framework.logging;


public interface Logger {
    void log(String message);
    void log(Level level, String message);

    default void info(String message)  { log(Level.INFO,  message); }
    default void warn(String message)  { log(Level.WARN,  message); }
    default void error(String message) { log(Level.ERROR, message); }
    default void debug(String message) { log(Level.DEBUG, message); }
}
