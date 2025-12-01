package com.example.smartAir.R6model;

public class SymptomLog {

    private long timestampMillis;
    private int burdenScore; // 0–3 etc.

    public SymptomLog() { }

    public SymptomLog(long timestampMillis, int burdenScore) {
        this.timestampMillis = timestampMillis;
        this.burdenScore = burdenScore;
    }

    public long getTimestampMillis() { return timestampMillis; }
    public int getBurdenScore() { return burdenScore; }
}
