package com.example.smartAir.ui.onboarding;

import androidx.fragment.app.Fragment;

import com.example.smartAir.domain.UserRole;

public class OnboardingCreator {

    private final UserRole role;
    private OnboardingExitAction action;


    public OnboardingCreator(UserRole role, OnboardingExitAction action) {
        this.role = role;
        this.action = action;
    }

    public Fragment createOnboardingFragment() {
        return new OnboardingContainerFragment(role);
    }
}
