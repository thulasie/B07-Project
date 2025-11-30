package com.example.smartAir.data;

import com.example.smartAir.pefAndRecovery.Pef;
import com.example.smartAir.triaging.TriageLogEntryData;

import java.util.HashMap;

public abstract class DatabaseLogEntryFactory {
    protected abstract DatabaseLogEntryData create();

    public static DatabaseLogEntryFactory make(DatabaseLogType type) {
        switch (type) {
            case TRIAGE:
                return new TriageLogEntryFactory();
            case PEF:
                return new PefFactory();
            default:
                return new DefaultFactory();
        }
    }

    public DatabaseLogEntryData createFromDB (HashMap<String, Object> map) {
        DatabaseLogEntryData newEntry = create();
        newEntry.setEntriesWithDB(map);
        return newEntry;
    }
}

class PefFactory extends DatabaseLogEntryFactory {
    @Override
    protected DatabaseLogEntryData create() {
        return null;
    }

    @Override
    public DatabaseLogEntryData createFromDB (HashMap<String, Object> map) {
        return Pef.createFromDB(map);
    }
}

class TriageLogEntryFactory extends DatabaseLogEntryFactory {
    @Override
    protected DatabaseLogEntryData create() {
        return new TriageLogEntryData();
    }
}

class DefaultFactory extends DatabaseLogEntryFactory {
    @Override
    protected DatabaseLogEntryData create() {
        return new DefaultEntry();
    }
}
