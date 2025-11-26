package com.example.smartAir;

import java.util.HashMap;
import java.util.Map;

public class R6Repository {

    public String getTodayZone() {
        return "Green";
    }

    public String getLastRescueTime() {
        return "2 hours ago";
    }

    public int getWeeklyRescueCount() {
        return 4;
    }

    // Trend summary without charting library
    public String getTrendSummary() {
        return "Stable: 4 rescues in past 7 days, slight increase yesterday.";
    }

    public int getControllerAdherencePercent() {
        return 82;
    }

    public int getSymptomBurdenDays() {
        return 2;
    }

    public int getTriageIncidents() {
        return 1;
    }

    public Map<String, Integer> getZoneDistribution() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Green", 20);
        map.put("Yellow", 5);
        map.put("Red", 2);
        return map;
    }
}
