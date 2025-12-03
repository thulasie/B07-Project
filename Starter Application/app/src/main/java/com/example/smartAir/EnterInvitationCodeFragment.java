package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class EnterInvitationCodeFragment extends Fragment {
    private Button Enter;
    private EditText codeEnter;
    private Spinner parentSpinner;
    private FirebaseDatabase db;
    private ArrayList<String> parentNames;
    private DatabaseReference parentRef, providerRef;
    private FirebaseUser user;
    private String userEmail;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_accept_invitation, container, false);

        Enter = view.findViewById(R.id.buttonEnterCode);
        codeEnter = view.findViewById(R.id.editTextInviteCode);
        parentSpinner = view.findViewById(R.id.spinnerParent);
        db = FirebaseDatabase.getInstance();
        providerRef = db.getReference("users/provider");
        parentRef = db.getReference("users/parent");
        parentNames = new ArrayList<String>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }

        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                parentNames.add("Pick a parent");
                for (DataSnapshot childSnapShot : snapshot.getChildren()) {
                    parentNames.add(childSnapShot.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, parentNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                parentSpinner.setAdapter(adapter);            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error: " + error.getMessage());
            }});


        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date now = new Date();
                VerifyCode(now.getTime());
            }
        });
        return view;
    }
    private void VerifyCode(long now){
        String code = codeEnter.getText().toString().trim();
        //providerEmail should not be hard coded instead should be userEmail.toString()
        parentRef.child(parentSpinner.getSelectedItem().toString()).child("provider").child("providerEmail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.getValue().toString().equals(code)) {
                        if( now - ((Long) snapshot.getValue()).longValue() < 604800000 && !snapshot.getValue().toString().equals(0)){
                            AddChildToProvider();
                            Toast.makeText(getContext(), "Code Accepted!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Wrong code or invitation does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Wrong code or invitation does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error: " + error.getMessage());
            }
        });
    }

    private void AddChildToProvider(){
        parentRef.child(parentSpinner.getSelectedItem().toString()).child("child").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapShot : snapshot.getChildren()) {
                    //providerEmail should not be hard coded instead should be userEmail.toString()
                    String childName = childSnapShot.getKey().toString();
                    providerRef.child("providerEmail").child(childName).child("controllersummary").setValue(false);
                    providerRef.child("providerEmail").child(childName).child("peakflow").setValue(false);
                    providerRef.child("providerEmail").child(childName).child("rescuelog").setValue(false);
                    providerRef.child("providerEmail").child(childName).child("summarychart").setValue(false);
                    providerRef.child("providerEmail").child(childName).child("symptom").setValue(false);
                    providerRef.child("providerEmail").child(childName).child("controllersummary").setValue(false);
                    providerRef.child("providerEmail").child(childName).child("triangleincident").setValue(false);
                    providerRef.child("providerEmail").child(childName).child("trigger").setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error: " + error.getMessage());
            }
        });
        //change the value of provider under the parent to 0
        parentRef.child(parentSpinner.getSelectedItem().toString()).child("provider").child("providerEmail").setValue(0);
    }
}

/*
TODO
spinner that has every parent
pick a parent then compare the provider email that is logged in and the invitation code they entered
If they match add all the child under that parent to the provider and change the value to 0
if they don't match just have a toast message showing wrong code or no invitation
 */