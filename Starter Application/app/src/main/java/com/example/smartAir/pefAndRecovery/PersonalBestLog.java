package com.example.smartAir.pefAndRecovery;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalBestLog {
    private static final PersonalBestLog singletonInstance = new PersonalBestLog();
    private String username;
    private Float currentPersonalBest;
    private final static FirebaseDatabase INSTANCE = FirebaseDatabase.getInstance();
    private final static String PARTITION = "Info";

    private PersonalBestLog() {} // Forces singleton to be accessed

    static PersonalBestLog getSingletonInstance() {
        return singletonInstance;
    }
    static void initializePersonalBestLog(String username, CompletionNotifier callback) {
        singletonInstance.username = username;

        OnSuccessListener<DataSnapshot> cb = (a) -> {
            Object value = a.getValue();
            if (value != null) {
                if (value instanceof Long) {
                    singletonInstance.currentPersonalBest = Float.valueOf((Long) value);
                } else if (value instanceof Double) {
                    singletonInstance.currentPersonalBest = ((Double) value).floatValue();
                }
            }

            ZoneChangeMonitor.getSingletonInstance().initializeCurrentPersonalBest(singletonInstance.currentPersonalBest);
            callback.notifyMonitor();
        };

        INSTANCE.getReference(PARTITION).child(username)
                        .child("personalBest").get().addOnSuccessListener(cb);



    }

    void setPersonalBest (Float f) {
        ZoneChangeMonitor.getSingletonInstance().setCurrentPersonalBest(f);
        currentPersonalBest = f;

        INSTANCE.getReference(PARTITION).child(username)
                .child("personalBest").setValue(f);

        System.out.println(username + "'s personal best: " + f);
    }

    Float getPersonalBest() {
        return currentPersonalBest;
    }
}
