package com.example.smartAir;

import android.text.TextUtils;

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
    private final SignUpModel model;
    private final FirebaseAuth auth;

    public SignUpPresenter(View view, SignUpModel model) {
        this.view = view;
        this.model = model;
        this.auth = FirebaseAuth.getInstance();
    }

    public void registerEmailUser(String email, String password, String role) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            view.showError("Email and password are required.");
            return;
        }

        view.showLoading(true);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        view.showLoading(false);
                        view.showError("Signup failed: " + (task.getException() != null ? task.getException().getMessage() : "unknown"));
                        return;
                    }

                    String uid = auth.getCurrentUser().getUid();
                    Map<String, Object> data = new HashMap<>();
                    data.put("email", email);
                    data.put("role", role.toLowerCase());
                    data.put("createdAt", System.currentTimeMillis());

                    model.createUserDocument(uid, data)
                            .addOnCompleteListener(docTask -> {
                                view.showLoading(false);
                                if (docTask.isSuccessful()) {
                                    view.showMessage("Account created. Please login.");
                                    view.navigateToLogin();
                                } else {
                                    view.showError("Failed to save user data: " + (docTask.getException() != null ? docTask.getException().getMessage() : "unknown"));
                                }
                            });
                });
    }

    public void createChildProfile(String parentUid, String childName, String childDOB) {
        if (TextUtils.isEmpty(parentUid)) {
            view.showError("Parent must be logged in to create a child profile.");
            return;
        }
        if (TextUtils.isEmpty(childName)) {
            view.showError("Child name is required.");
            return;
        }

        view.showLoading(true);
        Map<String, Object> childData = new HashMap<>();
        childData.put("name", childName);
        childData.put("dob", childDOB);
        childData.put("createdAt", System.currentTimeMillis());

        model.createChildProfile(parentUid, childData)
                .addOnCompleteListener(task -> {
                    view.showLoading(false);
                    if (task.isSuccessful()) {
                        view.showMessage("Child profile created.");
                    } else {
                        view.showError("Failed to create child profile: " + (task.getException() != null ? task.getException().getMessage() : "unknown"));
                    }
                });
    }

    public void detach() {
        // no-op for now
    }
}
