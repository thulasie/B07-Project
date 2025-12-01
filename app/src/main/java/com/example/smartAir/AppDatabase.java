package com.example.smartAir;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.smartAir.dao.BadgeDao;
import com.example.smartAir.dao.InventoryDao;
import com.example.smartAir.dao.MedicineLogDao;
import com.example.smartAir.model.Badge;
import com.example.smartAir.model.InventoryItem;
import com.example.smartAir.model.MedicineLog;

@Database(entities = {MedicineLog.class, InventoryItem.class, Badge.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicineLogDao medicineLogDao();
    public abstract InventoryDao inventoryDao();
    public abstract BadgeDao badgeDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "r3_db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
