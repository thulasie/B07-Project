package com.example.smartAir.symptom;

import com.example.smartAir.databaseLog.DatabaseLogEntry;
import com.example.smartAir.domain.SymptomEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemorySymptomRepository implements SymptomRepository {

    private static InMemorySymptomRepository instance;

    public static InMemorySymptomRepository getInstance() {
        if (instance == null) {
            instance = new InMemorySymptomRepository();
        }
        return instance;
    }

    private final List<SymptomEntry> allEntries = new ArrayList<>();

    private InMemorySymptomRepository() {}

    @Override
    public void addEntry(SymptomEntry entry) {
        allEntries.add(entry);
    }

    @Override
    public void getEntriesForChild(
            String childId,
            Date startDate,
            Date endDate,
            ArrayList<DatabaseLogEntry> buf,
            Callback callback
    ) {
        for (SymptomEntry e : allEntries) {
            if (!e.getChildId().equals(childId)) continue;
            Date d = e.getDate();
            if (d.compareTo(startDate) >= 0 && d.compareTo(endDate) <= 0) {
                //buf.add(SymptomEntryData.constructSymptomEntryData(e));
            }
            callback.run();
        }
    }
}
