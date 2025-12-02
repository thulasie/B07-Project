package com.example.smartAir.medicine;

public class InventoryStatus {

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
