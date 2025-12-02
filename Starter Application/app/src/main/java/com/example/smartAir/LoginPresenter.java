package com.example.smartAir;

import com.example.smartAir.data.UserProfile;
import com.example.smartAir.domain.UserRole;

public class LoginPresenter {
    public interface View {
        void showLoading(boolean loading);
        void showMessage(String msg);
        void showError(String err);
        void navigateToParentHome();
        void navigateToChildHome();
        void navigateToProviderHome();
        void navigateToRoleDashboard(String role);
        void navigateToChildDashboard();
    }

    private final View view;
    private final LoginModel model;

    public LoginPresenter(View view, LoginModel model) {
        this.view = view;
        this.model = model;
    }

    public void loginWithEmail(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            view.showError("Please enter email and password.");
            return;
        }
        view.showLoading(true);

        model.authenticateEmailUser(email, password, new LoginModel.LoginCallback() {
            @Override
            public void onSuccess(String uid, UserRole role) {
                view.showLoading(false);
                view.showMessage("Welcome!");

                UserProfile.initializeEmailProfile(() -> {
                    routeByRole(role);
                });
            }

            @Override
            public void onError(String message) {
                view.showLoading(false);
                view.showError(message);
            }
        });
    }

    public void loginChildProfile(String childId) {
        if (childId == null || childId.isEmpty()) {
            view.showError("Enter child profile ID.");
            return;
        }
        view.showLoading(true);
        model.loginChildProfile(childId, new LoginModel.LoginCallback() {
            @Override
            public void onSuccess(String uid, UserRole role) {
                view.showLoading(false);
                view.showMessage("Child profile loaded.");
                view.navigateToChildHome();
            }

            @Override
            public void onError(String message) {
                view.showLoading(false);
                view.showError(message);
            }
        });
    }

    public void recoverPassword(String email) {
        if (email == null || email.isEmpty()) {
            view.showError("Enter email.");
            return;
        }
        view.showLoading(true);
        model.sendRecoveryEmail(email, new LoginModel.RecoveryCallback() {
            @Override
            public void onSuccess(String message) {
                view.showLoading(false);
                view.showMessage(message);
            }

            @Override
            public void onError(String message) {
                view.showLoading(false);
                view.showError(message);
            }
        });
    }

    private void routeByRole(UserRole role) {
        if (role == null) {
            view.showError("Unknown role");
            return;
        }
        switch (role) {
            case PARENT:
                view.navigateToParentHome();
                break;

            case CHILD:
                view.navigateToChildHome();
                break;

            case PROVIDER:
                view.navigateToProviderHome();
                break;

            default:
                view.showError("Invalid role: " + role);
        }
    }

    public void detach() {
        // no-op
    }
}
