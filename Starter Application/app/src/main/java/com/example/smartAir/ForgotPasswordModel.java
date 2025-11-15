package com.example.cscb07_smart_air;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public interface RecoveryCallback {
        void onSuccess(String message);
        void onError(String error);
    }

    public void sendResetEmail(String email, RecoveryCallback callback) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callback.onSuccess("Password reset email sent.");
                    } else {
                        callback.onError("Unable to send reset email. Check if the email is correct.");
                    }
                });
    }
}
