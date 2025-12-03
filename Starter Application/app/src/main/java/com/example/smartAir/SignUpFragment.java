package com.example.smartAir;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class SignUpFragment extends Fragment {

    private EditText inputEmail, inputPassword;
    private EditText childNameInput, childDOBInput;
    private Spinner roleSpinner;
    private Button signUpButton, backButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    public SignUpFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);

        // UI references
        roleSpinner = view.findViewById(R.id.roleSpinner);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        childNameInput = view.findViewById(R.id.childNameInput);
        childDOBInput = view.findViewById(R.id.childDOBInput);
        signUpButton = view.findViewById(R.id.signUpButton);
        backButton = view.findViewById(R.id.backButton);
        progressBar = view.findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        // Role spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Parent", "Provider"/*, "Child"*/});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Listener to change UI depending on role
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                adjustUIForRole();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        signUpButton.setOnClickListener(v -> handleSignup());
        backButton.setOnClickListener(v -> navigateToLogin());

        return view;
    }

    private void adjustUIForRole() {
        String role = roleSpinner.getSelectedItem().toString().toLowerCase();

        if (role.equals("child")) {
            inputEmail.setVisibility(View.GONE);
            inputPassword.setVisibility(View.GONE);
            childNameInput.setVisibility(View.VISIBLE);
            childDOBInput.setVisibility(View.VISIBLE);
        } else {
            inputEmail.setVisibility(View.VISIBLE);
            inputPassword.setVisibility(View.VISIBLE);
            childNameInput.setVisibility(View.GONE);
            childDOBInput.setVisibility(View.GONE);
        }
    }

    private void handleSignup() {
        String role = roleSpinner.getSelectedItem().toString().toLowerCase();

        if (role.equals("child")) {
            registerChildProfile();
        } else {
            registerParentOrProvider(role);
        }
    }

    private void registerParentOrProvider(String role) {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            showError("Email required.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showError("Password required.");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        showMessage("Account created.");
                        navigateToLogin();
                    } else {
                        showError(task.getException().getMessage());
                    }
                });
    }

    private void registerChildProfile() {
        String name = childNameInput.getText().toString().trim();
        String dob = childDOBInput.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            showError("Child name required.");
            return;
        }
        if (TextUtils.isEmpty(dob)) {
            showError("Child date of birth required.");
            return;
        }

        String parentUid = mAuth.getCurrentUser() == null ? null : mAuth.getCurrentUser().getUid();
        if (parentUid == null) {
            showError("A parent must be logged in to create a child profile.");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("users").child(parentUid).child("children");

        String childId = ref.push().getKey();

        ref.child(childId).setValue(new ChildProfile(name, dob))
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        showMessage("Child profile created.");
                        navigateToLogin();
                    } else {
                        showError(task.getException().getMessage());
                    }
                });
    }

    private void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void navigateToLogin() {
        FragmentTransaction t = getParentFragmentManager().beginTransaction();
        t.replace(R.id.fragment_container, new LoginFragment());
        t.addToBackStack(null);
        t.commit();
    }

    public static class ChildProfile {
        public String name, dob;

        public ChildProfile() {}
        public ChildProfile(String name, String dob) {
            this.name = name;
            this.dob = dob;
        }
    }
}