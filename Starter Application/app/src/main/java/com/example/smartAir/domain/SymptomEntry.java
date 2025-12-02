package com.example.smartAir.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SymptomEntry {

    private String id;          // UUID for this entry
    private String childId;     // which child this entry belongs to
    private Date date;        // "yyyy-MM-dd" format

    // 0 = none, 1 = mild, 2 = severe
    private int nightWaking;
    private int activityLimit;
    private int coughWheeze;

    private List<TriggerType> triggers = new ArrayList<>();
    private EntryAuthor author;     // CHILD or PARENT
    private String notes;

    public SymptomEntry() {}

    public SymptomEntry(String id,
                        String childId,
                        Date date,
                        int nightWaking,
                        int activityLimit,
                        int coughWheeze,
                        List<TriggerType> triggers,
                        EntryAuthor author,
                        String notes) {
        this.id = id;
        this.childId = childId;
        this.date = date;
        this.nightWaking = nightWaking;
        this.activityLimit = activityLimit;
        this.coughWheeze = coughWheeze;
        if (triggers != null) {
            this.triggers = triggers;
        }
        this.author = author;
        this.notes = notes;
    }

    // getter / setter，use IDE to generate

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getChildId() { return childId; }
    public void setChildId(String childId) { this.childId = childId; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getNightWaking() { return nightWaking; }
    public void setNightWaking(int nightWaking) { this.nightWaking = nightWaking; }

    public int getActivityLimit() { return activityLimit; }
    public void setActivityLimit(int activityLimit) { this.activityLimit = activityLimit; }

    public int getCoughWheeze() { return coughWheeze; }
    public void setCoughWheeze(int coughWheeze) { this.coughWheeze = coughWheeze; }

    public List<TriggerType> getTriggers() { return triggers; }
    public void setTriggers(List<TriggerType> triggers) { this.triggers = triggers; }

    public EntryAuthor getAuthor() { return author; }
    public void setAuthor(EntryAuthor author) { this.author = author; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
