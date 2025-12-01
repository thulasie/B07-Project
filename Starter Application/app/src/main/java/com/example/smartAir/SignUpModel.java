package com.example.smartAir;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SignUpModel {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    // Create a user document under "users/{uid}"
    public Task<Void> createUserDocument(String uid, Map<String, Object> data) {
        return firestore.collection("users").document(uid).set(data);
    }

    // Create a child profile under users/{parentUid}/children
    public Task<DocumentReference> createChildProfile(String parentUid, Map<String, Object> childData) {
        return firestore.collection("users")
                .document(parentUid)
                .collection("children")
                .add(childData);
    }
}
