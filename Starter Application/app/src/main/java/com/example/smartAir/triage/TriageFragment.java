package com.example.smartAir.triage;

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
        loadFragment(new SymptomEntry(controller));
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.triage_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startRecentHistory() {
        loadFragment(new RecentHistoryEntry(controller));
    }
}



