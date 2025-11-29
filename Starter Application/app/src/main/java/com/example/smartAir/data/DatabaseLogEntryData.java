package com.example.smartAir.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class DatabaseLogEntryData {
    public abstract String getLogEntry(); // Nothing for now...

    public void setEntriesWithDB(HashMap<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            try {
                Field field = this.getClass().getDeclaredField(entry.getKey());
                if (entry.getValue() instanceof Long) {
                    field.set(this, ((Long) entry.getValue()).intValue());
                } else {
                    field.set(this, entry.getValue());
                }
            } catch (NoSuchFieldException e) {
                // Handle cases where a map key doesn't match an object field
                System.err.println("No such field: " + entry.getKey());
            } catch (IllegalAccessException e) {
                System.err.println("No access to field: " + entry.getKey());
                System.err.println("Try making this field private or provide your own implementation if needed...");
            }
        }
    }

    static DatabaseLogEntryData createEntryData (DatabaseLogType type, HashMap<String, Object> map) {
        Class entryClass = type.getAssociatedClass();
        DatabaseLogEntryData a;

        try {
            a = (DatabaseLogEntryData) type.getAssociatedClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        a.setEntriesWithDB(map);

        return a;
    }
}
