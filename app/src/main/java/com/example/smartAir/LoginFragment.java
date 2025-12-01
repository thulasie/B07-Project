package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07demosummer2024.R;

public class LoginFragment extends Fragment implements LoginPresenter.View {

    private EditText emailInput, passwordInput, childIdInput;
    private Spinner roleSpinner;
    private Button loginButton, goSignupButton, forgotButton;
    private ProgressBar progressBar;

    private LoginPresenter presenter;

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_in_fragment, container, false);

        emailInput = view.findViewById(R.id.loginEmailInput);
        passwordInput = view.findViewById(R.id.loginPasswordInput);
        childIdInput = view.findViewById(R.id.childIdInput); // optional in layout
        roleSpinner = view.findViewById(R.id.loginRoleSpinner);
        loginButton = view.findViewById(R.id.loginButton);
        goSignupButton = view.findViewById(R.id.goToSignupButton);
        forgotButton = view.findViewById(R.id.forgotPasswordButton);
        progressBar = view.findViewById(R.id.loginProgressBar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Parent", "Provider", "Child (Email)", "Child (Profile)"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        presenter = new LoginPresenter(this, new LoginModel());

        loginButton.setOnClickListener(v -> {
            String roleSel = roleSpinner.getSelectedItem().toString().toLowerCase();
            if (roleSel.contains("profile")) {
                // child profile login - use childIdInput
                String childId = childIdInput != null ? childIdInput.getText().toString().trim() : "";
                presenter.loginChildProfile(childId);
            } else {
                String email = emailInput.getText().toString().trim();
                String pw = passwordInput.getText().toString().trim();
                presenter.loginWithEmail(email, pw);
            }
        });

        goSignupButton.setOnClickListener(v -> navigateToFragment(new SignUpFragment()));

        forgotButton.setOnClickListener(v -> navigateToFragment(new ForgotPasswordFragment()));

        return view;
    }

    @Override
    public void showLoading(boolean loading) {
        if (progressBar == null) return;
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToRoleDashboard(String role) {
        navigateToFragment(new HomeFragment());
    }

    @Override
    public void navigateToChildDashboard() {
        navigateToFragment(new HomeFragment());
    }

    private void navigateToFragment(Fragment f) {
        FragmentTransaction t = getParentFragmentManager().beginTransaction();
        t.replace(R.id.fragment_container, f);
        t.addToBackStack(null);
        t.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detach();
    }
}
