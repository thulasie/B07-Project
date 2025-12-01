package com.example.smartAir.pefAndRecovery;

import static android.view.View.INVISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.smartAir.R;

public class PefEntry extends Fragment {

    PefEntryController controller = new PefEntryController() { // Mockup
        Float PEF;
        Float PEFPost;
        boolean hasPEFPost = false;

        @Override
        public boolean hasPrePostMedicinePEF() {
            return hasPEFPost;
        }

        @Override
        public void setPEFValue(Float f) {PEF = f;}

        @Override
        public void setPEFPostMedicineValue(Float f) {PEFPost = f;}

        @Override
        public void togglePEFPostMedicineValue(boolean b) {hasPEFPost = b;}

        @Override
        public void submitChanges() {System.out.println("Sent!");
        }
    };
    ExitScreenAction exitScreen = () -> System.out.println("Hi!");

    private void togglePEF (boolean b) {
        controller.togglePEFPostMedicineValue(b);

        EditText postMedEntry = requireView().findViewById(R.id.pef_entry_post_med);
        EditText defaultMedEntry = requireView().findViewById(R.id.pef_entry_default);
        if (b) {
            postMedEntry.setVisibility(View.VISIBLE);
            defaultMedEntry.setHint("PEF");
        } else {
            postMedEntry.setVisibility(INVISIBLE);
            defaultMedEntry.setHint("PEF (before medicine)");
        }
    }

    public PefEntry() {
        // Required empty public constructor
        super(R.layout.fragment_pef_entry);
    }

    public void setPEFController(PefEntryController c) {
        controller = c;
    }
    public void setScreenAfter(ExitScreenAction a) {
        exitScreen = a;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        CheckBox showPostMed = view.findViewById(R.id.pef_show_post_med);
        Button submit = view.findViewById(R.id.pef_submit_value);
        EditText defaultPefEntry = view.findViewById(R.id.pef_entry_default);
        EditText postMedPefEntry = view.findViewById(R.id.pef_entry_post_med);

        showPostMed.setChecked(controller.hasPrePostMedicinePEF());
        showPostMed.setOnCheckedChangeListener((button, checked) -> togglePEF(checked));

        submit.setOnClickListener((view1) -> {
            controller.submitChanges();
            exitScreen.run();
        });

        defaultPefEntry.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {}
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.isEmpty()) {
                    controller.setPEFValue(null);
                } else {
                    controller.setPEFValue(Float.valueOf(s.toString()));
                }
            }
        });

        postMedPefEntry.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {}
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.isEmpty()) {
                    controller.setPEFPostMedicineValue(null);
                } else {
                    controller.setPEFPostMedicineValue(Float.valueOf(s.toString()));
                }
            }
        });
    }
}