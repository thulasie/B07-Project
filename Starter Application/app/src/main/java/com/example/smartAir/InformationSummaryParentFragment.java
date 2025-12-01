package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

public class InformationSummaryParentFragment extends Fragment {
    private TextView rescueLogTitle, rescueLogContent, controllerSummaryTitle, controllerSummaryContent,
            symptomTitle, symptomContent, triggerTitle, triggerContent, peakFlowTitle, peakFlowContent,
            triageIncidentTitle, triageIncidentContent;
    private Spinner childSpinner, providerSpinner;
    private FirebaseDatabase db;
    private DatabaseReference provider, parent, info;
    private FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_information, container, false);
            rescueLogTitle = view.findViewById(R.id.textViewRescueLogsTitle);
            rescueLogContent = view.findViewById(R.id.textViewRescueLogsContent);
            controllerSummaryTitle = view.findViewById(R.id.textViewControllersummaryTitle);
            controllerSummaryContent = view.findViewById(R.id.textViewControllersummaryContent);
            symptomTitle = view.findViewById(R.id.textViewSymptomsTitle);
            symptomContent = view.findViewById(R.id.textViewSymptomsContent);
            triggerTitle = view.findViewById(R.id.textViewTriggerTitle);
            triggerContent = view.findViewById(R.id.textViewTriggerContent);
            peakFlowTitle = view.findViewById(R.id.textViewPeakflowTitle);
            peakFlowContent = view.findViewById(R.id.textViewPeakflowContent);
            triageIncidentTitle = view.findViewById(R.id.textViewTriageIncidentTitle);
            triageIncidentContent = view.findViewById(R.id.textViewTriageIncidentContent);
            childSpinner = view.findViewById(R.id.spinnerChild);
            providerSpinner = view.findViewById(R.id.spinnerProvider);


            db = FirebaseDatabase.getInstance();
            parent = db.getReference("users/parent");
            info = db.getReference("Info");
            provider = db.getReference("users/provider");

            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userEmail = user.getEmail();
            }
            setSpinner("child", childSpinner);
            setSpinner("provider", providerSpinner);

        return view;
    }
    private void setSpinner(String key, Spinner spinner){
        //it is set to parentEmail for testing but should be userEmail.toString()
        parent.child("parentEmail").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> childrenNames = new ArrayList<String>();
                childrenNames.add("Pick a " + key);
                for(DataSnapshot childSnapShot : snapshot.getChildren()){
                    childrenNames.add(childSnapShot.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, childrenNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //not quite sure how to implement this as info section is not properly structured yet
    private void displayValues(){
    }

    //not completed yet
    private void sharedNotShared(){

    }
}

//Then there remains displaying it from provider's POV and Child's POV, but they are similar to parent with
//Less to no spinner so shouldn't take too long if I focus