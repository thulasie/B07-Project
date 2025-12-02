package com.example.smartAir.medicine;

import com.example.smartAir.databaseLog.DatabaseLogEntry;
import com.example.smartAir.databaseLog.DatabaseLogType;
import com.example.smartAir.databaseLog.DatabaseLogger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;

public class MedicineDatabaseLogger {

    private String username;
    private DatabaseLogger logger;
    private static final MedicineDatabaseLogger singleton = new MedicineDatabaseLogger();

    static void setUser(String s) {
        singleton.username = s;
        singleton.logger = new DatabaseLogger(s);
    }

    static void logControllerUse(MedicineLog ml) {
        singleton.logger.addLog(new DatabaseLogEntry(new Date(), "CONTROLLER_USE", ml));
    }

    static void logRescueUse(MedicineLog ml) {
        singleton.logger.addLog(new DatabaseLogEntry(new Date(), "RESCUE_USE", ml));

        // TODO Execute the side effect of >3 consecutive rescue uses
    }

    static void getLogs(ArrayList<MedicineLog> logs, LogMedicineFragment.Callback callback) {
        HashSet<DatabaseLogType> set = new HashSet<>();
        set.add(DatabaseLogType.CONTROLLER_USE);
        set.add(DatabaseLogType.RESCUE_USE);
        ArrayList<DatabaseLogEntry> buf = new ArrayList<>();
        singleton.logger.getLogs(set, buf, ()-> {
            for (DatabaseLogEntry i: buf) {
                logs.add((MedicineLog) i.getData());
            }
            logs.sort(Comparator.comparing(MedicineLog::getTimestamp));
            callback.run();
        });
    }
}
