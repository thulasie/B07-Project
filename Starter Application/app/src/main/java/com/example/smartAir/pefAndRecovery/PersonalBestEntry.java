package com.example.smartAir.pefAndRecovery;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;

public class PersonalBestEntry extends Fragment {

    private ExitScreenAction buttonControl = () -> System.out.println("Please set a controller!");
    private PersonalBestEntryController controller;
    public PersonalBestEntry () {
        super(R.layout.fragment_personal_best_entry);
    }

    void setController(PersonalBestEntryController controller) {
        this.controller = controller;
    }

    void setOnExit(ExitScreenAction buttonControl) {
        this.buttonControl = buttonControl;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button submit = view.findViewById(R.id.pb_submit_button);
        EditText postMedPefEntry = view.findViewById(R.id.pb_value_entry);

        submit.setOnClickListener((ignored) -> {
            controller.submitPersonalBest();
            buttonControl.run();
        });

        postMedPefEntry.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {}
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.isEmpty()) {
                    controller.changePersonalBest(Float.parseFloat(s.toString()));
                } else {
                    controller.changePersonalBest(null);
                }
            }
        });

    }
}
