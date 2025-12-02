package com.example.smartAir.motivation;
/*
import androidx.room.Entity;
import androidx.room.PrimaryKey;*/

import com.example.smartAir.databaseLog.DatabaseLogEntryData;

import java.util.Date;

/*@Entity(tableName = "badges")*/
public class Badge extends DatabaseLogEntryData {
    /*@PrimaryKey(autoGenerate = true)*/
    public long id;
    public String title;
    public boolean earned;
    public Date earnedDate;

    @Override
    public String getLogEntry() {
        return "";
    }
}
