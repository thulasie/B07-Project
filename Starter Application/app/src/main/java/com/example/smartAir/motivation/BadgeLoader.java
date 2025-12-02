package com.example.smartAir.motivation;

import com.example.smartAir.databaseLog.DatabaseLogEntry;
import com.example.smartAir.databaseLog.DatabaseLogType;
import com.example.smartAir.databaseLog.DatabaseLogger;

import java.util.ArrayList;
import java.util.HashSet;

public class BadgeLoader {
    DatabaseLogger logger;
    String userID = null;
    private static final BadgeLoader singleton = new BadgeLoader();

    interface Callback {
        void run();
    }

    private BadgeLoader(){};

    public static void initializeLoader(String userID) {
        singleton.userID = userID;
        singleton.logger = new DatabaseLogger(userID);
    }

    public static void logBadge(Badge b) {
        singleton.logger.addLog(new DatabaseLogEntry(b.earnedDate, "BADGE", b));
    }

    public static void loadBadges(ArrayList<Badge> buf, Callback c) {
        HashSet<DatabaseLogType> b = new HashSet<>();
        b.add(DatabaseLogType.BADGE);

        ArrayList<DatabaseLogEntry> wideBuf = new ArrayList<>();

        singleton.logger.getLogs(b, wideBuf, () -> {
            for (DatabaseLogEntry dat: wideBuf) {
                buf.add((Badge) dat.getData());
            }
            c.run();
        });
    }
}
