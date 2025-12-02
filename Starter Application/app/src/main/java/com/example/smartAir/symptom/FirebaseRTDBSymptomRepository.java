package com.example.smartAir.symptom;

import com.example.smartAir.databaseLog.DatabaseLogEntry;
import com.example.smartAir.databaseLog.DatabaseLogType;
import com.example.smartAir.databaseLog.DatabaseLogger;
import com.example.smartAir.domain.SymptomEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class FirebaseRTDBSymptomRepository implements SymptomRepository {
    DatabaseLogger logger;
    private String userID = "";
    private static final FirebaseRTDBSymptomRepository singleton = new FirebaseRTDBSymptomRepository();

    private FirebaseRTDBSymptomRepository() {}

    public static FirebaseRTDBSymptomRepository getSingleton() {
        return singleton;
    }

    public static void setUserId (String userID) {
        singleton.userID = userID;
        DatabaseLogger logger = new DatabaseLogger(userID);
        singleton.logger = logger;
    }

    public void addEntry(SymptomEntry entry) {
        logger.addLog(new DatabaseLogEntry(entry.getDate(),
                "SYMPTOM_ENTRY",
                SymptomEntryData.constructSymptomEntryData(entry))
        );
    }

    @Override
    public void getEntriesForChild(
            String childId,
            Date startDate,
            Date endDate,
            ArrayList<DatabaseLogEntry> buf,
            Callback callback
    ) {
        HashSet<DatabaseLogType> wanted = new HashSet<>();
        wanted.add(DatabaseLogType.SYMPTOM_ENTRY);
        logger.getLogs(wanted, buf, callback::run, startDate);
    }
}
