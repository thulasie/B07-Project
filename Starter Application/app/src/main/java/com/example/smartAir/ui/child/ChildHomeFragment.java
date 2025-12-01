package com.example.smartAir.ui.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.Navigation;

import com.example.smartAir.R;

public class ChildHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_child_home, container, false);

        // find Daily Check-in button
        Button buttonDaily = v.findViewById(R.id.buttonDailyCheckIn);
        buttonDaily.setOnClickListener(view -> {
            NavController nav = NavHostFragment.findNavController(this);

            Bundle args = new Bundle();
            args.putString("author", "CHILD");  // tell DailyCheckIn it's a child entry

            nav.navigate(R.id.action_childHome_to_dailyCheckIn, args);
        });

        return v;
    }
}
