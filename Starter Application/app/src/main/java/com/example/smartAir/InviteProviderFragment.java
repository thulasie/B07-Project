package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class InviteProviderFragment extends Fragment {
    private Spinner spinnerInvite, spinnerProvider;
    private Button process;
    private FirebaseDatabase db;
    private DatabaseReference parentRef, providerRef;
    private ArrayList<String> providerNames;
    private FirebaseUser user;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_manage_provider, container, false);
            spinnerInvite = view.findViewById(R.id.spinnerInvite);
            spinnerProvider = view.findViewById(R.id.spinnerPickProvider);
            process = view.findViewById(R.id.buttonInvite);
            db = FirebaseDatabase.getInstance();
            parentRef = db.getReference("users/parent");
            providerRef = db.getReference("users/provider");
            providerNames = new ArrayList<String>();


        //adding providerRef options into spinner
            //it is set to parentEmail for testing but should be userEmail.toString()
            providerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    providerNames.add("Pick a provider");
                    for(DataSnapshot childSnapShot : snapshot.getChildren()){
                        providerNames.add(childSnapShot.getKey());
                    }
                    setUpSpinner(spinnerProvider, providerNames);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.err.println("Error: " + error.getMessage());
                }});

            ArrayList<String> service = new ArrayList<String>();
            service.add("Pick a service");
            service.add("Invite");
            service.add("Revoke");
            setUpSpinner(spinnerInvite, service);

            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                userEmail = user.getEmail();
            }

            process.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    providerExist();
                }
            });


        return view;
    }

    private void setUpSpinner(Spinner spinner, ArrayList<String> list){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //when process clicked
    private void InviteOrRevoke(boolean exist, String provider){
        String operation = spinnerInvite.getSelectedItem().toString();
        if(operation.equals("Invite")){
            processInvite(exist, provider);
        }
        else if(operation.equals("Revoke")){
            processRevoke(exist, provider);
        }
        else{
            Toast.makeText(getContext(), "Please select Invite or Revoke", Toast.LENGTH_SHORT).show();
        }
    }
    //When button spinner invite is selected
    private void processInvite(boolean exist, String provider){
        if(!exist){
            Date now = new Date();
            long code = now.getTime();
            //should be userEmail.toString() and not parentEmail
            parentRef.child("parentEmail").child("provider").child(provider).setValue(code).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Invite is sent, capture the code:" + code, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Something must have gone wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getContext(), provider + " is already invited", Toast.LENGTH_SHORT).show();
        }
    }
    private void processRevoke(boolean exist, String provider){
        //if not say there's no such provider invited, if exist just delete it
            if(exist){
                parentRef.child("parentEmail").child("provider").child(provider).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if((long)snapshot.getValue() == 0){
                            //parentEmail should userEmail.toString()
                            parentRef.child("parentEmail").child("provider").child(provider).removeValue();
                            parentRef.child("parentEmail").child("child").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot childSnapShot : snapshot.getChildren()){
                                        System.out.println(childSnapShot);
                                        providerRef.child(provider).removeValue();
                                    }
                                    Toast.makeText(getContext(), "Invitation is revoked", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    System.err.println("Error: " + error.getMessage());
                                }
                            });
                        }else{
                            parentRef.child("parentEmail").child("provider").child(provider).removeValue().addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Invitation is revoked", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getContext(), "Something must have gone wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.err.println("Error: " + error.getMessage());
                    }
                });
            }
            else{
                Toast.makeText(getContext(), provider + "can not be revoked as they aren't invited", Toast.LENGTH_SHORT).show();
            }
    }

    private void providerExist(){
        String provider = spinnerProvider.getSelectedItem().toString();
        if(provider.equals("Pick a provider")){
            Toast.makeText(getContext(), "Pick a provider to process", Toast.LENGTH_SHORT).show();
            return;
        }
        //hardcoded parentEmail it should be userEmail.toString()
        parentRef.child("parentEmail").child("provider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                InviteOrRevoke(snapshot.child(provider).exists(), provider);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error: " + error.getMessage());
            }
        });
    }
}