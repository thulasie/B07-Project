package com.example.smartAir.R6model;

public class ControllerDoseEvent {

    private long timestampMillis;
    private boolean taken;

    public ControllerDoseEvent() { }

    public ControllerDoseEvent(long timestampMillis, boolean taken) {
        this.timestampMillis = timestampMillis;
        this.taken = taken;
    }

    public long getTimestampMillis() { return timestampMillis; }
    public boolean isTaken() { return taken; }
}
