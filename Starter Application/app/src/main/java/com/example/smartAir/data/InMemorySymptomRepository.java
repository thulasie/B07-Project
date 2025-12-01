package com.example.smartAir.data;

import com.example.smartAir.domain.SymptomEntry;

import java.util.ArrayList;
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
    public List<SymptomEntry> getEntriesForChild(
            String childId,
            String startDate,
            String endDate
    ) {
        List<SymptomEntry> result = new ArrayList<>();
        for (SymptomEntry e : allEntries) {
            if (!e.getChildId().equals(childId)) continue;
            String d = e.getDate();
            if (d.compareTo(startDate) >= 0 && d.compareTo(endDate) <= 0) {
                result.add(e);
            }
        }
        return result;
    }
}
