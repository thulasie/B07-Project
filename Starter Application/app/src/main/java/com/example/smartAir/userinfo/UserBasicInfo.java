package com.example.smartAir.userinfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.LoginFragment;
import com.example.smartAir.LoginModel;
import com.example.smartAir.LoginPresenter;
import com.example.smartAir.R;
import com.example.smartAir.alerting.AlertMonitor;
import com.example.smartAir.dailies.DailyCheckInFacade;
import com.example.smartAir.domain.UserRole;
import com.example.smartAir.inventory.InventoryFacade;
import com.example.smartAir.pefAndRecovery.ZoneEntryFacade;
import com.example.smartAir.triaging.TriageScreenCreator;
import com.example.smartAir.ui.onboarding.OnboardingCreator;
import com.example.smartAir.ui.onboarding.OnboardingPlaceholder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UserBasicInfo {

    public interface Callback {
        void run();
    }

    protected static String username;
    protected static UserRole role;
    protected static boolean onboarded;
    protected static ArrayList<String> children = new ArrayList<>();

    public static Fragment getHomeFragment() {
        if (!onboarded) {
            OnboardingPlaceholder frag = new OnboardingPlaceholder();
            onboarded = true;
            return frag;
        }

        if (role == UserRole.CHILD) {
                return new HomeFragmentChild();
        } else if (role == UserRole.PARENT) {
                return new HomeFragmentParent();
        } else if (role == UserRole.PROVIDER) {
                return new HomeFragmentProvider();
        }
        return new LoginFragment();
    }

    public static String getUsername() {
        return username;
    }

    public static ArrayList<String> getChildren(){
        return children;
    }

    public static void initialize(String username, UserRole role, Callback c) {
        UserBasicInfo.username = username.replace(".", "").replace("#", "").replace("$", "");
        UserBasicInfo.role = role;
        System.out.println("UserBasicInfo: " + username + ": " + role);

        FirebaseDatabase.getInstance().getReference("onboarding")
                .child(UserBasicInfo.username).get()
                .addOnCompleteListener(task -> {
                        if (task.isSuccessful()
                                && task.getResult().getValue() != null
                                && task.getResult().getValue().toString().equals("complete")) {
                            onboarded = true;
                        } else {
                            FirebaseDatabase.getInstance().getReference("onboarding").child(UserBasicInfo.username).setValue("complete");
                            onboarded = false;
                        }
                        System.out.println(onboarded);

                    if (role == UserRole.PARENT) {
                        // initialize list of children...
                        reinitializeChildren(c);
                    } else {
                        c.run();
                    }
                });
    }

    public static void reinitializeChildren(Callback c) {
        children = new ArrayList<>();
        children.add("Child1");
        children.add("Child2");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        db.getReference().child("parentChildren")
                        .child(UserBasicInfo.getUsername()).get().addOnCompleteListener((task) -> {
                            if (task.isSuccessful()) {
                                task.getResult().getChildren();
                                for (DataSnapshot s: task.getResult().getChildren()) {
                                    System.out.println("UserBasicInfo: " + s.getKey());
                                    children.add(s.getKey());
                                }
                                c.run();
                            } else {
                                System.out.println("Noooooooo");
                                c.run();
                            }
                });
    }

    public static void logOut() {
        username = "";
        children.clear();
        FirebaseAuth.getInstance().signOut();
        role = null;
    }

}