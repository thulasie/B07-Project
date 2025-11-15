package com.example.cscb07_smart_air;

public class LoginPresenter {

    private final LoginView view;
    private final LoginModel model;

    public LoginPresenter(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;
    }

    // ---------------------------
    // EMAIL LOGIN (Parent/Provider/Child Own Account)
    // ---------------------------
    public void loginWithEmail(String email, String password, String role) {

        if (email.isEmpty() || password.isEmpty()) {
            view.showError("Please fill out all fields.");
            return;
        }

        model.authenticateEmailUser(email, password, role, new LoginModel.LoginCallback() {
            @Override
            public void onSuccess(String uid, String role) {
                view.showMessage("Welcome!");
                view.navigateToRoleDashboard(role);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }

    // ---------------------------
    // CHILD PROFILE LOGIN
    // ---------------------------
    public void loginChildProfile(String childId) {

        if (childId.isEmpty()) {
            view.showError("Enter child ID.");
            return;
        }

        model.loginChildProfile(childId, new LoginModel.LoginCallback() {
            @Override
            public void onSuccess(String uid, String role) {
                view.showMessage("Child profile loaded.");
                view.navigateToChildDashboard();
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }

    // ---------------------------
    // PASSWORD RECOVERY
    // ---------------------------
    public void recoverPassword(String email) {

        if (email.isEmpty()) {
            view.showError("Enter an email.");
            return;
        }

        model.sendRecoveryEmail(email, new LoginModel.RecoveryCallback() {
            @Override
            public void onSuccess(String message) {
                view.showMessage(message);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }
}
