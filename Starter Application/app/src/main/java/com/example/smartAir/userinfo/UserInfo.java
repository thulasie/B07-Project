package com.example.smartAir.userinfo;

import androidx.fragment.app.Fragment;

import com.example.smartAir.alerting.AlertMonitor;
import com.example.smartAir.dailies.DailyCheckInFacade;
import com.example.smartAir.domain.UserRole;
import com.example.smartAir.inventory.InventoryFacade;
import com.example.smartAir.pefAndRecovery.ZoneEntryFacade;
import com.example.smartAir.triaging.TriageScreenCreator;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public abstract class UserInfo {
    interface Callback {
        void run();
    }
    interface FragmentSwitcher {
        void navigate(Fragment fragment);
    }

    protected static UserInfo singleton;
    protected static FragmentSwitcher switcher;
    protected String username;
    public abstract UserRole getRole();
    protected static UserDashboardFactory factory;
    protected Callback cb;
    int count = 0;
    abstract protected int getTotal();
    protected static AlertMonitor.Alerter alerter;

    public static void instantiate(String username, Callback cb, AlertMonitor.Alerter a) {
        // Initialize the singleton to something...
        alerter = a;

        FirebaseDatabase.getInstance().getReference("");

        singleton.username = username;
        singleton.initializeAllInfo();
        singleton.cb = cb;
        // Query profile from database now...


        factory = singleton.createDashboardFactory();
    }

    protected abstract void initializeAllInfo();

    protected abstract UserDashboardFactory createDashboardFactory();
    public static UserDashboardFactory getFactory (){
        return factory;
    }

    public void increment() {
        count++;
        if (getTotal() == count) {
            cb.run();
        }
    }
}

class ChildInfo extends UserInfo {
    @Override
    public UserRole getRole() {
        return UserRole.CHILD;
    }

    @Override
    protected int getTotal() {
        return 2;
    }

    @Override
    protected void initializeAllInfo() {
        InventoryFacade.changeUserName(username, super::increment);
        ZoneEntryFacade.changeUser(username, super::increment);
    }

    @Override
    protected UserDashboardFactory createDashboardFactory() {
        return null;
    }
}


class ParentInfo extends UserInfo {
    ArrayList<String> children = new ArrayList<>();

    @Override
    public UserRole getRole() {
        return UserRole.PARENT;
    }

    @Override
    protected int getTotal() {
        return 2;
    }

    @Override
    protected void initializeAllInfo() {
        InventoryFacade.changeUserName(username, super::increment);
    }

    @Override
    protected UserDashboardFactory createDashboardFactory() {
        return null;
    }
}

class ProviderInfo extends UserInfo {
    // Do stuff...
    ArrayList<String> parents = new ArrayList<>();
    ArrayList<String> children = new ArrayList<>();

    @Override
    public UserRole getRole() {
        return UserRole.PROVIDER;
    }

    @Override
    protected int getTotal() {
        return 0;
    }

    @Override
    protected void initializeAllInfo() {

    }

    @Override
    protected UserDashboardFactory createDashboardFactory() {
        return null;
    }
}
