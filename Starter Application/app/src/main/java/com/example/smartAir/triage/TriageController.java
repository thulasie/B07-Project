package com.example.smartAir.triage;

import androidx.annotation.Nullable;

import com.example.smartAir.domain.Symptom;

public class TriageController {


    enum TriageState {
        ENTRY_SCREEN, SYMPTOM_SCREEN, RECENT_HISTORY, DECISION_CARD, PARENT_ALERTED, DOWNTIME
    }

    TriageController (TriageFragmentSwitcher s) {
        this.model = new TriageModel();
        this.d = s;
    }

    interface TriageFragmentSwitcher {
        void startRecentHistory();
    }

    public interface TriageEscalationController {
        void triageSessionStarted();

        void triageAutoEscalation();

        void triageManualEscalation();
    }

    private final TriageModel model;
    private TriageState s;
    private final TriageFragmentSwitcher d;

    // Symptoms

    void toggleSymptom(boolean b, Symptom s) {
        model.toggleSymptom(b, s);
    }

    void submitSymptoms() {
        d.startRecentHistory();
        s = TriageState.RECENT_HISTORY;
    }

    // Recent history

    void setPEF(@Nullable Float f) {
        model.setPEF(f);
    }

    void setRescueCount(int c) {
        model.setRescueCount(c);
    }

    void submitRecentHistory() {

    }

    public void startTriage() {
        // Start the timer

        // Alert the parent

        // Set up
    }
}
