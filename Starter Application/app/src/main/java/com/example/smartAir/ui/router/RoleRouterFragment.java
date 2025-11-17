package com.example.b07demosummer2024.ui.router;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.b07demosummer2024.R;
import com.example.b07demosummer2024.data.AuthProfileRepo;
import com.example.b07demosummer2024.data.UserProfile;
import com.example.b07demosummer2024.domain.UserRole;

public class RoleRouterFragment extends Fragment {

    private final AuthProfileRepo repo = new AuthProfileRepo();

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

        NavController nav = NavHostFragment.findNavController(this);
        NavOptions clearStack = new NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true)
                .build();

    
        if (repo.getCurrentUser() == null) {
            nav.navigate(R.id.signInFragment, null, clearStack);
            return;
        }

       
        repo.getCurrentUserProfile(profile -> {
            UserRole role = profile.roleEnum();
            if (role == null) {
                nav.navigate(R.id.signInFragment, null, clearStack);
                return;
            }
            switch (role) {
                case CHILD:
                    nav.navigate(R.id.childHomeFragment, null, clearStack); break;
                case PARENT:
                    nav.navigate(R.id.parentHomeFragment, null, clearStack); break;
                case PROVIDER:
                    nav.navigate(R.id.providerHomeFragment, null, clearStack); break;
            }
        }, err -> nav.navigate(R.id.signInFragment, null, clearStack));
    }
}
