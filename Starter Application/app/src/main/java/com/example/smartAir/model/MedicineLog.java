package com.example.smartAir.model;
/*import androidx.room.Entity;
import androidx.room.PrimaryKey;*/

/*@Entity(tableName = "medicine_logs")*/
public class MedicineLog {
    /*@PrimaryKey(autoGenerate = true)*/
    public long id;

    public String type;
    public long timestamp;
    public int dose;
    public boolean techniqueDone;
}
