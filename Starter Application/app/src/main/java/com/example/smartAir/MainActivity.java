package com.example.smartAir;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.inventory.InventoryFacade;
import com.example.smartAir.medicine.MedicineLogFactory;
import com.example.smartAir.pefAndRecovery.ZoneEntryFacade;
import com.example.smartAir.symptom.DailyCheckInFacade;
import com.example.smartAir.triaging.TriageScreenCreator;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TEMP_USER = "testUsername";

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InventoryFacade.changeUserName(TEMP_USER, ()-> {
            Fragment f= InventoryFacade.generateCanisterMainPage();

            loadFragment(f);

        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    // Provide bindings for navigating back....
}