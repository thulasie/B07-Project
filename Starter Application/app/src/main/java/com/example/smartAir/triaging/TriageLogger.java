package com.example.smartAir.triaging;


import com.example.smartAir.data.DatabaseLogEntry;
import com.example.smartAir.data.DatabaseLogger;

public interface TriageLogger {

    void updateTriage(TriageModel self);
}

class DefaultTriageLogger implements TriageLogger {
    @Override
    public void updateTriage(TriageModel model) {
        System.out.println("Fake TriageLogger: " + model.getTriageLogEntryData().getLogEntry());
    }
}

class DatabaseTriageLogger implements TriageLogger {
    private DatabaseLogger logger;

    public void setLogger(DatabaseLogger l) {
        this.logger = l;
    }

    @Override
    public void updateTriage(TriageModel self) {
        System.out.println("Heyyy");
        logger.addLog(new DatabaseLogEntry(self.getStartDate(), "TRIAGE", self.getTriageLogEntryData()));
    }
}