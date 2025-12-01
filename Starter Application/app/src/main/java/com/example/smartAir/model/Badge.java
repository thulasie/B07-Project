package com.example.smartAir.model;
/*
import androidx.room.Entity;
import androidx.room.PrimaryKey;*/

/*@Entity(tableName = "badges")*/
public class Badge {
    /*@PrimaryKey(autoGenerate = true)*/
    public long id;
    public String title;
    public boolean earned;
    public String earnedDate;
}
