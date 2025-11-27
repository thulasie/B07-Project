package com.example.smartAir.triage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;
import com.example.smartAir.domain.Symptom;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class SymptomEntry extends Fragment {

    private TriageController triageController;

    public SymptomEntry() {
        super(R.layout.triage_fragment_symptoms);
    }

    public SymptomEntry(TriageController t) {
        this();
        this.triageController = t;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ChipGroup symptom_box = view.findViewById(R.id.triage_symptoms);

        for (Symptom symptom : Symptom.values()) {
            Chip a = (Chip) getLayoutInflater().inflate(R.layout.triage_symptom_chip, null, false);

            a.setText(symptom.toSimpleString());

            a.setOnCheckedChangeListener((button, newState) -> {

                if (triageController != null) {
                    triageController.toggleSymptom(newState, symptom);
                }

                a.setChecked(newState);
            });

            symptom_box.addView(a);
        }

        Button b = (Button) view.findViewById(R.id.triage_symptoms_button);

        b.setOnClickListener((ignored) -> {
            triageController.submitSymptoms();
        });
    }
}
