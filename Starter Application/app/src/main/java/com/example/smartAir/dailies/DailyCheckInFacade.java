package com.example.smartAir.symptom;

public class DailyCheckInFacade {
    public static DailyCheckInFragment create (String username, DailyCheckInFragment.Callback cb) {
        DailyCheckInFragment frag = new DailyCheckInFragment();
        FirebaseRTDBSymptomRepository.setUserId(username);

        frag.setRepo(FirebaseRTDBSymptomRepository.getSingleton());
        frag.setCallback(cb);
        frag.setUsername(username);

        return frag;
    }
}
