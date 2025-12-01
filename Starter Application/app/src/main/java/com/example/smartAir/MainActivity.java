package com.example.smartAir;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.pefAndRecovery.ZoneChangeMonitor;
import com.example.smartAir.pefAndRecovery.ZoneEntryFacade;
import com.example.smartAir.triaging.TriageFragment;
import com.example.smartAir.triaging.TriageScreenCreator;
import com.example.smartAir.triaging.ZoneStepsProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance("https://b07projectlogin-default-rtdb.firebaseio.com");

        FirebaseAuth.getInstance().signInWithEmailAndPassword("elderflowerings@gmail.com", "thereisnogoodandbad")
                .addOnCompleteListener(task -> {
                    System.out.println("Logged in !");
                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    TriageScreenCreator creator = new TriageScreenCreator(auth.getUid());

                    ZoneEntryFacade.changeUser(auth.getUid(), () -> {
                        creator.setHomeController(() -> {
                            System.out.println("Going home...");
                        });

                        creator.setZoneStepsProvider((inflater, zone) -> {
                            Chip view = (Chip) inflater.inflate(R.layout.triage_symptom_chip, null);
                            view.setText(zone + " plan");

                            return view;
                        });

                        creator.setBreathInformationProvider(ZoneEntryFacade.getBreathProvider());



                        loadFragment(creator.createTriageFragment());
                    });
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
}