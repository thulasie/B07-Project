package com.example.cscb07_smart_air;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.*;

public class LoginView extends Fragment {

    private EditText emailField, passwordField, childIdField;
    private Spinner roleSpinner;
    private Button loginButton, childLoginButton, backButton;
    private TextView signUpRedirect, forgotPasswordButton;

    private LoginPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_view, container, false);

        // UI elements
        emailField = view.findViewById(R.id.inputEmail);
        passwordField = view.findViewById(R.id.inputPassword);
        childIdField = view.findViewById(R.id.childIdInput);
        roleSpinner = view.findViewById(R.id.roleSpinner);

        loginButton = view.findViewById(R.id.loginButton);
        childLoginButton = view.findViewById(R.id.childProfileLoginButton);
        forgotPasswordButton = view.findViewById(R.id.forgotPasswordText);
        signUpRedirect = view.findViewById(R.id.signUpRedirect);
        backButton = view.findViewById(R.id.backButton);

        presenter = new LoginPresenter(this, new LoginModel());

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String pass = passwordField.getText().toString().trim();
            String role = roleSpinner.getSelectedItem().toString().toLowerCase();
            presenter.loginWithEmail(email, pass, role);
        });

        childLoginButton.setOnClickListener(v -> {
            presenter.loginChildProfile(childIdField.getText().toString().trim());
        });

        forgotPasswordButton.setOnClickListener(v ->
                presenter.recoverPassword(emailField.getText().toString().trim()));

        backButton.setOnClickListener(v -> loadFragment(new HomeFragment()));
        signUpRedirect.setOnClickListener(v -> loadFragment(new SignUpFragment()));

        return view;
    }

    // View interface functions
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void navigateToRoleDashboard(String role) {
        switch (role) {
            case "parent":
                loadFragment(new ParentDashboardFragment());
                break;
            case "provider":
                loadFragment(new ProviderDashboardFragment());
                break;
            case "child":
                loadFragment(new ChildDashboardFragment());
                break;
        }
    }

    public void navigateToChildDashboard() {
        loadFragment(new ChildDashboardFragment());
    }

    private void loadFragment(Fragment f) {
        FragmentTransaction t = getParentFragmentManager().beginTransaction();
        t.replace(R.id.fragment_container, f);
        t.addToBackStack(null);
        t.commit();
    }
}

