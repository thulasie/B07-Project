package com.example.b07demosummer2024;

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

import com.example.b07demosummer2024.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment implements SignUpPresenter.View {

    private EditText inputEmail, inputPassword;
    private EditText childNameInput, childDOBInput;
    private Spinner roleSpinner;
    private Button signUpButton, createChildProfileButton, backButton;
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
        childNameInput = view.findViewById(R.id.childNameInput);
        childDOBInput = view.findViewById(R.id.childDOBInput);
        signUpButton = view.findViewById(R.id.signUpButton);
        createChildProfileButton = view.findViewById(R.id.createChildProfileButton);
        backButton = view.findViewById(R.id.backButton);
        progressBar = view.findViewById(R.id.progressBar);

        // spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Parent", "Provider", "Child (Email)", "Child (Profile)"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // firebase + presenter
        mAuth = FirebaseAuth.getInstance();
        presenter = new SignUpPresenter(this, new SignUpModel());

        signUpButton.setOnClickListener(v -> {
            String sel = roleSpinner.getSelectedItem().toString();
            String role = parseRole(sel);

            if ("child_profile".equals(role)) {
                showError("Use 'Create Child Profile' for child profiles without email.");
                return;
            }
            presenter.registerEmailUser(inputEmail.getText().toString().trim(),
                    inputPassword.getText().toString().trim(),
                    role);
        });

        createChildProfileButton.setOnClickListener(v -> {
            // require logged in parent
            if (mAuth.getCurrentUser() == null) {
                showError("Parent must be logged in to create child profiles.");
                return;
            }
            String parentUid = mAuth.getCurrentUser().getUid();
            presenter.createChildProfile(parentUid,
                    childNameInput.getText().toString().trim(),
                    childDOBInput.getText().toString().trim());
        });

        backButton.setOnClickListener(v -> navigateToLogin());

        return view;
    }

    private String parseRole(String sel) {
        sel = sel.toLowerCase();
        if (sel.contains("parent")) return "parent";
        if (sel.contains("provider")) return "provider";
        if (sel.contains("email")) return "child";
        return "child_profile";
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
