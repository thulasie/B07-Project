package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.userinfo.UserBasicInfo;

public class LoginFragment extends Fragment implements LoginPresenter.View {

    private EditText emailInput, passwordInput, childIdInput;
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
        loginButton = view.findViewById(R.id.loginButton);
        goSignupButton = view.findViewById(R.id.goToSignupButton);
        forgotButton = view.findViewById(R.id.forgotPasswordButton);
        progressBar = view.findViewById(R.id.loginProgressBar);

        presenter = new LoginPresenter(this, new LoginModel());

        loginButton.setOnClickListener(v -> {

            String email = emailInput.getText().toString().trim();
            String pw = passwordInput.getText().toString().trim();
            presenter.loginWithEmail(email, pw);
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
    public void navigateToDashboard() {
        System.out.println("Logged in !");
        navigateToFragment(UserBasicInfo.getHomeFragment());
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
