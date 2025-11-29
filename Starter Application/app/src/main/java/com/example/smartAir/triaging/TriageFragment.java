package com.example.smartAir.triaging;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.R;

public class TriageFragment extends Fragment implements TriageController.TriageFragmentSwitcher{

    TriageController controller = new TriageController(this);

    public TriageFragment() {
        super(R.layout.triage_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        loadFragment(new TriageStartScreen(controller));
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.triage_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startSymptomEntry() {
        loadFragment(new SymptomEntry(controller));
    }

    @Override
    public void startRecentHistoryEntry() {
        loadFragment(new RecentHistoryEntry(controller));
    }

    @Override
    public void startDecisionCard() {
        loadFragment(new DecisionCard(controller));
    }

    @Override
    public void startCheckBack() {
        loadFragment(new CheckBackScreen(controller));
    }

    @Override
    public void showResolutionScreen() {
        loadFragment(new TriageResolutionScreen(controller));
        // Make sure to include a return home controller...
    }
}



