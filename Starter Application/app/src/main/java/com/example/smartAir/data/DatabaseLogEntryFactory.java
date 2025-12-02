package com.example.smartAir.data;

import com.example.smartAir.medicine.MedicineLog;
import com.example.smartAir.pefAndRecovery.Pef;
import com.example.smartAir.pefAndRecovery.ZoneChangeData;
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
            case ZONE_CHANGE:
                return new ZoneChangeFactory();
            case SYMPTOM_ENTRY:
                return new SymptomEntryDataFactory();
            case MEDICINE_LOG:
                return new MedicineLogFactory();
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

class ZoneChangeFactory extends DatabaseLogEntryFactory {
    @Override
    protected DatabaseLogEntryData create() {return new ZoneChangeData();}
}

class MedicineLogFactory extends DatabaseLogEntryFactory {
    @Override
    protected DatabaseLogEntryData create() {return new MedicineLog();}
}

class SymptomEntryDataFactory extends DatabaseLogEntryFactory {
    @Override
    protected DatabaseLogEntryData create() {return new SymptomEntryData();}

}