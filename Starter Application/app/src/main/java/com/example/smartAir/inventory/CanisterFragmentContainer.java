package com.example.smartAir.inventory;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.R;

public class CanisterFragmentContainer extends Fragment implements CanisterEntryFragment.FragmentSwitcher, CanisterMainPage.FragmentSwitcher {
    public CanisterFragmentContainer() {
        super(R.layout.inventory_container);
    }

    public static void setGoBackToDashboard(Callback goBackToDashboard) {
        CanisterFragmentContainer.goBackToDashboard = goBackToDashboard;
    }

    static Callback goBackToDashboard;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CanisterMainPage frag = new CanisterMainPage();
        loadFragment(frag);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.inventory_container_switcher, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void goHome() {
        CanisterMainPage frag = new CanisterMainPage();
        loadFragment(frag);
    }

    @Override
    public void goEditCanister(Canister c) {
        CanisterEntryFragment frag = new CanisterEntryFragment();
        frag.setCan(c);
        loadFragment(frag);
    }
}
