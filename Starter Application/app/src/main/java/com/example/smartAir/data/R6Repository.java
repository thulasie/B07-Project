package com.example.smartAir.data;

import androidx.annotation.NonNull;

import com.example.smartAir.medicine.ControllerDoseEvent;
import com.example.smartAir.inventory.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.symptom.z_s_l;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class R6Repository implements R6RepositoryInterface {

    private final FirebaseFirestore db;
    private final String childId;

    public R6Repository(@NonNull String childId) {
        this.db = FirebaseFirestore.getInstance();
        this.childId = childId;
    }

    @Override
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

    @Override
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

    @Override
    public Task<List<z_s_l>> getSymptomLogs(long fromMillis, long toMillis) {
        return db.collection("children").document(childId)
                .collection("symptoms")
                .whereGreaterThanOrEqualTo("timestampMillis", fromMillis)
                .whereLessThanOrEqualTo("timestampMillis", toMillis)
                .get()
                .continueWith(task -> {
                    List<z_s_l> list = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        z_s_l ev = doc.toObject(z_s_l.class);
                        if (ev != null) list.add(ev);
                    }
                    return list;
                });
    }

    @Override
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

    @Override
    public long now() {
        return System.currentTimeMillis();
    }

    @Override
    public long getStartMillisMonthsAgo(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -months);
        return cal.getTimeInMillis();
    }
}
