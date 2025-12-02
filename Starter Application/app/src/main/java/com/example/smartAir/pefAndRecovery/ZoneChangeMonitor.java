package com.example.smartAir.pefAndRecovery;

import com.example.smartAir.databaseLog.DatabaseLogEntry;
import com.example.smartAir.databaseLog.DatabaseLogger;
import com.example.smartAir.domain.Zone;

import java.util.Date;

class ZoneChangeMonitor {
    private Zone currentZone = Zone.NOT_APPLICABLE;
    private Float highestPEF;
    private Float currentPersonalBest;
    private String userID;
    private static final ZoneChangeMonitor singletonInstance = new ZoneChangeMonitor();
    private DatabaseLogger logger;

    private ZoneChangeMonitor() {} // Prevents constructor from getting called elsewhere

    static ZoneChangeMonitor getSingletonInstance() {
        return singletonInstance;
    }

    static void initialize(String userID) {
        singletonInstance.userID = userID;
        singletonInstance.logger = new DatabaseLogger(userID);

        System.out.println("PEf: " + singletonInstance.highestPEF + "PB: " + singletonInstance.currentPersonalBest);
    }

    void initializeHighestPEF(Float f) {
        this.highestPEF = f;
        this.currentZone = Zone.calculateZone(highestPEF, currentPersonalBest);
    }

    void initializeCurrentPersonalBest(Float f) {
        this.currentPersonalBest = f;
        this.currentZone = Zone.calculateZone(highestPEF, currentPersonalBest);
    }

    void setHighestPEF(Float f) {
        if (f > this.highestPEF) {
            this.highestPEF = f;

            Zone newZone = Zone.calculateZone(highestPEF, currentPersonalBest);
            System.out.println("Calculated zone: " + newZone);

            if (newZone != Zone.NOT_APPLICABLE && newZone != currentZone) {
                notifyOfNewZone(newZone);
            }
        }
    }

    void setCurrentPersonalBest(Float newPersonalBest) {
        this.currentPersonalBest = newPersonalBest;
        Zone newZone = Zone.calculateZone(highestPEF, currentPersonalBest);
        System.out.println("Calculated zone: " + newZone);

        if (newZone != Zone.NOT_APPLICABLE && newZone != currentZone) {
            notifyOfNewZone(newZone);
        }
    }

    private void notifyOfNewZone(Zone newZone) {
        System.out.println("ZoneChangeMonitor: New zone is " + newZone);

        ZoneChangeData data = new ZoneChangeData(this.currentZone, newZone);
        logger.addLog(new DatabaseLogEntry(new Date(), "ZONE_CHANGE", data));
        this.currentZone = newZone;
    }

    public Zone getCurrentZone() {
        System.out.println("Zone change monitor: gave zone " + this.currentZone);
        return this.currentZone;
    }
}
