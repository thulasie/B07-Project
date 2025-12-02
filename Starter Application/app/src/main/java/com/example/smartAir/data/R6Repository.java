package com.example.smartAir.data;

import androidx.annotation.NonNull;

import com.example.smartAir.medicine.ControllerDoseEvent;
import com.example.smartAir.inventory.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.dailies.z_s_l;
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
    public Task<List<RescueEvent>> getRescueEvents(long fromMillis, long toMillis, Callback cb) {
        return null;
    }

    @Override
    public Task<List<ControllerDoseEvent>> getControllerDoses(long fromMillis, long toMillis, Callback cb) {
        return null;
    }

    @Override
    public Task<List<z_s_l>> getSymptomLogs(long fromMillis, Callback cb) {
        return null;
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
}
