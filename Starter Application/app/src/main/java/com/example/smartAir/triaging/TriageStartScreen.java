package com.example.smartAir.triaging;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;

public class TriageStartScreen extends Fragment {

    TriageController controller;

    public TriageStartScreen(TriageController controller) {
        super(R.layout.triage_generic_screen);
        this.controller = controller;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button startButton = view.findViewById(R.id.triage_generic_button);

        startButton.setOnClickListener((ignored) -> {
            controller.startTriage();
        });
    }
}
