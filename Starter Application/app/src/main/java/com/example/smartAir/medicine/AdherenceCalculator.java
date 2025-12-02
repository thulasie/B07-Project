package com.example.smartAir.medicine;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdherenceCalculator {

    public static double calculateAdherencePercent(
            List<ControllerDoseEvent> doses,
            int expectedDosesPerDay,
            long fromMillis,
            long toMillis
    ) {
        if (expectedDosesPerDay <= 0) return 0.0;

        long days = 1 + TimeUnit.MILLISECONDS.toDays(toMillis - fromMillis);
        if (days <= 0) return 0.0;

        int expectedTotal = (int) (expectedDosesPerDay * days);
        int taken = 0;

        for (ControllerDoseEvent ev : doses) {
            if (ev.isTaken()
                    && ev.getTimestampMillis() >= fromMillis
                    && ev.getTimestampMillis() <= toMillis) {
                taken++;
            }
        }

        if (expectedTotal == 0) return 0.0;
        return taken * 100.0 / expectedTotal;
    }

    public static String calculateTodayZone(int rescuesToday, int symptomScoreToday) {
        if (rescuesToday >= 4 || symptomScoreToday >= 3) return "RED";
        if (rescuesToday >= 2 || symptomScoreToday == 2) return "YELLOW";
        return "GREEN";
    }
}
