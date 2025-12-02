package com.example.smartAir;

import com.example.smartAir.domain.UserRole;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpModel {

    public interface OnSuccessListener {
        void onSuccess();
        void onFailure();
    }

    public static void registerUser(String userID, String email, UserRole role, OnSuccessListener a) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("role", role);

        FirebaseDatabase.getInstance()
                .getReference("roles").child(userID).setValue(map).addOnCompleteListener((v)-> {
                    if (v.isSuccessful()) {
                        a.onSuccess();
                    } else {
                        a.onFailure();
                    }
                });
    }
}
