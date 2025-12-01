package com.example.smartAir.pefAndRecovery;

class PersonalBestEntryController {

    private Float personalBest;
    private PersonalBestLog editor;

    void setLogger(PersonalBestLog e) {
        editor = e;
    }

    void changePersonalBest(Float d) {
        personalBest = d;
    }

    void submitPersonalBest() {
        if (personalBest != null) {
            editor.setPersonalBest(personalBest);
        }
    }
}
