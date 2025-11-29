package com.example.smartAir.triaging;

import android.os.CountDownTimer;

import androidx.annotation.Nullable;

import com.example.smartAir.domain.SevereSymptoms;
import com.example.smartAir.domain.Zone;

public class TriageController {

    interface TriageFragmentSwitcher {

        void startSymptomEntry();
        void startRecentHistoryEntry();
        void startDecisionCard();
        void startCheckBack();

        void showResolutionScreen();
    }

    interface DecisionCardView {
        void callEmergency();

        void startAtHomeSteps();

        void setRemainingTriageTime(long timeRemaining);
        void timerEndCallback();

    }

    interface TriageResolver {
        void callEmergency();
        void showTriageEnd();
    }

    interface AlerterService {
        void notifyOfStart();
        void notifyOfEmergency();
        void notifyOfEscalation();
    }

    enum TriageState {
        ENTRY_SCREEN, SYMPTOM_SCREEN, RECENT_HISTORY, DECISION_CARD, CALLING_EMERGENCY,
        STARTING_TREATMENT_PLAN, CHECK_BACK, FINISH
    }

    TriageController (TriageFragmentSwitcher s) {
        this.state = TriageState.ENTRY_SCREEN;
        this.model = new TriageModel();
        this.triageFragmentSwitcher = s;
        TriageModel.setProvider(new TriageModel.BreathInformationProvider() {
            @Override
            public double getPB() {
                return 10;
            }

            @Override
            public double getPEF() {
                return 5;
            }
        });
    }

    private final long TIMER_LENGTH = 15000;
    private final TriageModel model;
    private TriageState state;
    private final TriageFragmentSwitcher triageFragmentSwitcher;
    private DecisionCardView decisionCardView;
    private CountDownTimer fireTimer;
    private boolean feelingBetter = false;
    private AlerterService alerter = new AlerterService() {
        @Override
        public void notifyOfStart() {
            System.out.println("rump AlerterterService: Triage started!");
        }

        @Override
        public void notifyOfEmergency() {
            System.out.println("rump AlerterterService: Emergency started!");
        }

        @Override
        public void notifyOfEscalation() {
            System.out.println("rump AlerterterService: Escalation started!");
        }
    };

    // Starting the process

    public void startTriage() {
        // Start the timer
        fireTimer = new CountDownTimer(TIMER_LENGTH, 1000) {
            @Override
            public void onFinish() {
                if (getState() == TriageState.STARTING_TREATMENT_PLAN) {
                    alerter.notifyOfEscalation();
                    enterCheckBack(false);
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("time left: " + millisUntilFinished);
                if (getState() == TriageState.STARTING_TREATMENT_PLAN) {
                    decisionCardView.setRemainingTriageTime(millisUntilFinished);
                }
            }
        };

        fireTimer.start();

        // Alert the parent

        alerter.notifyOfStart();

        // Go to symptoms

        triageFragmentSwitcher.startSymptomEntry();
        state = TriageState.SYMPTOM_SCREEN;

        // Officially start the triage session
        model.startTriageSession();
    }

    // Symptoms

    void toggleSymptom(boolean b, SevereSymptoms s) {
        model.toggleSymptom(b, s);
    }

    void submitSymptoms() {
        triageFragmentSwitcher.startRecentHistoryEntry();
        state = TriageState.RECENT_HISTORY;
    }

    // Recent history

    void setPEF(@Nullable Float f) {
        model.setPEF(f);
    }

    void setRescueCount(int c) {
        model.setRescueCount(c);
    }

    void submitRecentHistory() {
        triageFragmentSwitcher.startDecisionCard();
        this.state = TriageState.DECISION_CARD;
    }

    // Decision card

    void setDecisionCardView(DecisionCardView view) {
        this.decisionCardView = view;
        if (this.model.hasSevereSymptoms()) {
            startEmergency();
        } else {
            decisionCardView.startAtHomeSteps();
            model.setDecision(TriageModel.TriageDecision.HOME_STEPS);
            state = TriageState.STARTING_TREATMENT_PLAN;
        }
    }

    // Second screening

    void enterCheckBack(boolean feelingBetter) {
        this.feelingBetter = feelingBetter;
        this.triageFragmentSwitcher.startCheckBack();
        this.state = TriageState.CHECK_BACK;
    }

    void setFeelingBetter(boolean b) {
        feelingBetter = b;
    }

    boolean isFeelingBetter() {
        return feelingBetter;
    }

    void submitCheckBack() {
        if (!isFeelingBetter() || model.hasSevereSymptoms()) {
            startEmergency();
        } else {
            concludeTriage();
        }
    }

    // In case symptoms don't improve...

    void startEmergency () {
        fireTimer.cancel();
        state = TriageState.CALLING_EMERGENCY;
        alerter.notifyOfEmergency();
        model.setDecision(TriageModel.TriageDecision.EMERGENCY_CALLED);
        triageFragmentSwitcher.showResolutionScreen();
    }

    // but if they do get better

    void concludeTriage() {
        // No more alerts! Yay!
        this.feelingBetter = true;
        state = TriageState.FINISH;
        fireTimer.cancel();
        model.setDecision(TriageModel.TriageDecision.RESOLVED);
        triageFragmentSwitcher.showResolutionScreen();
    }

    // Show triage results

    void controlTriageEndScreen (TriageResolver resolver) {
        if (model.getDecision() == TriageModel.TriageDecision.EMERGENCY_CALLED) {
            resolver.callEmergency();
        } else {
            resolver.showTriageEnd();
        }
    }

    // Helpers

    Zone getZone() {
        return model.getZone();
    }

    TriageState getState() {
        return this.state;
    }

}
