package com.example.smartAir.data;

import com.example.smartAir.domain.SymptomEntry;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseRTDBSymptomRepository implements SymptomRepository {
    DatabaseLogger logger;
    private String userID = "";

    public void setUserId (String userID) {
        this.userID = userID;
        DatabaseLogger logger = new DatabaseLogger(userID);
    }

    public void addEntry(SymptomEntry entry) {
        logger.addLog(new DatabaseLogEntry(entry.getDate(), "SYMPTOMENTRY"));
    }

    public List<SymptomEntry> getEntriesForChild(
            String childId,
            String startDate,  // "yyyy-MM-dd"
            String endDate     // "yyyy-MM-dd"
    ) {

    }
}
