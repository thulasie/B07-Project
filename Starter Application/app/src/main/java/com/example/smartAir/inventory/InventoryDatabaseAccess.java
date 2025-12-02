package com.example.smartAir.inventory;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class InventoryDatabaseAccess {
    private String userID = null;
    private static final InventoryDatabaseAccess singleton = new InventoryDatabaseAccess();
    private DatabaseReference dbRef;

    static void switchUser (String s, Callback c){
        singleton.userID = s;
        singleton.dbRef = FirebaseDatabase.getInstance()
                .getReference("inventory").child(s);

        singleton.dbRef.get().addOnSuccessListener((v) -> {
            for (DataSnapshot dat: v.getChildren()) {
                if (Objects.equals(dat.getKey(), "Rescue")) {
                    Canister.initializeRescue(dat);
                } else if (Objects.equals(dat.getKey(), "Controller")) {
                    Canister.initializeController(dat);
                }
            }
            c.run();
        });

    }

    static void putItem(Canister item) {
        HashMap<String, Object> update = new HashMap<>();
        update.put(item.getName(), item);
        singleton.dbRef.updateChildren(update);
    }

    static void deleteItem(Canister item) {
        HashMap<String, Object> update = new HashMap<>();
        update.put(item.getName(), null);
        singleton.dbRef.updateChildren(update);

    }

    static void uploadCanisters() {
        if (Canister.getController() != null) {
            putItem(Canister.getController());
        }
        if (Canister.getRescue() != null) {
            putItem(Canister.getRescue());
        }
    }
}
