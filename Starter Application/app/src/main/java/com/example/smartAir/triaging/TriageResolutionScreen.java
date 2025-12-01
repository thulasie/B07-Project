package com.example.smartAir.triaging;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;

public class TriageResolutionScreen extends Fragment implements TriageResolver {
    private TriageController triageController;
    private HomeController controller = () -> System.out.println("No home controller set.");

    public TriageResolutionScreen() {
        super(R.layout.triage_generic_screen);
    }

    public TriageResolutionScreen(TriageController t) {
        this();
        this.triageController = t;
    }

    public void setHomeController(HomeController h) {
        this.controller = h;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        triageController.controlTriageEndScreen(this);
    }

    public void callEmergency() {
        Button button = requireView().findViewById(R.id.triage_generic_button);
        TextView title = requireView().findViewById(R.id.triage_generic_title);
        title.setText("We suggest calling the emergency room");

        button.setOnClickListener((view) -> {
            System.out.println("Emergency called...");
        });

        button.setText("Call emergency now");

    }
    public void showTriageEnd() {
        Button button = requireView().findViewById(R.id.triage_generic_button);
        TextView title = requireView().findViewById(R.id.triage_generic_title);

        title.setText("All good! Go on with your day");

        button.setOnClickListener((view) ->
            controller.returnToHome()
        );

        button.setText("Back to dashboard");

    }
}
