package com.example.smartAir.ui.onboarding;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.smartAir.R;
import com.example.smartAir.domain.UserRole;
import com.example.smartAir.ui.child.ChildHomeFragment;
import com.example.smartAir.ui.parent.ParentHomeFragment;
import com.example.smartAir.ui.provider.ProviderHomeFragment;

import java.io.Serializable;
import java.util.Objects;

public class OnboardingPanelFragment extends Fragment {

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

            Serializable obj = args.getSerializable("user_role");

            exitButton.setOnClickListener(v -> {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                Fragment nextFragment;
                UserRole role;
                if (obj instanceof UserRole) {
                    role = (UserRole) obj;
                    switch (role) {
                        case CHILD:
                            nextFragment = new ChildHomeFragment();
                            break;
                        case PARENT:
                            nextFragment = new ParentHomeFragment();
                            break;
                        default:
                            nextFragment = new ProviderHomeFragment();
                            break;
                    }
                } else {
                    nextFragment = new ChildHomeFragment();
                }

                transaction.replace(R.id.fragment_container, nextFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }
    }

}