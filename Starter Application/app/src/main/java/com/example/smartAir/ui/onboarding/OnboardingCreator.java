package com.example.smartAir.ui.onboarding;

import androidx.fragment.app.Fragment;

import com.example.smartAir.domain.UserRole;

public class OnboardingCreator {


    private final UserRole role;
    static OnboardingExitAction action;


    public OnboardingCreator(UserRole role, OnboardingExitAction action) {
        this.role = role;
        OnboardingCreator.action = action;
    }

    public Fragment createOnboardingFragment() {
        OnboardingContainerFragment frag = new OnboardingContainerFragment(role);
        OnboardingContent c;

        switch(role) {
            case PROVIDER:
                c = new ChildOnboarding();
                break;
            case PARENT:
                c = new ParentOnboarding();
                break;
            default:
                c = new ChildOnboarding();
        }

        frag.setOnboardingContent(c);

        return new OnboardingContainerFragment(role);
    }
}
