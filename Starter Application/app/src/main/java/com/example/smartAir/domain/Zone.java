package com.example.smartAir.domain;

public enum Zone {
    RED, GREEN, YELLOW;

    public static Zone calculateZone(double pEF, double personalBest) {
        double percent = pEF / personalBest;

        if (percent >= 0.8) {
            return GREEN;
        } else if (percent >= 0.5) {
            return YELLOW;
        } else {
            return RED;
        }
    }
}
