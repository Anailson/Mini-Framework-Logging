package com.framework.logging;

public interface Appender {

    void append(String formattedMessage);
    default void close() throws Exception {}
}
