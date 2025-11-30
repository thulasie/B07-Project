package com.example.smartAir.triaging;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;
import com.example.smartAir.domain.Zone;
import com.google.android.material.chip.Chip;

public class DecisionCard extends Fragment implements TriageController.DecisionCardView {

    public interface ZoneStepsProvider {
        View getZoneAlignedSteps(Zone z);
    }

    private TriageController triageController;

    private ZoneStepsProvider zoneStepsProvider;

    public DecisionCard() {
        super(R.layout.triage_fragment_decision_card);
    }

    public DecisionCard(TriageController t) {
        this();
        this.triageController = t;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        zoneStepsProvider = z -> {
            Chip v = (Chip) getLayoutInflater().inflate(R.layout.triage_symptom_chip, null);
            v.setText(z.name());
            return v;
        };

        triageController.setDecisionCardView(this);
    }

    public void callEmergency() {
        ((TextView) requireView().findViewById(R.id.triage_decision_description)).setText("(Emergency called. Don't want to implement this functionality FR though)");
        Button proceed = (Button) getLayoutInflater().inflate(R.layout.triage_decision_button, null);
        proceed.setText("Call emergency now");
    }

    @Override
    public void startAtHomeSteps() {
        View view = requireView();

        ((TextView) view.findViewById(R.id.triage_decision_description)).setText("Treat your symptoms...");

        LinearLayout l = requireView().findViewById(R.id.triage_decision_action_box);

        Button betterButton = (Button) getLayoutInflater().inflate(R.layout.triage_decision_button, null);
        betterButton.setText("I'm feeling better!");
        betterButton.setOnClickListener((view1) -> triageController.concludeTriage());

        Button worseButton = (Button) getLayoutInflater().inflate(R.layout.triage_decision_button, null);
        worseButton.setText("I'm feeling worse...");
        worseButton.setOnClickListener((view1) -> triageController.enterCheckBack(false));

        l.addView(zoneStepsProvider.getZoneAlignedSteps(triageController.getZone()));
        l.addView(betterButton);
        l.addView(worseButton);
    }

    @Override
    public void setRemainingTriageTime(long timeRemaining) {
        ((TextView)requireView().findViewById(R.id.triage_decision_timer)).setText(formatTime(timeRemaining));
    }

    // Helpers

    private String formatTime (long i) {
        return String.valueOf(Math.floor(i / 1000.));
    }
}
