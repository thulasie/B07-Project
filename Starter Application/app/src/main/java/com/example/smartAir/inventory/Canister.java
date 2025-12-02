package com.example.smartAir.inventory;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.Date;

public abstract class Canister {
    private static RescueInhaler rescueSingleton;
    private static ControllerInhaler controllerSingleton;

    public long purchaseDate;
    public int puffsUsed;
    public long expiryDate;
    public int amountLeft;
    public int fullAmount;
    public InventoryMarking whoLastMarked = InventoryMarking.NA;

    @Exclude
    public abstract String getName();

    @Exclude
    public String getSummary() {
        return "Expires on " + new Date(purchaseDate) + ", have " + amountLeft +" left. Last updated by " + whoLastMarked;
    }
    public int getPuffsUsed() {
        return puffsUsed;
    };

    public void changeAmountLeft(int fullAmount) {
        this.fullAmount = fullAmount;
    }

    public void use(int uses) {
        this.puffsUsed += uses;
    }

    @Exclude
    public static Canister getRescue() {
        return rescueSingleton;
    }

    @Exclude
    public static Canister getController() {
        return controllerSingleton;
    }

    public static void initializeRescue() {
        rescueSingleton = new RescueInhaler();
    }
    public static void initializeController() {
        controllerSingleton = new ControllerInhaler();
    }

    public static void deleteRescue() {
        rescueSingleton = null;
        InventoryDatabaseAccess.uploadCanisters();
    }
    public static void deleteController() {
        controllerSingleton = null;
        InventoryDatabaseAccess.uploadCanisters();
    }

    public static void initializeRescue(DataSnapshot d) {
        rescueSingleton = d.getValue(RescueInhaler.class);
    }

    public static void initializeController(DataSnapshot d) {
        controllerSingleton = d.getValue(ControllerInhaler.class);
    }
}

class RescueInhaler extends Canister {

    @Override
    public String getName() {
        return "Rescue";
    }

    @Override
    public void use(int uses) { // TODO include side effects here
        super.use(uses);
        InventoryDatabaseAccess.uploadCanisters();
    }
}

class ControllerInhaler extends Canister {

    @Override
    public String getName() {
        return "Controller";
    }

    @Override
    public void use(int uses) {
        super.use(uses);
        InventoryDatabaseAccess.uploadCanisters();
    }
}
