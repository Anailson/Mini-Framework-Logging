package com.framework.logging;

import java.util.List;

public class XmlFileLoggerCreator extends LoggerCreator{

    private final String filePath;

    public XmlFileLoggerCreator(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Formatter createFormatter() {
        return new TimestampXmlFormatter();
    }

    @Override
    protected List<Appender> createAppenders() {
        return List.of(new FileAppender(filePath));
    }
}
