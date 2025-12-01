package com.example.smartAir.triaging;

import androidx.annotation.Nullable;

import com.example.smartAir.domain.SevereSymptoms;
import com.example.smartAir.domain.Zone;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TriageModel {

    private TriageLogger logger;

    public enum TriageDecision {
        UNDECIDED, HOME_STEPS, EMERGENCY_CALLED, RESOLVED
    }

    private Set<SevereSymptoms> severeSymptomsSet;
    private ArrayList<String> guidance = new ArrayList<>();
    private int rescueCount = 0;
    @Nullable
    private Float currentPEF; // because it is optional we use the class instead
    private Date started;
    private TriageDecision decision = TriageDecision.UNDECIDED;

    private BreathInformationProvider provider;

    // Initialization

    public void setProvider(BreathInformationProvider provider) {
        this.provider = provider;
    }

    public void setLogger(TriageLogger l) {
        this.logger = l;
    }


    public TriageModel() {
        severeSymptomsSet = new HashSet<>();
    }

    public void startTriageSession() {
        started = new Date();
        logger.updateTriage(this);
    }

    // Guidance

    public void addGuidance (String msg) {
        this.guidance.add(msg);
    }

    public void recordZoneAlignedStepsInGuidance() {
        this.guidance.add("Asked user to perform " + this.getZone().toString().toLowerCase() + " zone aligned steps");
    }

    // Symptoms

    public boolean hasSevereSymptoms() {
        return !severeSymptomsSet.isEmpty();
    }

    // Rescue Count, Peak flow, & Zone

    public void setPEF(@Nullable Float f) {
        currentPEF = f;
        if (currentPEF != null) {
            provider.setPEF(f);
        }
    }

    public Zone getZone() {
        if (this.currentPEF != null) {
            return Zone.calculateZone(this.currentPEF, provider.getPB());
        } else {
            return provider.getZone();
        }
    }

    public void setRescueCount(Integer c) {
        rescueCount = c;
    }

    // Decision

    public void setDecision (TriageDecision d) {
        this.decision = d;
    }

    public void toggleSymptom(boolean b, SevereSymptoms s) {
        if (b) {
            severeSymptomsSet.add(s);
        } else {
            severeSymptomsSet.remove(s);
        }
    }

    // Triage log entry

    public TriageLogEntryData getTriageLogEntryData() {
        return new TriageLogEntryData(this.currentPEF, this.rescueCount, this.getDecision().toString(), this.formatGuidance());
    }

    // Helpers

    public String getLogIdentifier() {
        return String.valueOf(this.started.getTime());
    }

    public TriageDecision getDecision() {
        return decision;
    }

    Date getStartDate() {
        return started;
    }

    void updateLog() {
        logger.updateTriage(this);
    }

    private String formatGuidance() {
        StringBuilder guidance = new StringBuilder();

        for (String line: this.guidance) {
            guidance.append(line).append("; ");
        }

        return guidance.toString();
    }


}

