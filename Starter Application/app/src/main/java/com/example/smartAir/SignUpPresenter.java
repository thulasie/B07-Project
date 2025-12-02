package com.example.smartAir;

import android.text.TextUtils;

import com.example.smartAir.domain.UserRole;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class SignUpPresenter {
    public interface View {
        void showLoading(boolean loading);
        void showMessage(String msg);
        void showError(String err);
        void navigateToLogin();
    }

    private final View view;
    private final FirebaseAuth auth;

    public SignUpPresenter(View view) {
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
    }

    public void registerEmailUser(String email, String password, String role) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            view.showError("Email and password are required.");
            return;
        }

        if (!email.contains("@")){
            email = email + "@smartair.com";
        }

        final String user = email;

        view.showLoading(true);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        view.showLoading(false);
                        view.showError("Signup failed: " + (task.getException() != null ? task.getException().getMessage() : "unknown"));
                        return;
                    }

                    SignUpModel.registerUser(task.getResult().getUser().getUid(), user, UserRole.valueOf(role.toUpperCase()), new SignUpModel.OnSuccessListener() {
                        @Override
                        public void onSuccess() {
                            view.showLoading(false);
                            view.showMessage("Try logging in now!");
                            view.navigateToLogin();
                        }

                        @Override
                        public void onFailure() {
                            view.showLoading(false);
                            view.showError("Sign up failed for some reason");
                        }
                    });
                });
    }

    public void detach() {
        // no-op for now
    }
}
