package com.example.cscb07_smart_air;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.*;

public class SignUpView extends Fragment {

    private EditText emailField, passwordField;
    private EditText childNameField, childDOBField;
    private Spinner roleSpinner;
    private Button signUpButton, createChildButton, backButton;

    private SignUpPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.signup_view, container, false);

        roleSpinner = view.findViewById(R.id.roleSpinner);
        emailField = view.findViewById(R.id.inputEmail);
        passwordField = view.findViewById(R.id.inputPassword);

        childNameField = view.findViewById(R.id.childNameInput);
        childDOBField = view.findViewById(R.id.childDOBInput);
        createChildButton = view.findViewById(R.id.createChildProfileButton);

        signUpButton = view.findViewById(R.id.signUpButton);
        backButton = view.findViewById(R.id.backButton);

        presenter = new SignUpPresenter(this, new SignUpModel());

        // Standard signup (Parent / Provider / Child w/ email)
        signUpButton.setOnClickListener(v -> {
            String role = roleSpinner.getSelectedItem().toString().toLowerCase();
            presenter.registerEmailUser(
                    emailField.getText().toString().trim(),
                    passwordField.getText().toString().trim(),
                    role
            );
        });

        // Child profile signup (no email)
        createChildButton.setOnClickListener(v -> {
            String parentUid = ""; // TODO: pass logged-in parent UID here
            presenter.createChildProfile(parentUid,
                    childNameField.getText().toString(),
                    childDOBField.getText().toString());
        });

        backButton.setOnClickListener(v -> navigateToLogin());

        return view;
    }

    // View Interface Methods
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void navigateToLogin() {
        loadFragment(new LoginView());
    }

    private void loadFragment(Fragment f) {
        FragmentTransaction t = getParentFragmentManager().beginTransaction();
        t.replace(R.id.fragment_container, f);
        t.addToBackStack(null);
        t.commit();
    }
}
