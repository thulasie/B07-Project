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

    private UserRole role = UserRole.CHILD;
    private OnboardingContent content;

    public OnboardingContainerFragment() {
        super(R.layout.onboarding_container);
        role = null;
    }
    public OnboardingContainerFragment(UserRole role) {
        super(R.layout.onboarding_container);
        this.role = role;
    }

    void setOnboardingContent (OnboardingContent a) {
        content = a;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ViewPager2 onboarding = (ViewPager2) view.findViewById(R.id.onboarding_pager);
        OnboardingCollectionAdapter adapter = new OnboardingCollectionAdapter(this, content);
        onboarding.setAdapter(adapter);

        // Connect to pagination dots
        TabLayout pager_dots = view.findViewById(R.id.onboarding_pager_dots);
        new TabLayoutMediator(pager_dots, onboarding,
                (tab, position) -> {}).attach();
    }
}



