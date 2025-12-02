package com.example.smartAir.dailies;

public class z_s_l {

    private long timestampMillis;
    private int burdenScore; // 0–3 etc.

    public z_s_l() { }

    public z_s_l(long timestampMillis, int burdenScore) {
        this.timestampMillis = timestampMillis;
        this.burdenScore = burdenScore;
    }

    public long getTimestampMillis() { return timestampMillis; }
    public int getBurdenScore() { return burdenScore; }
}
