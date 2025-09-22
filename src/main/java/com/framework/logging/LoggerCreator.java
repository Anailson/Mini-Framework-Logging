package com.framework.logging;

import java.util.List;

public abstract class LoggerCreator {

    public Logger createLogger() {
        Formatter f = createFormatter();
        List<Appender> a = createAppenders();
        Level min = minLevel();
        return new DefaultLogger(f, a, min);
    }

    protected abstract Formatter createFormatter();
    protected abstract List<Appender> createAppenders();
    protected Level minLevel() { return Level.DEBUG; }
}
