package com.example.smartAir;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment implements SignUpPresenter.View {

    private EditText inputEmail, inputPassword;
    private EditText childNameInput, childDOBInput;
    private Spinner roleSpinner;
    private Button signUpButton, backButton;
    private ProgressBar progressBar;

    private SignUpPresenter presenter;
    private FirebaseAuth mAuth;

    public SignUpFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);

        // bind
        roleSpinner = view.findViewById(R.id.roleSpinner);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        signUpButton = view.findViewById(R.id.signUpButton);
        backButton = view.findViewById(R.id.backButton);
        progressBar = view.findViewById(R.id.progressBar);

        // spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Parent", "Provider"/*, "Child"*/});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // firebase + presenter
        mAuth = FirebaseAuth.getInstance();
        presenter = new SignUpPresenter(this);

        signUpButton.setOnClickListener(v -> {
            String sel = roleSpinner.getSelectedItem().toString();
            String role = parseRole(sel);

            if ("child".equalsIgnoreCase(role)) {
                presenter.registerEmailUser(inputEmail.getText().toString().trim() + "@smartair.com",
                        inputPassword.getText().toString().trim(), role);
            } else {
                presenter.registerEmailUser(inputEmail.getText().toString().trim(),
                        inputPassword.getText().toString().trim(), role);
            }
        });

        backButton.setOnClickListener(v -> navigateToLogin());

        return view;
    }

    private String parseRole(String sel) {
        sel = sel.toLowerCase();
        if (sel.contains("parent")) return "parent";
        if (sel.contains("provider")) return "provider";
        return "child";
    }

    // View methods
    @Override
    public void showLoading(boolean loading) {
        if (progressBar != null) progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
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
    public void navigateToLogin() {
        navigateToFragment(new LoginFragment());
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
