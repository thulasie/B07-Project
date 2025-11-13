package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class OnboardingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.onboarding_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        assert args != null;
        Button exitButton = view.findViewById(R.id.onboarding_exit_button);

        if (!args.getBoolean("onboarding_exit_page")) {
            ((TextView) view.findViewById(R.id.onboarding_caption))
                    .setText(args.getString("onboarding_caption"));
            ((ImageView) view.findViewById(R.id.onboarding_image))
                    .setImageResource(args.getInt("onboarding_image"));

        } else {
            ((ImageView) view.findViewById(R.id.onboarding_image))
                    .setImageResource(R.drawable.onboarding_tab_indicator);
            ((TextView) view.findViewById(R.id.onboarding_caption))
                    .setText("You're finished! Go on with your day :)");
            exitButton.setVisibility(View.VISIBLE);
            exitButton.setText("Exit onboarding");

            exitButton.setOnClickListener(v -> {

                Objects.requireNonNull(this.getActivity()).finish();
                // Add a side effect
            });
        }
    }

}