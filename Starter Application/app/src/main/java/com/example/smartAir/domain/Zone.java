package com.example.smartAir.domain;

import androidx.annotation.Nullable;

public enum Zone {
    RED, GREEN, YELLOW;

    public static Zone calculateZone(@Nullable Double pEF, double personalBest) {
        if (pEF == null) return null;

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
