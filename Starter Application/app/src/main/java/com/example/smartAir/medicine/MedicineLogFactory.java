package com.example.smartAir.medicine;

public class MedicineLogFactory {
    public static LogMedicineFragment create(String username) {
        LogMedicineFragment frag = new LogMedicineFragment();
        MedicineDatabaseLogger.setUser(username);
        return frag;
    }
}
