package com.example.smartAir.domain;

public enum SevereSymptoms {
    SPEECH_IMPEDED, RETRACTIONS, CYANOSIS;

    public String toSimpleString() {
        switch (this) {
            case SPEECH_IMPEDED:
                return "trouble speaking full sentences";
            case RETRACTIONS:
                return "chest pulling in";
            case CYANOSIS:
                return "blue/gray nails or lips";
            default:
                return this.name();
        }
    }
}