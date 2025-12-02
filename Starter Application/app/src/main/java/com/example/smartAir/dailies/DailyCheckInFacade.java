package com.example.smartAir.dailies;

import android.os.Bundle;

import com.example.smartAir.domain.EntryAuthor;

public class DailyCheckInFacade {
    public static DailyCheckInFragment create (String username, DailyCheckInFragment.Callback goBack, EntryAuthor a) {
        DailyCheckInFragment frag = new DailyCheckInFragment();
        FirebaseRTDBSymptomRepository.setUserId(username);

        frag.setRepo(FirebaseRTDBSymptomRepository.getSingleton());
        frag.setCallback(goBack);
        frag.setUsername(username);

        Bundle args = new Bundle();

        args.putString("author", a.name());
        frag.setArguments(args);

        return frag;
    }
}
