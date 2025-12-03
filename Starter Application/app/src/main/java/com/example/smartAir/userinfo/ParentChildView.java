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

public class ParentChildView extends Fragment {
    private FragmentLoader loader;
    private String childUsername = "";

    GoBackProvider provider = () -> {
        ParentChildView frag = new ParentChildView();
        frag.setChildUsername(this.childUsername);
        loader.load(frag);
    };

    public void setChildUsername(String childUsername) {
        this.childUsername = childUsername;
    }

    public ParentChildView() {
        super(R.layout.home_fragment_child_parent_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.loader = new FragmentLoader() {
            final FragmentManager manager = ParentChildView.this.getParentFragmentManager();
            @Override
            public void load(Fragment f) {
                FragmentTransaction t = manager.beginTransaction();
                t.replace(R.id.fragment_container, f);
                t.addToBackStack(null);
                t.commit();
            }
        };

        Button buttonDaily = view.findViewById(R.id.parent_home_start_check_in);
        Button pefButton = view.findViewById(R.id.parent_home_enter_pef);
        Button medLogButton = view.findViewById(R.id.parent_home_enter_log_medicine);
        Button exitButton = view.findViewById(R.id.parent_home_exit_button);
        Button pbButton = view.findViewById(R.id.parent_home_enter_pb);
        TextView childName = view.findViewById(R.id.parent_home_child_name);

        childName.setText(childUsername);

        buttonDaily.setOnClickListener(v -> navigateToDailyLogin());
        pefButton.setOnClickListener(v -> this.navigateToPEFEntry());
        medLogButton.setOnClickListener(v -> this.navigateToMedicineLog());
        exitButton.setOnClickListener(v -> this.exitProfile());
        pbButton.setOnClickListener(v -> this.navigateToPBEntry());

        ZoneEntryFacade.changeUser(childUsername, () -> {
            TextView zone = view.findViewById(R.id.parent_home_stats_zone);

            zone.setText(ZoneEntryFacade.getBreathProvider().getZone().toString());
        });
    }

    private void navigateToDailyLogin() {
        Fragment frag = DailyCheckInFacade.create(childUsername,
                () -> loader.load(UserBasicInfo.getHomeFragment()),
                EntryAuthor.PARENT);
        loader.load(frag);
    }

    private void navigateToPEFEntry() {
        ZoneEntryFacade.changeUser(childUsername, () -> {
            ZoneEntryFacade.setExitAction((a) -> provider::goBack);
            Fragment f = ZoneEntryFacade.createPefEntry();
            loader.load(f);
        });
    }

    private void navigateToPBEntry() {
        ZoneEntryFacade.changeUser(childUsername, () -> {
            ZoneEntryFacade.setExitAction((a) -> provider::goBack);
            Fragment f = ZoneEntryFacade.createPersonalBestEntry();
            loader.load(f);
        });
    }

    private void navigateToMedicineLog() {
        loader.load(MedicineLogFragmentFactory.create(childUsername, provider::goBack));
    }

    private void exitProfile() {
        loader.load(UserBasicInfo.getHomeFragment());
    }
}