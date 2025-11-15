package com.example.cscb07_smart_air;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class LoginModel {

    private final FirebaseAuth mAuth;
    private final FirebaseDatabase db;

    public LoginModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
    }

    // ---------------------------
    // PARENT / PROVIDER / CHILD (OWN ACCOUNT)
    // ---------------------------
    public void authenticateEmailUser(String email, String password, String role, LoginCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        callback.onError("Invalid email or password.");
                        return;
                    }

                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user == null) {
                        callback.onError("Unexpected error occurred.");
                        return;
                    }

                    if (!user.isEmailVerified()) {
                        user.sendEmailVerification();
                        callback.onError("Email not verified. Please check your inbox.");
                        return;
                    }

                    validateRole(user.getUid(), role, callback);
                });
    }

    // Confirm user’s database role matches login attempt
    private void validateRole(String uid, String expectedRole, LoginCallback callback) {

        DatabaseReference ref = db.getReference("users")
                .child(expectedRole + "s")   // parents, providers, children
                .child(uid);

        ref.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful() || !task.getResult().exists()) {
                callback.onError("This account does not belong to a " + expectedRole);
            } else {
                callback.onSuccess(uid, expectedRole);
            }
        });
    }

    // ---------------------------
    // CHILD UNDER PARENT LOGIN (NO EMAIL REQUIRED)
    // ---------------------------
    public void loginChildProfile(String childId, LoginCallback callback) {

        DatabaseReference ref = db.getReference("users")
                .child("children")
                .child(childId);

        ref.get().addOnCompleteListener(task -> {

            if (!task.isSuccessful() || !task.getResult().exists()) {
                callback.onError("Child profile not found.");
                return;
            }

            Boolean ownAccount = task.getResult().child("hasOwnAccount").getValue(Boolean.class);

            if (ownAccount == null || ownAccount) {
                callback.onError("This child uses an email account. Use email login.");
            } else {
                callback.onSuccess(childId, "child_profile");
            }
        });
    }

    // ---------------------------
    // PASSWORD RECOVERY
    // ---------------------------
    public void sendRecoveryEmail(String email, RecoveryCallback callback) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callback.onSuccess("Password reset email sent.");
                    } else {
                        callback.onError("No account found for this email.");
                    }
                });
    }


    // ---------------------------
    // Callback Interfaces
    // ---------------------------
    public interface LoginCallback {
        void onSuccess(String uid, String role);
        void onError(String message);
    }

    public interface RecoveryCallback {
        void onSuccess(String message);
        void onError(String message);
    }
}

