package com.example.smartAir.userinfo.addchildren;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.R;
import com.example.smartAir.userinfo.FragmentLoader;
import com.example.smartAir.userinfo.UserBasicInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddChildrenFragment extends Fragment {
    FragmentLoader loader;
    private String childName = "";
    private String childDOB = "";
    private String childNotes = "";
    private String childPassword = "";
    interface Callback {
        void onSuccess();
        void noSuccess(String msg);
    }

    public AddChildrenFragment() {
        super(R.layout.add_children_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText etchildName = view.findViewById(R.id.add_children_username);
        EditText etchildDOB = view.findViewById(R.id.add_children_date_of_birth);
        EditText etchildNotes = view.findViewById(R.id.add_children_notes);
        EditText etchildPassword = view.findViewById(R.id.add_children_password);
        Button addChildButton = view.findViewById(R.id.add_child_button);
        Button addChildExitButton = view.findViewById(R.id.add_child_exit_button);

        etchildName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddChildrenFragment.this.childName = s.toString();
            }
        });

        etchildDOB.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddChildrenFragment.this.childDOB = s.toString();
            }
        });

        etchildNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddChildrenFragment.this.childNotes = s.toString();
            }
        });

        etchildPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddChildrenFragment.this.childPassword = s.toString();
            }
        });


        this.loader = new FragmentLoader() {
            final FragmentManager manager = getParentFragmentManager();
            @Override
            public void load(Fragment f) {
                FragmentTransaction t = manager.beginTransaction();
                t.replace(R.id.fragment_container, f);
                t.addToBackStack(null);
                t.commit();
            }
        };

        addChildExitButton.setOnClickListener(v -> {
            UserBasicInfo.reinitializeChildren( () -> {
                loader.load(UserBasicInfo.getHomeFragment());
            });
        });

        addChildButton.setOnClickListener((v) -> {
            addChild(childName, childPassword, childNotes, childDOB, new Callback() {
                @Override
                public void onSuccess() {
                    Toast toast = Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT);
                    toast.show();
                    UserBasicInfo.reinitializeChildren( () -> {
                        loader.load(UserBasicInfo.getHomeFragment());
                    });
                }

                @Override
                public void noSuccess(String msg) {
                    Toast toast = Toast.makeText(getContext(), "Failure: " + msg, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        });

    }

    private void addChild (String name, String password, String notes, String dateOfBirth, Callback c) {
        if (name.length() < 4) {
            c.noSuccess("Please choose a longer username");
            return;
        }
        if (name.contains("@")) {
            c.noSuccess("Do not add an @ to the username.");
            return;
        }

        if (dateOfBirth.isEmpty()) {
            c.noSuccess("Please enter a date of birth.");
            return;
        }

        name = name + "@smartaircom";

        String finalName = name;

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(name.strip(), password.strip())
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().getUser() != null) {
                            registerUser(finalName, task.getResult().getUser().getUid(), notes, dateOfBirth, c);
                        }
                    } else {
                        if (task.getException() != null) {
                            c.noSuccess(task.getException().toString());
                        }
                    }
                });
    }

    private static void registerUser(String username, String uid, String notes, String dateOfBirth, Callback c) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("dob", dateOfBirth);
        values.put("name", username);

        FirebaseDatabase.getInstance().getReference("users").child("child")
                .child(username).updateChildren(values);

        // Add parent relation...
        HashMap<String, Object> parentKey = new HashMap<>();
        values.put(username, true);

        FirebaseDatabase.getInstance().getReference("parentChildren")
                .child(UserBasicInfo.getUsername())
                .updateChildren(parentKey);

        HashMap<String, Object> roleInfo = new HashMap<>();
        values.put("email", username);
        values.put("role", "CHILD");

        FirebaseDatabase.getInstance().getReference("roles")
                .child(uid).updateChildren(roleInfo);


        c.onSuccess();
    }
}


