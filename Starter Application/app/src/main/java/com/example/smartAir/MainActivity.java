package com.example.smartAir;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.pefAndRecovery.ZoneChangeMonitor;
import com.example.smartAir.pefAndRecovery.ZoneEntryFacade;
import com.example.smartAir.triaging.TriageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

                    ZoneEntryFacade.changeUser(auth.getUid(), () -> {
                        loadFragment(ZoneEntryFacade.createPersonalBestEntry());
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