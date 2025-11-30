package com.example.smartAir.pefAndRecovery;

import com.example.smartAir.data.DatabaseLogEntry;
import com.example.smartAir.data.DatabaseLogType;
import com.example.smartAir.data.DatabaseLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class PefLog implements PefController.PefLogger {
    private final HashMap<Date, Pef> log = new HashMap<>();
    private final static PefLog singletonInstance = new PefLog();
    private DatabaseLogger logger;
    private String userID;

    private PefLog() {} // Forces singleton to be accessed

    public static void initializeTodaysPefLog(String userID, CompletionNotifier notifier) {
        singletonInstance.logger = new DatabaseLogger(userID);
        singletonInstance.userID = userID;

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

            ZoneChangeMonitor.getSingletonInstance()
                    .initializeHighestPEF(singletonInstance.getHighestPEF());

            notifier.notifyMonitor();
        }, midnightToday);
    }

    public static PefLog getSingletonInstance() {
        return singletonInstance;
    }

    void setLogger(DatabaseLogger logger) {
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

        ZoneChangeMonitor.getSingletonInstance().setHighestPEF(pEF);
    }

    @Override
    public void logPEF(Float preMedPEF, Float postMedPEF) {
        Pef entryData = Pef.create(preMedPEF, postMedPEF);
        Date now = new Date();

        log.put(now, entryData);
        if (logger != null) {
            logger.addLog(new DatabaseLogEntry(now, "PEF", entryData));
        }

        ZoneChangeMonitor.getSingletonInstance().setHighestPEF(preMedPEF);
        ZoneChangeMonitor.getSingletonInstance().setHighestPEF(postMedPEF);
    }

    float getHighestPEF() {
        float highest = -1f;

        for (Pef p : log.values()) {
            if (highest < p.getHighestPEF()) {
                highest = p.getHighestPEF();
            }
        }

        return highest;
    }
}
