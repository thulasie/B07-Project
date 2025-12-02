package com.example.smartAir.symptom;

import com.example.smartAir.databaseLog.DatabaseLogEntry;
import com.example.smartAir.domain.SymptomEntry;

import java.util.ArrayList;
import java.util.Date;

public interface SymptomRepository {

    void addEntry(SymptomEntry entry);

    interface Callback {
        void run();
    }

    void getEntriesForChild(
            String childId,
            Date startDate,  // "yyyy-MM-dd"
            Date endDate,     // "yyyy-MM-dd"
            ArrayList<DatabaseLogEntry> buf,
            Callback callback
    );
}

