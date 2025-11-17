package com.example.b07demosummer2024.ui.provider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import com.example.b07demosummer2024.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProviderHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_provider_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnSignOut = view.findViewById(R.id.btnSignOutProvider);
        btnSignOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            NavController nav = NavHostFragment.findNavController(this);
            NavOptions opts = new NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .build();
            nav.navigate(R.id.signInFragment, null, opts);
        });
    }
}
