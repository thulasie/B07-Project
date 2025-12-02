package com.example.smartAir.medicine;

public class ControllerDoseEvent { // TODO CHANGE THIS UPPPP AGAIN!!!!

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
