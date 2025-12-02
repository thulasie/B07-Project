package com.example.smartAir.motivation;

public class MotivationFacade {

    public static MotivationFragment createMotivationFragment(String username) {
        BadgeLoader.initializeLoader(username);
        MotivationFragment frag = new MotivationFragment();
        return frag;
    }
}
