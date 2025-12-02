package com.example.smartAir.ui.onboarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.smartAir.R;

public class OnboardingCollectionAdapter extends FragmentStateAdapter {
    OnboardingContent onboardingContent;

    private OnboardingExitAction action = ()-> {System.out.println("Please set an action");};

    public OnboardingCollectionAdapter(Fragment fragment) {
        super(fragment);
    }

    public OnboardingCollectionAdapter(OnboardingContainerFragment fragment) {
        super(fragment);
    }

    public OnboardingCollectionAdapter(OnboardingContainerFragment fragment, OnboardingContent onboardingContent) {
        super(fragment);
        this.onboardingContent = onboardingContent;
    }

    void setOnboardingExitAction (OnboardingExitAction a) {
        action = a;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        OnboardingPanelFragment fragment = new OnboardingPanelFragment();
        Bundle args = new Bundle();

        if (onboardingContent == null) {
            args.putString("onboarding_caption", "Hello! " + position);
            args.putInt("onboarding_image", R.drawable.not_my_cat_low_res);
            args.putBoolean("is_end_of_page", false);
        } else {
            if (position < onboardingContent.getPageCount()) {
                args.putString("onboarding_caption", onboardingContent.getPageCaption(position));
                args.putInt("onboarding_image", onboardingContent.getPageImage(position));
                args.putBoolean("onboarding_exit_page", false);
            } else {
                fragment.setExitScreen(true);
                fragment.setOnboardingExitAction(action);
            }
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        if (onboardingContent == null)
            return 10;
        else {
            return onboardingContent.getPageCount()+1;
        }
    }

    void setOnboardingContent(OnboardingContent c) {
        this.onboardingContent = c;
    }
}
