package com.example.smartAir.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.example.smartAir.medicine.MedicineLog;
import com.example.smartAir.medicine.InventoryItem;
import com.example.smartAir.motivation.Badge;

public class Sync { // TODO CHANGE THIS UP!!!!!!

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
