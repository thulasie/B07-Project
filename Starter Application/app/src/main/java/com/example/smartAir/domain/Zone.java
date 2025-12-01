package com.example.smartAir.domain;

import androidx.annotation.Nullable;

public enum Zone {
    NOT_APPLICABLE, RED, GREEN, YELLOW;

    public static Zone calculateZone(@Nullable Float pEF, Float personalBest) {
        if (pEF == null || personalBest == null) return NOT_APPLICABLE;

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
