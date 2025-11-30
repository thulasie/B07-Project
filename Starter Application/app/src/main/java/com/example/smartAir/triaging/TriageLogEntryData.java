package com.example.smartAir.triaging;

import com.example.smartAir.data.DatabaseLogEntryData;

public class TriageLogEntryData extends DatabaseLogEntryData {
    public Double pef;
    public Integer rescueCount;
    public String triageDecision;
    public String guidanceProvided;

    public TriageLogEntryData(Double pef, Integer rescueCount, String triageDecision, String guidanceProvided) {
        this.pef = pef;
        this.rescueCount = rescueCount;
        this.triageDecision = triageDecision;
        this.guidanceProvided = guidanceProvided;
    }

    public TriageLogEntryData() {} // Used by DatabaseEntryData


    public Double getPef() {
        return pef;
    }

    public Integer getRescueCount() {
        return rescueCount;
    }

    public String getTriageDecision() {
        return triageDecision;
    }

    public String getGuidanceProvided() {
        return guidanceProvided;
    }

    public String getLogEntry() {
        return this.toString();
    }
}
