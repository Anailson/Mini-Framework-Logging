package com.framework.logging;

public enum Level {

    DEBUG(10), INFO(20), WARN(30), ERROR(40);
    private final int weight;
    Level(int w) { this.weight = w; }
    public int weight() { return weight; }
}
