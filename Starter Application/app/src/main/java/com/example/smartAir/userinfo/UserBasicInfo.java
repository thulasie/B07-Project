package com.example.smartAir.userinfo;

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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UserBasicInfo {

    public interface Callback {
        void run();
    }

    protected static FragmentLoader switcher;
    protected static String username;
    protected static UserRole role;
    protected static ArrayList<String> children = new ArrayList<>();

    public static Fragment getHomeFragment() {
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

    public static void initialize(String username, UserRole role) {
        UserBasicInfo.username = username.replace(".", "").replace("#", "").replace("$", "");
        UserBasicInfo.role = role;
        System.out.println("UserBasicInfo: " + username + ": " + role);
        if (role == UserRole.PARENT) {

            // initialize list of children...
            children.add("Child1");
            children.add("Child2");
        }
    }

    public static void reinitializeChildren(Callback c) {
        c.run();
    }

    public static void logOut() {

    }

}