package com.example.smartAir.data;

import androidx.annotation.NonNull;

import com.example.smartAir.R6model.ControllerDoseEvent;
import com.example.smartAir.R6model.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.R6model.SymptomLog;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class R6Repository {

    private final FirebaseFirestore db;
    private final String childId;

    public R6Repository(@NonNull String childId) {
        this.db = FirebaseFirestore.getInstance();
        this.childId = childId;
    }

    public Task<List<RescueEvent>> getRescueEvents(long fromMillis, long toMillis) {
        return db.collection("children").document(childId)
                .collection("rescueEvents")
                .whereGreaterThanOrEqualTo("timestampMillis", fromMillis)
                .whereLessThanOrEqualTo("timestampMillis", toMillis)
                .get()
                .continueWith(task -> {
                    List<RescueEvent> list = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        RescueEvent ev = doc.toObject(RescueEvent.class);
                        if (ev != null) list.add(ev);
                    }
                    return list;
                });
    }

    public Task<List<ControllerDoseEvent>> getControllerDoses(long fromMillis, long toMillis) {
        return db.collection("children").document(childId)
                .collection("controllerDoses")
                .whereGreaterThanOrEqualTo("timestampMillis", fromMillis)
                .whereLessThanOrEqualTo("timestampMillis", toMillis)
                .get()
                .continueWith(task -> {
                    List<ControllerDoseEvent> list = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        ControllerDoseEvent ev = doc.toObject(ControllerDoseEvent.class);
                        if (ev != null) list.add(ev);
                    }
                    return list;
                });
    }

    public Task<List<SymptomLog>> getSymptomLogs(long fromMillis, long toMillis) {
        return db.collection("children").document(childId)
                .collection("symptoms")
                .whereGreaterThanOrEqualTo("timestampMillis", fromMillis)
                .whereLessThanOrEqualTo("timestampMillis", toMillis)
                .get()
                .continueWith(task -> {
                    List<SymptomLog> list = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        SymptomLog ev = doc.toObject(SymptomLog.class);
                        if (ev != null) list.add(ev);
                    }
                    return list;
                });
    }

    public Task<List<InventoryStatus>> getInventoryStatuses() {
        return db.collection("children").document(childId)
                .collection("inventory")
                .get()
                .continueWith(task -> {
                    List<InventoryStatus> list = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        InventoryStatus st = doc.toObject(InventoryStatus.class);
                        if (st != null) list.add(st);
                    }
                    return list;
                });
    }

    public long now() {
        return System.currentTimeMillis();
    }

    public long getStartMillisMonthsAgo(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -months);
        return cal.getTimeInMillis();
    }
}
