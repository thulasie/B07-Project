package com.example.smartAir.ui.router;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.LoginFragment;
import com.example.smartAir.R;
import com.example.smartAir.data.AuthProfileRepo;
import com.example.smartAir.domain.UserRole;
import com.example.smartAir.onboarding.OnboardingContainerFragment;
import com.example.smartAir.ui.child.ChildHomeFragment;
import com.example.smartAir.ui.parent.ParentHomeFragment;
import com.example.smartAir.ui.provider.ProviderHomeFragment;

public class RoleRouterFragment extends Fragment {

    private final AuthProfileRepo repo;

    public RoleRouterFragment() {
        this.repo = new AuthProfileRepo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_role_router, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (repo.getCurrentUser() == null) {
            navigateTo(new LoginFragment());
            return;
        }

        repo.getCurrentUserProfile(profile -> {
            UserRole role = profile.roleEnum();
            if (role == null) {
                navigateTo(new LoginFragment());
                return;
            }

            SharedPreferences a = requireActivity().getSharedPreferences(getString(R.string.shared_preferences_key),
                    Context.MODE_PRIVATE);
            if (a.getBoolean("not_first_launch", false)) {
                switch (role) {
                    case CHILD:
                        navigateTo(new ChildHomeFragment());
                        break;
                    case PARENT:
                        navigateTo(new ParentHomeFragment());
                        break;
                    case PROVIDER:
                        navigateTo(new ProviderHomeFragment());
                        break;
                }
            } else {
                a.edit().putBoolean("not_first_launch", true);

                Bundle args = new Bundle();
                args.putSerializable("role", role);

                OnboardingContainerFragment onboarding = new OnboardingContainerFragment();
                onboarding.setArguments(args);

                navigateTo(onboarding);
            }
        }, err -> navigateTo(new LoginFragment()));
    }

    public void navigateTo(Fragment f) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, f);

        transaction.addToBackStack(null);
        transaction.commit();
    }
}
