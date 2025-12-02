package com.example.smartAir.medicine;
/*import androidx.room.Entity;
import androidx.room.PrimaryKey;*/

import com.example.smartAir.databaseLog.DatabaseLogEntryData;

/*@Entity(tableName = "medicine_logs")*/
public class MedicineLog extends DatabaseLogEntryData {
    /*@PrimaryKey(autoGenerate = true)*/
    public long id;

    public String type;
    public long timestamp;
    public int dose;
    public boolean techniqueDone;

    @Override
    public String getLogEntry() {
        return "";
    }

    public long getTimestamp() {
        return timestamp;
    }
}
