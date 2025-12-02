package com.example.smartAir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment implements LoginPresenter.View {

    private EditText emailInput, passwordInput, childIdInput;
    private Spinner roleSpinner;
    private Button loginButton, forgotPasswordButton, goToSignupButton;
    private ProgressBar progressBar;

    private LoginPresenter presenter;

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.log_in_fragment, container, false);

        // FIXED: Match XML IDs exactly
        emailInput = view.findViewById(R.id.loginEmailInput);
        passwordInput = view.findViewById(R.id.loginPasswordInput);
        childIdInput = view.findViewById(R.id.childIdInput);
        loginButton = view.findViewById(R.id.loginButton);
        forgotPasswordButton = view.findViewById(R.id.forgotPasswordButton);
        goToSignupButton = view.findViewById(R.id.goToSignupButton);
        roleSpinner = view.findViewById(R.id.loginRoleSpinner);
        progressBar = view.findViewById(R.id.loginProgressBar);

        presenter = new LoginPresenter(this, new LoginModel());

        // Email Login
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            presenter.loginWithEmail(email, password);
        });

        // Child Login
        childIdInput.setOnEditorActionListener((v, actionId, event) -> {
            String childId = childIdInput.getText().toString().trim();
            presenter.loginChildProfile(childId);
            return true;
        });

        // Forgot Password
        forgotPasswordButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            presenter.recoverPassword(email);
        });

        // Go to Signup
        goToSignupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SignUpFragment.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void showLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String err) {
        Toast.makeText(requireContext(), err, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToParentHome() {
//        Intent intent = new Intent();
//        Activity parentActivity = getActivity();
//        intent.setAction("com.example.smartAir.");
//        startActivity(intent);
        startActivity(new Intent(requireContext(), ParentHomeFragment.class));
//        loadFragment(new ParentHomeFragment());
    }

    @Override
    public void navigateToChildHome() {
        startActivity(new Intent(requireContext(), ChildHomeActivity.class));
    }

    @Override
    public void navigateToProviderHome() {
        startActivity(new Intent(requireContext(), ProviderHomeActivity.class));
    }

    @Override
    public void navigateToRoleDashboard(String role) {

    }

    @Override
    public void navigateToChildDashboard() {

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}