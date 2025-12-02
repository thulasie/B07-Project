package com.example.smartAir.data;

import com.example.smartAir.domain.SymptomEntry;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class FirebaseRTDBSymptomRepository implements SymptomRepository {
    DatabaseLogger logger;
    private String userID = "";

    public void setUserId (String userID) {
        this.userID = userID;
        DatabaseLogger logger = new DatabaseLogger(userID);
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
