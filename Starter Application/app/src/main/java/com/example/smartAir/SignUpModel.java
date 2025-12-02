package com.example.smartAir;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class SignUpModel {

    private final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference user;

    // Create a user document under "users/{uid}"
    public Task<Void> createUserDocument(String uid, Map<String, Object> data) {

        // POINT TO: users/uid
        user = db.getReference("users").child(uid);

        // Save user data
        return user.setValue(data);
    }

    // Create a child profile under: users/{parentUid}/children/{childUid}
    public Task<Void> createChildProfile(String parentUid, Map<String, Object> childData) {

        DatabaseReference childRef =
                db.getReference("users")
                        .child(parentUid)
                        .child("children")
                        .push();   // generate childID

        return childRef.setValue(childData);
    }
}
