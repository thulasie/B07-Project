package com.example.smartAir;

public abstract class OnboardingContent {
    int pageCount;
    String[] pageCaptions;
    int[] pageImages;

    public String getPageCaption (int i) {
        return pageCaptions[i];
    }

    public int getPageImage (int i) {
        return pageImages[i];
    }

    public int getPageCount () {
        return pageCount;
    }
}

class TestOnboarding extends OnboardingContent {
    TestOnboarding () {
        pageCount = 2;
        pageCaptions = new String[]{"Explore our application...", "Are you ready?!"};
        pageImages = new int[]{R.drawable.not_my_cat_low_res, R.drawable.squeak};
    }
}



