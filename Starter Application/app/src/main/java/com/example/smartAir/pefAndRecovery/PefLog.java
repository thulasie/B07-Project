package com.example.smartAir.pefAndRecovery;

import com.example.smartAir.data.DatabaseLogEntry;
import com.example.smartAir.data.DatabaseLogType;
import com.example.smartAir.data.DatabaseLogger;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class PefLog implements PefController.ZoneChangeLogger {
    private final HashMap<Date, Pef> log = new HashMap<>();
    private final static PefLog singletonInstance = new PefLog();
    private DatabaseLogger logger;

    public static void initializePefLog(String userID) {
        singletonInstance.logger = new DatabaseLogger(userID);
        HashSet<DatabaseLogType> typesWanted =  new HashSet<>();
        typesWanted.add(DatabaseLogType.PEF);
        Long now = new Date().getTime();
        Date midnightToday = new Date(now - now % (24 * 60 * 60 * 1000));
        ArrayList<DatabaseLogEntry> buf = new ArrayList<>();

        singletonInstance.logger.getLogs(typesWanted, buf, () -> {
            for (DatabaseLogEntry entry: buf) {
                singletonInstance.log.put(entry.accessDate(), (Pef) entry.getData());
                System.out.println(entry.getData().getLogEntry());
            }
        }, midnightToday);
    }

    public static PefLog getSingletonInstance() {
        return singletonInstance;
    }

    public void setLogger(DatabaseLogger logger) {
        this.logger = logger;
    }

    @Override
    public void logPEF(Float pEF) {
        Pef entryData = Pef.create(pEF);
        Date now = new Date();

        log.put(now, entryData);
        if (logger != null) {
            logger.addLog(new DatabaseLogEntry(now, "PEF", entryData));
        }
    }

    @Override
    public void logPEF(Float preMedPEF, Float postMedPEF) {
        Pef entryData = Pef.create(preMedPEF, postMedPEF);
        Date now = new Date();

        log.put(now, entryData);
        if (logger != null) {
            logger.addLog(new DatabaseLogEntry(now, "PEF", entryData));
        }
    }

    public float getHighestPEF() {
        float highest = -1f;

        for (Pef p : log.values()) {
            if (highest < p.getHighestPEF()) {
                highest = p.getHighestPEF();
            }
        }

        return highest;
    }
}
