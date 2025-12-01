package com.example.smartAir.ui.parent;

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

import com.example.smartAir.R;
import com.google.firebase.auth.FirebaseAuth;

public class ParentHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //public NavController
        NavController nav = NavHostFragment.findNavController(this);

        // Sign out button
        Button btnSignOut = view.findViewById(R.id.btnSignOutParent);
        btnSignOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            NavOptions opts = new NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true) // clear back stack
                    .build();
            nav.navigate(R.id.signInFragment, null, opts);
        });

        // Daily Check-in（R5）
        Button buttonDaily = view.findViewById(R.id.buttonDailyCheckIn);
        buttonDaily.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("author", "PARENT");   // tell DailyCheckIn it's Parent entry
            nav.navigate(R.id.action_parentHome_to_dailyCheckIn, args);
        });
    }
}
