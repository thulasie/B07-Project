package com.example.smartAir.inventory;

public class InventoryStatus { // TODO change the dpeendencies here...

    private String medicationName;
    private int remainingDoses;
    private long expiryDateMillis;

    public InventoryStatus() { }

    public InventoryStatus(String medicationName, int remainingDoses, long expiryDateMillis) {
        this.medicationName = medicationName;
        this.remainingDoses = remainingDoses;
        this.expiryDateMillis = expiryDateMillis;
    }

    public String getMedicationName() { return medicationName; }
    public int getRemainingDoses() { return remainingDoses; }
    public long getExpiryDateMillis() { return expiryDateMillis; }
}
