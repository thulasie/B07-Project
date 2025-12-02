package com.example.smartAir.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.Navigation;

import com.example.smartAir.R;

public class HomeFragmentChild extends Fragment {

    public HomeFragmentChild() {
        super(R.layout.home_fragment_child);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonDaily = view.findViewById(R.id.child_home_start_check_in);
        Button badgeButton = view.findViewById(R.id.child_home_view_badges);
        Button pefButton = view.findViewById(R.id.child_home_enter_pef);
        Button medLogButton = view.findViewById(R.id.child_home_enter_log_medicine);
        Button triageButton = view.findViewById(R.id.child_home_triage_start);
        Button techniqueButton = view.findViewById(R.id.child_home_start_technique_helper);
        Button signOutButton = view.findViewById(R.id.child_home_sign_out_button);

        buttonDaily.setOnClickListener(v -> navigateToDailyLogin());
        badgeButton.setOnClickListener(v -> this.navigateToBadges());
        pefButton.setOnClickListener(v -> this.navigateToPEFEntry());
        medLogButton.setOnClickListener(v -> this.navigateToMedicineLog());
        triageButton.setOnClickListener(v -> this.navigateToTriage());
        techniqueButton.setOnClickListener(v -> this.navigateToTechniqueHelper());
        signOutButton.setOnClickListener(v -> this.signOut());
    }

    private void navigateToDailyLogin() {
        NavController nav = NavHostFragment.findNavController(this);

        Bundle args = new Bundle();
        args.putString("author", "CHILD");  // tell DailyCheckIn it's a child entry

        //nav.navigate(R.id.action_childHome_to_dailyCheckIn, args);
    }

    private void navigateToBadges() {

    }

    private void navigateToPEFEntry() {

    }

    private void navigateToMedicineLog() {

    }

    private void navigateToTriage() {

    }

    private void navigateToTechniqueHelper() {

    }

    private void signOut() {

    }
}