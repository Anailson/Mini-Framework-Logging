package com.framework.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternFormatter implements Formatter{

    private final String pattern;
    private static final Pattern DT = Pattern.compile("%d\\{([^}]+)}");

    public PatternFormatter(String pattern) {
        if (pattern == null || pattern.isBlank()) {
            throw new IllegalArgumentException("pattern não pode ser vazio");
        }
        this.pattern = pattern;
    }

    @Override
    public String format(String message) {
        return format(Level.INFO, message);
    }

    @Override
    public String format(Level level, String message) {
        String out = pattern;

        // %d{...}
        Matcher m = DT.matcher(out);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String p = m.group(1);
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern(p));
            m.appendReplacement(sb, Matcher.quoteReplacement(ts));
        }
        m.appendTail(sb);
        out = sb.toString();

        // %level
        out = out.replace("%level", level.toString());

        // %msg
        out = out.replace("%msg", message);

        return out;
    }
}
