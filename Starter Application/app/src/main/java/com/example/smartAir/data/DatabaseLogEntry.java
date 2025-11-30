package com.example.smartAir.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class DatabaseLogEntry {
    public DatabaseLogEntryData data;
    public DatabaseLogType type;
    private Date date;

    public DatabaseLogEntry() {} // This is for firebase


    public DatabaseLogEntry(Date date, String type, DatabaseLogEntryData data) {
        this.type = DatabaseLogType.valueOf(type);
        this.data = data;
        this.date = date;
    }

    public String getType() {
        return type.toString();
    }

    public DatabaseLogEntryData getData() {
        return data;
    }

    @Exclude
    public Date accessDate() {
        return date;
    }

    public DatabaseLogEntry includeDate (Date date) {
        this.date = date;
        return this;
    }

    @NonNull @Override
    public String toString() {
        return type.toString() + "<"+ date + ">: " + data.getLogEntry();
    }
}
