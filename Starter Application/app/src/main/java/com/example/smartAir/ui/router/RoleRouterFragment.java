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
import com.example.smartAir.data.UserProfile;
import com.example.smartAir.domain.UserRole;
import com.example.smartAir.ui.child.ChildHomeFragment;
import com.example.smartAir.ui.onboarding.OnboardingCreator;
import com.example.smartAir.ParentHomeFragment;
import com.example.smartAir.ui.provider.ProviderHomeFragment;

public class RoleRouterFragment extends Fragment {

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

        if (UserProfile.getProfileSingleton() == null) {
            navigateTo(new LoginFragment());
            System.out.println("Not logged in!");
            return;
        }

        UserRole role = UserProfile.getProfileSingleton().getRole();

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

            OnboardingCreator creator = new OnboardingCreator(role, () -> {
                System.out.println("Put a function here to navigate elsewhere...");
            });

            navigateTo(creator.createOnboardingFragment());
        }
    }

    public void navigateTo(Fragment f) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, f);

        transaction.addToBackStack(null);
        transaction.commit();
    }
}
