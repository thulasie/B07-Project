package com.example.smartAir.ui.onboarding;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.smartAir.R;
import com.example.smartAir.domain.UserRole;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OnboardingContainerFragment extends Fragment {

    OnboardingContent c;


    public OnboardingContainerFragment() {
        super(R.layout.onboarding_container);
    }
    public OnboardingContainerFragment(UserRole role) {
        super(R.layout.onboarding_container);
    }

    void setOnboardingContent (OnboardingContent a) {
        c = a;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ViewPager2 onboarding = view.findViewById(R.id.onboarding_pager);
        onboarding.setAdapter(new OnboardingCollectionAdapter(this, c));

        // Connect to pagination dots
        TabLayout pager_dots = view.findViewById(R.id.onboarding_pager_dots);
        new TabLayoutMediator(pager_dots, onboarding,
                (tab, position) -> {}).attach();
    }
}



