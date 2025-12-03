package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class AddChildFragment extends Fragment {
    private EditText editName, editPassword, editDateOfBirth, editOptionalNote;
    private Button addChild;
    private FirebaseDatabase db;
    private DatabaseReference child, parent, provider;
    private FirebaseUser user;
    private String userEmail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_add_child, container, false);

        editName = view.findViewById(R.id.editName);
        editPassword = view.findViewById(R.id.editPassword);
        editDateOfBirth = view.findViewById(R.id.editDateOfBirth);
        editOptionalNote = view.findViewById(R.id.editOptionalNote);
        addChild = view.findViewById(R.id.buttonAddChild);

        db = FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }

        addChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        return view;
    }

    private void addItem() {
        //gets the string info from user's input
        String name = editName.getText().toString().trim().toLowerCase();
        String password = editPassword.getText().toString().trim();
        String dateOfBirth = editDateOfBirth.getText().toString().trim();
        String optionalNote = editOptionalNote.getText().toString().trim();

        //checks if any section is missing
        if (name.isEmpty() || password.isEmpty() || dateOfBirth.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //identifies the path
        child = db.getReference("users/child");
        parent = db.getReference("users/parent");
        provider = db.getReference("users/provider");
        Item item = new Item(name, password, dateOfBirth, optionalNote);

        //parentEmail should be userEmail.toString() but hardcoded it for testing purposes
        parent.child("parentEmail").child("child").child(name).setValue("");
        addToProvider();
        child.child(name).setValue(item).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to add item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToProvider(){
        //parentEmail should be userEmail.toString() but hardcoded it for testing purposes
        parent.child("parentEmail").child("provider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = editName.getText().toString().trim().toLowerCase();
                if(snapshot.exists()){
                    for(DataSnapshot childsnapshot : snapshot.getChildren()){
                        String key = childsnapshot.getKey();
                        provider.child(key).child(name).child("controllersummary").setValue(false);
                        provider.child(key).child(name).child("peakflow").setValue(false);
                        provider.child(key).child(name).child("rescuelog").setValue(false);
                        provider.child(key).child(name).child("summarychart").setValue(false);
                        provider.child(key).child(name).child("symptom").setValue(false);
                        provider.child(key).child(name).child("triangleincident").setValue(false);
                        provider.child(key).child(name).child("trigger").setValue(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error: " + error.getMessage());
            }
        });
    }
}