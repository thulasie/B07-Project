package com.example.smartAir.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.example.smartAir.model.MedicineLog;
import com.example.smartAir.model.InventoryItem;
import com.example.smartAir.model.Badge;

public class Sync {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void syncMedicineLog(MedicineLog log) {
        db.collection("medicine_logs")
                .document(String.valueOf(log.id))
                .set(log);
    }

    public void syncBadge(Badge badge) {
        db.collection("badges")
                .document(String.valueOf(badge.id))
                .set(badge);
    }

    public void syncInventoryItem(InventoryItem item) {
        db.collection("inventory_items")
                .document(String.valueOf(item.id))
                .set(item);
    }

}
