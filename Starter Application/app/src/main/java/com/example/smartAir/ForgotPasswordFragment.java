package com.example.smartAir;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forgot_password_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        // UI elements
        Button resendButton = view.findViewById(R.id.btnSendResetLink);
        EditText emailInput = view.findViewById(R.id.editTextTextEmailAddress);
        Button backButton = view.findViewById(R.id.backButton);

        // Send reset link
        resendButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(getContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            sendResetLink(email);
        });

        // BACK BUTTON LOGIC
        backButton.setOnClickListener(v -> {
            FragmentTransaction t = getParentFragmentManager().beginTransaction();
            t.replace(R.id.fragment_container, new LoginFragment());
            t.addToBackStack(null);
            t.commit();
        });

        return view;
    }

    private void sendResetLink(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Password reset email sent to: " + email, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(),
                        "Failed to send reset email: " + task.getException().getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
