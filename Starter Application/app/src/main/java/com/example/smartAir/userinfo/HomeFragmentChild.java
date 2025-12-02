package com.example.smartAir.userinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.smartAir.R;
import com.example.smartAir.dailies.DailyCheckInFacade;
import com.example.smartAir.domain.EntryAuthor;
import com.example.smartAir.medicine.MedicineLogFragmentFactory;
import com.example.smartAir.motivation.MotivationFacade;
import com.example.smartAir.pefAndRecovery.ZoneEntryFacade;
import com.example.smartAir.triaging.TriageScreenCreator;

public class HomeFragmentChild extends Fragment {
    private static FragmentLoader loader;

    GoBackProvider provider;

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
        DailyCheckInFacade.create(UserInfo.singleton.username,
                () -> loader.load(UserInfo.getFactory().makeUserDashboard()),
                EntryAuthor.CHILD);
    }

    private void navigateToBadges() {
        loader.load(MotivationFacade.createMotivationFragment(UserInfo.singleton.username));
    }

    private void navigateToPEFEntry() {
        ZoneEntryFacade.setExitAction((a) -> provider::goBack);
        loader.load(ZoneEntryFacade.createPefEntry());
    }

    private void navigateToMedicineLog() {
        loader.load(MedicineLogFragmentFactory.create(UserInfo.singleton.username, provider::goBack));

    }

    private void navigateToTriage() {
        TriageScreenCreator t = new TriageScreenCreator(UserInfo.singleton.username);
        t.setHomeController(provider::goBack);
        t.setBreathInformationProvider(ZoneEntryFacade.getBreathProvider());
        loader.load(t.createTriageFragment());
    }

    private void navigateToTechniqueHelper() {
        
    }

    private void signOut() {

    }
}