package com.example.smartAir.R6model;

public class RescueEvent {

    private long timestampMillis;
    private int dosesTaken;
    private boolean worseAfterDose;

    public RescueEvent() { }  // for Firestore

    public RescueEvent(long timestampMillis, int dosesTaken, boolean worseAfterDose) {
        this.timestampMillis = timestampMillis;
        this.dosesTaken = dosesTaken;
        this.worseAfterDose = worseAfterDose;
    }

    public long getTimestampMillis() { return timestampMillis; }
    public int getDosesTaken() { return dosesTaken; }
    public boolean isWorseAfterDose() { return worseAfterDose; }
}
