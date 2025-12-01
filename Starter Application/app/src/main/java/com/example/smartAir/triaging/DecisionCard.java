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

public class DecisionCard extends Fragment implements DecisionCardView {

    private TriageController triageController;

    private ZoneStepsProvider zoneStepsProvider;

    public DecisionCard() {
        super(R.layout.triage_fragment_decision_card);
    }

    public DecisionCard(TriageController t) {
        this();
        this.triageController = t;
    }

    public void setZoneStepsProvider(ZoneStepsProvider zoneStepsProvider) {
        this.zoneStepsProvider = zoneStepsProvider;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        triageController.setDecisionCardView(this);
    }

    @Override
    public void startAtHomeSteps() {
        View view = requireView();

        ((TextView) view.findViewById(R.id.triage_decision_description)).setText("Treat your symptoms...");

        Button betterButton = requireView().findViewById(R.id.triage_decision_left_button);
        betterButton.setOnClickListener((view1) -> triageController.concludeTriage());

        Button worseButton = requireView().findViewById(R.id.triage_decision_right_button);
        worseButton.setOnClickListener((view1) -> triageController.enterCheckBack());

        LinearLayout l = requireView().findViewById(R.id.triage_decision_action_box);
        l.addView(zoneStepsProvider.getZoneAlignedSteps(getLayoutInflater(), triageController.getZone()));
        setRemainingTriageTime(triageController.getTimeLeft());
    }

    @Override
    public void setRemainingTriageTime(long timeRemaining) {
        ((TextView)requireView().findViewById(R.id.triage_decision_timer)).setText(formatTime(timeRemaining));
    }

    // Helpers

    private String formatTime (long i) {
        long secondsTotal = i / 1000;
        long secondsOnly = secondsTotal % 60;
        long minutes = secondsTotal / 60;

        return "We'll check back in " + String.format("%02d", minutes) + ":" + String.format("%02d", secondsOnly) ;
    }
}
