package com.example.smartAir.ui.onboarding;

import com.example.smartAir.R;
import com.example.smartAir.domain.UserRole;

abstract class OnboardingContent {
    int pageCount;
    String[] pageCaptions;
    int[] pageImages;
    UserRole role;

    public String getPageCaption (int i) {
        return pageCaptions[i];
    }

    public int getPageImage (int i) {
        return pageImages[i];
    }

    public int getPageCount () {
        return pageCount;
    }

    public UserRole getRole() {return role;}


}

class TestOnboarding extends OnboardingContent {
    TestOnboarding () {
        pageCount = 2;
        pageCaptions = new String[]{"Explore our application...", "Are you ready?!"};
        pageImages = new int[]{R.drawable.not_my_cat_low_res, R.drawable.squeak};
    }
}

class ChildOnboarding extends OnboardingContent {
    ChildOnboarding () {
        pageCount = 2;
        pageCaptions = new String[]{"As a child, you can...", "Are you ready?!"};
        pageImages = new int[]{R.drawable.not_my_cat_low_res, R.drawable.squeak};
        role = UserRole.CHILD;
    }
}

class ProviderOnboarding extends OnboardingContent {
    ProviderOnboarding () {
        pageCount = 2;
        pageCaptions = new String[]{"As a provider, you may", "Are you ready?!"};
        pageImages = new int[]{R.drawable.not_my_cat_low_res, R.drawable.squeak};
        role = UserRole.PROVIDER;

    }
}

class ParentOnboarding extends OnboardingContent {
    ParentOnboarding () {
        pageCount = 2;
        pageCaptions = new String[]{"As a parent, you may", "Are you ready?!"};
        pageImages = new int[]{R.drawable.not_my_cat_low_res, R.drawable.squeak};
        role = UserRole.PARENT;
    }
}


