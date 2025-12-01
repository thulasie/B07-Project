package com.example.smartAir.data;

import com.example.smartAir.domain.SymptomEntry;
import java.util.List;

public interface SymptomRepository {

    void addEntry(SymptomEntry entry);

    List<SymptomEntry> getEntriesForChild(
            String childId,
            String startDate,  // "yyyy-MM-dd"
            String endDate     // "yyyy-MM-dd"
    );
}
