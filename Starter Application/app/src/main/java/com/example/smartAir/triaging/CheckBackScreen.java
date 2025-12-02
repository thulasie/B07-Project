package com.example.smartAir.triaging;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;
import com.example.smartAir.domain.SevereSymptoms;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class CheckBackScreen extends Fragment {

    private TriageController triageController;

    public CheckBackScreen () {
        super(R.layout.triage_check_back_screen);
    }
    public CheckBackScreen(TriageController controller) {
        this();
        this.triageController = controller;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ChipGroup symptom_box = view.findViewById(R.id.triage_check_back_symptoms_entry);

        for (SevereSymptoms severeSymptoms : SevereSymptoms.values()) {
            Chip a = (Chip) getLayoutInflater().inflate(R.layout.triage_symptom_chip, null, false);

            a.setText(severeSymptoms.toSimpleString());

            a.setOnCheckedChangeListener((button, newState) -> {

                if (triageController != null) {
                    triageController.toggleSymptom(newState, severeSymptoms);
                }

                a.setChecked(newState);
            });

            symptom_box.addView(a);
        }

        MaterialCheckBox feelingWorseSwitch = view.findViewById(R.id.triage_check_back_feeling_worse_switch);

        feelingWorseSwitch.setOnCheckedChangeListener((button, newState) -> {
            triageController.setFeelingBetter(newState);
        });

        Button submitButton = view.findViewById(R.id.triage_check_back_button);

        submitButton.setOnClickListener((ignored) -> {
            triageController.submitCheckBack();
        });
    }
}
