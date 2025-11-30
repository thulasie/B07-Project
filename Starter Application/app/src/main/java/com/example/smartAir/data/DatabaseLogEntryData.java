package com.example.smartAir.data;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class DatabaseLogEntryData {
    public abstract String getLogEntry();

    // This method is just a generic, override if needed
    public void setEntriesWithDB(HashMap<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            try {
                Field field = this.getClass().getDeclaredField(entry.getKey());
                if (entry.getValue() instanceof Long) {
                    Long l = (Long) entry.getValue();

                    if (field.getType() == Integer.class || field.getType() == int.class) {
                        field.set(this, l.intValue());
                    } else if (field.getType() == Float.class || field.getType() == float.class) {
                        field.set(this, l.floatValue());
                    } else if (field.getType() == Double.class || field.getType() == double.class) {
                        field.set(this, l.doubleValue());
                    } else {
                        field.set(this, l.floatValue());
                    }
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
}

class DefaultEntry extends DatabaseLogEntryData {
    public String getLogEntry() {
        return "This is blank";
    }
}
