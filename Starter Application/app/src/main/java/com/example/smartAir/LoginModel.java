package com.example.smartAir;

import androidx.annotation.NonNull;

import com.example.smartAir.domain.UserRole;
import com.example.smartAir.userinfo.UserBasicInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.*;

import java.util.HashMap;
import java.util.Map;

public class LoginModel {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public interface LoginCallback {
        void onSuccess(String email, UserRole role);
        void onError(String message);
    }

    public interface RecoveryCallback {
        void onSuccess(String message);
        void onError(String message);
    }

    // Authenticate email & return role
    public void authenticateEmailUser(String email, String password, LoginCallback callback) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener((result) -> {
            String uid = result.getUser().getUid();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            db.getReference("roles").child(uid).child("role")
                    .get().addOnSuccessListener((task)-> {
                UserRole r = UserRole.valueOf(((String) task.getValue()));
                callback.onSuccess(email, r);
            });
        }).addOnFailureListener((error) -> {
            callback.onError(error.getMessage());
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
