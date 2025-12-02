package com.example.smartAir;

import com.example.smartAir.domain.UserRole;
import com.example.smartAir.userinfo.UserBasicInfo;

public class LoginPresenter {
    public interface View {
        void showLoading(boolean loading);
        void showMessage(String msg);
        void showError(String err);
        void navigateToDashboard();
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
        if (!email.contains("@")) {
            email = email + "@smartair.com";
        }

        view.showLoading(true);
        model.authenticateEmailUser(email, password, new LoginModel.LoginCallback() {
            @Override
            public void onSuccess(String uid, UserRole role) {
                view.showLoading(false);
                view.showMessage("Welcome! Just wait a moment while we get set up...");
                System.out.println(role);

                UserBasicInfo.initialize(uid, role);
                view.navigateToDashboard();
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

    public void detach() {
        // no-op
    }
}
