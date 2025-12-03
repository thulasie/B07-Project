package com.example.smartAir.userinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    private FragmentLoader loader;

    GoBackProvider provider = () -> loader.load(UserBasicInfo.getHomeFragment());

    public HomeFragmentChild() {
        super(R.layout.home_fragment_child);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.loader = new FragmentLoader() {
            final FragmentManager manager = HomeFragmentChild.this.getParentFragmentManager();
            @Override
            public void load(Fragment f) {
                FragmentTransaction t = manager.beginTransaction();
                t.replace(R.id.fragment_container, f);
                t.addToBackStack(null);
                t.commit();
            }
        };

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

        ZoneEntryFacade.changeUser(UserBasicInfo.getUsername(), () -> {
            TextView v = view.findViewById(R.id.child_home_stats_zone);
            v.setText(ZoneEntryFacade.getBreathProvider().getZone().toString());
        });
    }

    private void navigateToDailyLogin() {
        Fragment frag = DailyCheckInFacade.create(UserBasicInfo.getUsername(),
                () -> loader.load(UserBasicInfo.getHomeFragment()),
                EntryAuthor.CHILD);
        loader.load(frag);
    }

    private void navigateToBadges() {
        loader.load(MotivationFacade.createMotivationFragment(UserBasicInfo.getUsername()));
    }

    private void navigateToPEFEntry() {
        ZoneEntryFacade.changeUser(UserBasicInfo.getUsername(), () -> {
            ZoneEntryFacade.setExitAction((a) -> provider::goBack);
            Fragment f = ZoneEntryFacade.createPefEntry();
            loader.load(f);
        });
    }

    private void navigateToMedicineLog() {
        loader.load(MedicineLogFragmentFactory.create(UserBasicInfo.getUsername(), provider::goBack));

    }

    private void navigateToTriage() {
        TriageScreenCreator t = new TriageScreenCreator(UserBasicInfo.getUsername());
        t.setHomeController(provider::goBack);
        t.setBreathInformationProvider(ZoneEntryFacade.getBreathProvider());
        loader.load(t.createTriageFragment());
    }

    private void navigateToTechniqueHelper() {
        
    }

    private void signOut() {

    }
}