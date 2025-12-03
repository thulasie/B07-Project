package com.example.smartAir.ui.onboarding;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.R;
import com.example.smartAir.userinfo.FragmentLoader;
import com.example.smartAir.userinfo.HomeFragmentParent;
import com.example.smartAir.userinfo.UserBasicInfo;

public class OnboardingPlaceholder extends Fragment {
    private OnboardingExitAction action;
    private FragmentLoader loader;

    public void setAction(OnboardingExitAction action) {
        this.action = action;
    }

    public OnboardingPlaceholder() {
        super(R.layout.triage_generic_screen);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.loader = new FragmentLoader() {
            final FragmentManager manager = OnboardingPlaceholder.this.getParentFragmentManager();
            @Override
            public void load(Fragment f) {
                FragmentTransaction t = manager.beginTransaction();
                t.replace(R.id.fragment_container, f);
                t.addToBackStack(null);
                t.commit();
            }
        };

        Button button = requireView().findViewById(R.id.triage_generic_button);
        TextView title = requireView().findViewById(R.id.triage_generic_title);

        button.setText("Proceed...");
        title.setText("Onboarding placeholder");

        button.setOnClickListener((v) -> {
            loader.load(UserBasicInfo.getHomeFragment());
        });
    }


}
