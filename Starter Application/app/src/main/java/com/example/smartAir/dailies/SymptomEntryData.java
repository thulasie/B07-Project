package com.example.smartAir.dailies;

import com.example.smartAir.databaseLog.DatabaseLogEntryData;
import com.example.smartAir.domain.EntryAuthor;
import com.example.smartAir.domain.SymptomEntry;
import com.example.smartAir.domain.TriggerType;

import java.util.ArrayList;
import java.util.List;

public class SymptomEntryData extends DatabaseLogEntryData {

    public String id;          // UUID for this entry
    public String childId;     // which child this entry belongs to
    public String date;        // "yyyy-MM-dd" format

    // 0 = none, 1 = mild, 2 = severe
    public int nightWaking;
    public int activityLimit;
    public int coughWheeze;

    public List<String> triggers = new ArrayList<>();
    public EntryAuthor author;     // CHILD or PARENT
    public String notes;

    @Override
    public String getLogEntry() {
        return "....."; // TODO change this
    }

    public static SymptomEntryData constructSymptomEntryData(SymptomEntry s) {
        SymptomEntryData dat = new SymptomEntryData();
        dat.id = s.getId();
        dat.childId = s.getId();
        dat.nightWaking = s.getNightWaking();
        dat.activityLimit = s.getActivityLimit();
        dat.coughWheeze = s.getCoughWheeze();
        for (TriggerType trigger: s.getTriggers()) {
            dat.triggers.add(String.valueOf(trigger));
        }
        dat.author = s.getAuthor();
        dat.notes = s.getNotes();

        return dat;
    }
}
