package com.example.smartAir.triage;

import androidx.annotation.Nullable;

import com.example.smartAir.domain.Symptom;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TriageModel {

    TriageModel() {
        symptomSet = new HashSet<Symptom>();
    }

    public void setPEF(@Nullable Float f) {
        currentPEF = f;
    }

    public void setRescueCount(Integer c) {
        rescueCount = c;
    }

    public enum TriageDecision {
        NONE, CALL_EMERGENCY_NOW, START_HOME_STEPS
    }

    private Set<Symptom> symptomSet;
    private int rescueCount = 0;
    @Nullable
    private Float currentPEF; // because it is optional we use the class instead
    private Date started;
    private TriageDecision decision = TriageDecision.NONE;

    public void startTriageSession() {

    }

    public void escalate() {

    }

    public void setDecision (TriageDecision d) {
        this.decision = d;
    }

    public void toggleSymptom(boolean b, Symptom s) {
        if (b) {
            symptomSet.add(s);
        } else {
            symptomSet.remove(s);
        }
    }
}

