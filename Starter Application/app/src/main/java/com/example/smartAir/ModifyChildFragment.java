//December 1, 2025
//CSCB07 smartAir project modify child
//Sorry for my messy code to anyone reading this
//Mason Cho
package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModifyChildFragment extends Fragment {

    public interface BooleanCallBack{
        void onCallBack(boolean callBack);
    }

    private EditText optionalNote, pickChild, providerEmail;
    private ToggleButton rescueLog, controllerSummary, symptom, trigger, peakFlow, triangleIncident, summaryChart;
    private Button update, check;
    private FirebaseDatabase db;
    private DatabaseReference parent, provider, child;
    private String Name, Email, userEmail;
    private FirebaseUser user;
    private boolean childNameCheck, providerEmailCheck, childDone, providerDone, inputValid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_modify_child, container, false);

        //elements of the layout
        optionalNote = view.findViewById(R.id.editModifyOptionalNote);
        pickChild = view.findViewById(R.id.editPickChild);
        providerEmail = view.findViewById(R.id.editProviderEmail);

        rescueLog = view.findViewById(R.id.toggleButtonRescueLogs);
        controllerSummary = view.findViewById(R.id.toggleButtonControllerSummary);
        symptom = view.findViewById(R.id.toggleButtonSymptom);
        trigger = view.findViewById(R.id.toggleButtonTrigger);
        peakFlow = view.findViewById(R.id.toggleButtonPeakFlow);
        triangleIncident = view.findViewById(R.id.toggleButtonTriangleIncident);
        summaryChart = view.findViewById(R.id.toggleButtonSummaryChart);

        update = view.findViewById(R.id.buttonUpdateChild);
        check = view.findViewById(R.id.buttonChecking);
        db = FirebaseDatabase.getInstance();
        parent = db.getReference("users/parent");
        provider = db.getReference("users/provider");
        child = db.getReference("users/child");

        //gets the current user's email address
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
        }

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckForInput();
                //resetting values so they can be used again
                childDone = false;
                providerDone = false;
                providerEmailCheck = false;
                childNameCheck = false;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputValid)
                    UpdateChild();
                else{
                    Toast.makeText(getContext(), "Please enter proper child and provider", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void UpdateChild(){
        provider.child(Email).child(Name).child("rescuelog").setValue(rescueLog.isChecked());
        provider.child(Email).child(Name).child("controllersummary").setValue(controllerSummary.isChecked());
        provider.child(Email).child(Name).child("symptom").setValue(symptom.isChecked());
        provider.child(Email).child(Name).child("trigger").setValue(trigger.isChecked());
        provider.child(Email).child(Name).child("peakflow").setValue(peakFlow.isChecked());
        provider.child(Email).child(Name).child("triangleincident").setValue(triangleIncident.isChecked());
        provider.child(Email).child(Name).child("summarychart").setValue(summaryChart.isChecked());
        child.child(Name).child("optionalnote").setValue(optionalNote.getText().toString());
        Toast.makeText(getContext(), "updated successfully!", Toast.LENGTH_SHORT).show();
    }

    private void CheckForInput() {

        Name = pickChild.getText().toString().trim().toLowerCase();
        Email = providerEmail.getText().toString().trim();

        if (Name.isEmpty() || Email.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in both child and provider", Toast.LENGTH_SHORT).show();
            return;
        }
        checkValidity();
    }
    private void evaluateToggleValues(){
        if (providerDone && childDone) {
            if (childNameCheck && providerEmailCheck) {
                assignValue("rescuelog", rescueLog);
                assignValue("controllersummary", controllerSummary);
                assignValue("symptom", symptom);
                assignValue("trigger", trigger);
                assignValue("peakflow", peakFlow);
                assignValue("triangleincident", triangleIncident);
                assignValue("summarychart", summaryChart);
                inputValid = true;
                Toast.makeText(getContext(), "Child and provider are found", Toast.LENGTH_SHORT).show();
            } else {
                rescueLog.setChecked(false);
                controllerSummary.setChecked(false);
                symptom.setChecked(false);
                trigger.setChecked(false);
                peakFlow.setChecked(false);
                triangleIncident.setChecked(false);
                summaryChart.setChecked(false);
                inputValid = false;
                Toast.makeText(getContext(), "Child and provider are not found", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //checking if name and provider email user entered is real
    private void checkExistence(String role, BooleanCallBack callBack){
        String identifyer = "";
        if(role.equals("child")){
            identifyer = Name;
        }
        else if(role.equals("provider")){
            identifyer = Email;
        }
        //I'm using parentEmail for now but should be userEmail.toString()
        parent.child("parentEmail").child(role).child(identifyer).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(role + ": " + snapshot.exists());
                callBack.onCallBack(snapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error: " + error.getMessage());
            }
        });
    }
    private void checkValidity(){
        checkExistence("child", new BooleanCallBack() {
            @Override
            public void onCallBack(boolean callBack) {
                childNameCheck = callBack;
                childDone = true;
                evaluateToggleValues();
            }
        });
        checkExistence("provider", new BooleanCallBack() {
            @Override
            public void onCallBack(boolean callBack) {
                providerEmailCheck = callBack;
                providerDone = true;
                evaluateToggleValues();
            }
        });
    }

    //assign the found info from firebase to each toggle button
    private void assignValue(String key, ToggleButton button){
        getValue(key, new BooleanCallBack() {
            @Override
            public void onCallBack(boolean callBack) {
                button.setChecked(callBack);
            }
        });
    }
    //finds if info is shared or not
    private void getValue(String key, BooleanCallBack callBack){
        provider.child(Email).child(Name).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    callBack.onCallBack(snapshot.getValue(boolean.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error: " + error.getMessage());
            }
        });
    }
}
