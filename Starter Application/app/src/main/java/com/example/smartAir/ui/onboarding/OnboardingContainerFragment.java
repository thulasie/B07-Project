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
    public OnboardingContainerFragment() {
        super(R.layout.onboarding_container);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ViewPager2 onboarding = (ViewPager2) view.findViewById(R.id.onboarding_pager);
        Object a = null;
        if (savedInstanceState != null) {
            a = savedInstanceState.get("userRole"); // Replace with however the roles are
        }
        OnboardingCollectionAdapter adapter = getOnboardingCollectionAdapter(a);
        onboarding.setAdapter(adapter);

        // Connect to pagination dots
        TabLayout pager_dots = view.findViewById(R.id.onboarding_pager_dots);
        new TabLayoutMediator(pager_dots, onboarding,
                (tab, position) -> {}).attach();
    }

    @NonNull
    private OnboardingCollectionAdapter getOnboardingCollectionAdapter(Object a) {
        OnboardingContent c = new TestOnboarding();

        if (a instanceof UserRole) {
            if (a == UserRole.CHILD) { // child
                c = new ChildOnboarding();
            } else if (a == UserRole.PARENT) { // parent
                c = new ParentOnboarding();
            } else if (a == UserRole.PROVIDER) { // provider
                c = new ProviderOnboarding();
            }
        }
        // Some code to switch onboarding style based on instance...
        return new OnboardingCollectionAdapter(this, c);
    }
}



