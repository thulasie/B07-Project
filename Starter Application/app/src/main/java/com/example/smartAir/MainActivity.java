package com.example.smartAir;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.databaseLog.DatabaseLogType;
import com.example.smartAir.databaseLog.view.LogFragment;
import com.example.smartAir.domain.Zone;
import com.example.smartAir.inventory.InventoryFacade;
import com.example.smartAir.inventory.InventoryMarking;
import com.example.smartAir.medicine.MedicineLogFragmentFactory;
import com.example.smartAir.pefAndRecovery.BreathInformationProvider;
import com.example.smartAir.pefAndRecovery.ZoneEntryFacade;
import com.example.smartAir.triaging.TriageScreenCreator;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private static final String TEMP_USER = "testUsername";

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);/*
        InventoryFacade.setInventoryMarking(InventoryMarking.PARENT);
        InventoryFacade.changeUserName(TEMP_USER, ()-> {
            Fragment f= InventoryFacade.generateCanisterMainPage(() -> System.out.println("I'm so so happy!"));

            loadFragment(f);
        });*/
        //loadFragment(MedicineLogFragmentFactory.create("testUser", () -> System.out.println("Exit")));



        /*TriageScreenCreator creator = new TriageScreenCreator(TEMP_USER);
        creator.setHomeController(() -> System.out.println("going home"));

        ZoneEntryFacade.changeUser(TEMP_USER, ()-> {
            creator.setBreathInformationProvider(ZoneEntryFacade.getBreathProvider());
            loadFragment(creator.createTriageFragment());
        });*/
        HashSet<DatabaseLogType> wanted = new HashSet<>();
        Date startDate = new Date(1764663968695L), endDate = new Date();
        wanted.add(DatabaseLogType.RESCUE_USE);

        loadFragment(new LoginFragment());
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