package com.example.smartAir;

import androidx.annotation.NonNull;

import com.example.smartAir.data.UserProfile;
import com.example.smartAir.domain.UserRole;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.HashMap;
import java.util.Map;

public class LoginModel {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public interface LoginCallback {
        void onSuccess(String uid, UserRole role);
        void onError(String message);
    }

    public interface RecoveryCallback {
        void onSuccess(String message);
        void onError(String message);
    }

    // Authenticate email & return role
    public void authenticateEmailUser(String email, String password, LoginCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        callback.onError(task.getException() != null ? task.getException().getMessage() : "Authentication failed");
                        return;
                    }
                    String uid = auth.getCurrentUser().getUid();
                    // fetch role from Firestore

                    UserProfile.initializeEmailProfile(() -> {
                        callback.onSuccess(uid, UserProfile.getProfileSingleton().getRole());
                    });/*

                    firestore.collection("users").document(uid).get()
                            .addOnCompleteListener(docTask -> {
                                if (!docTask.isSuccessful() || !docTask.getResult().exists()) {
                                    callback.onError("User data not found.");
                                    return;
                                }
                                String role = docTask.getResult().getString("role");
                                if (role == null) role = "child";
                                callback.onSuccess(uid, UserRole.extract(role));
                            });*/
                });
    }

    // Login by child profile id (collectionGroup)
    public void loginChildProfile(String childDocId, LoginCallback callback) {
        // search children across all parents
        firestore.collectionGroup("children")
                .whereEqualTo(FieldPath.documentId(), childDocId)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        callback.onError(task.getException() != null ? task.getException().getMessage() : "Query failed");
                        return;
                    }
                    QuerySnapshot snap = task.getResult();
                    if (snap == null || snap.isEmpty()) {
                        callback.onError("Child profile not found.");
                        return;
                    }
                    // assume first hit
                    DocumentSnapshot childDoc = snap.getDocuments().get(0);
                    DocumentReference parentDocRef = childDoc.getReference().getParent().getParent(); // users/{parentUid}
                    if (parentDocRef == null) {
                        callback.onError("Parent not found.");
                        return;
                    }
                    String parentUid = parentDocRef.getId();
                    callback.onSuccess(parentUid, UserRole.CHILD);
                });
    }

    public void sendRecoveryEmail(String email, RecoveryCallback callback) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) callback.onSuccess("Password reset email sent.");
                    else callback.onError(task.getException() != null ? task.getException().getMessage() : "Failed to send reset email");
                });
    }
}
