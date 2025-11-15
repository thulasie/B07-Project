package com.example.cscb07_smart_air;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.*;

public class ForgotPasswordView extends Fragment {

    private EditText emailInput;
    private Button resetButton, backButton;

    private ForgotPasswordPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forgot_password_view, container, false);

        emailInput = view.findViewById(R.id.inputEmail);
        resetButton = view.findViewById(R.id.resetButton);
        backButton = view.findViewById(R.id.backButton);

        presenter = new ForgotPasswordPresenter(this, new ForgotPasswordModel());

        resetButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            presenter.recoverPassword(email);
        });

        backButton.setOnClickListener(v -> {
            loadFragment(new LoginView());
        });

        return view;
    }

    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void loadFragment(Fragment f) {
        FragmentTransaction t = getParentFragmentManager().beginTransaction();
        t.replace(R.id.fragment_container, f);
        t.addToBackStack(null);
        t.commit();
    }
}

