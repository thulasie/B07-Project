package com.example.smartAir.triaging;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;

public class RecentHistoryEntry extends Fragment {
    private TriageController triageController;

    public RecentHistoryEntry () {
        super(R.layout.triage_fragment_recent_history);
    }

    public RecentHistoryEntry (TriageController t) {
        this();
        this.triageController = t;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        EditText pEFEntry = view.findViewById(R.id.triage_recent_history_pef);
        EditText rescueCountEntry = view.findViewById(R.id.triage_recent_history_rescue_count);
        Button submit = (Button) view.findViewById(R.id.triage_recent_history_button);

        pEFEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {//donothing;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.isEmpty()) {
                    triageController.setPEF(Double.parseDouble(s.toString()));
                } else {
                    triageController.setPEF(null);
                }

            }
        });

        rescueCountEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.isEmpty()) {
                    triageController.setRescueCount(Integer.parseInt(s.toString()));
                } else {
                    triageController.setRescueCount(0);
                    rescueCountEntry.setText("0");
                }
            }
        });

        submit.setOnClickListener((ignored) -> triageController.submitRecentHistory());
    }
}
