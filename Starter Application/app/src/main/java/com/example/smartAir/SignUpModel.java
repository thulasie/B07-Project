package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

//public class SignUpModel {
//    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//
//    // Create a user document under "users/{uid}"
//    public Task<Void> createUserDocument(String uid, Map<String, Object> data) {
//        return firestore.collection("users").document(uid).set(data);
//    }
//
//    // Create a child profile under users/{parentUid}/children
//    public Task<DocumentReference> createChildProfile(String parentUid, Map<String, Object> childData) {
//        return firestore.collection("users")
//                .document(parentUid)
//                .collection("children")
//                .add(childData);
//    }

//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
//
//    }
//    }
//}


/*TODO
1. change it to onCreateView
2. save data according to the spinner
3. add additional child into key depending on the spinner
 */
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

/*TODO
1. grab key from spinner and reloacte data with appropiate key, child, and value
 */