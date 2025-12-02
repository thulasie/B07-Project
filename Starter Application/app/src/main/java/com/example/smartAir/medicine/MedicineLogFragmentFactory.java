package com.example.smartAir.medicine;

public class MedicineLogFragmentFactory {
    public interface Callback {
        void run();
    }

    public static MedicineLogFragment create(String username, Callback c) {
        MedicineLogFragment frag = new MedicineLogFragment();
        frag.setGoBack(c::run);
        MedicineDatabaseLogger.setUser(username);
        return frag;
    }
}
