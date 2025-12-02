package com.example.smartAir.medicine;
/*import androidx.room.Entity;
import androidx.room.PrimaryKey;*/

import com.example.smartAir.databaseLog.DatabaseLogEntryData;
import com.example.smartAir.domain.Rating;

/*@Entity(tableName = "medicine_logs")*/
public class MedicineLog extends DatabaseLogEntryData {
    /*@PrimaryKey(autoGenerate = true)*/
    public long id;

    public String type;
    public long timestamp;
    public int dose;
    public boolean techniqueDone;
    public Rating rating = Rating.SAME;

    @Override
    public String getLogEntry() {
        return dose + " dose(s) of " + type + "medicine.\n"
                + "Reported " + rating.name().toLowerCase() + " feeling.";
    }

    public long getTimestamp() {
        return timestamp;
    }
}
