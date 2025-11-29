package com.example.smartAir.triaging;

import androidx.annotation.Nullable;

import com.example.smartAir.data.DatabaseLogEntryData;
import com.example.smartAir.data.DatabaseLoggable;
import com.example.smartAir.domain.SevereSymptoms;
import com.example.smartAir.domain.Zone;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TriageModel implements DatabaseLoggable {

    @Override
    public DatabaseLogEntryData formatAsLogEntry() {
        return this.getTriageLogEntry();
    }

    public interface BreathInformationProvider {
        double getPB();
        double getPEF();
    }

    private static BreathInformationProvider provider;

    public static void setProvider(BreathInformationProvider provider) {
        TriageModel.provider = provider;
    }

    public interface TriageLogger {
        void startTriage(TriageModel self, String identifier);

        void updateTriage(TriageModel self, String identifier);
    }

    private static TriageLogger logger = new TriageLogger() {
        @Override
        public void startTriage(TriageModel self, String identifier) {
            System.out.println("rump TriageLogger: started triage to " + identifier);
        }

        @Override
        public void updateTriage(TriageModel self, String identifier) {
            System.out.println("rump TriageLogger: updated " + identifier);
        }
    };

    public enum TriageDecision {
        UNDECIDED, HOME_STEPS, EMERGENCY_CALLED, RESOLVED
    }

    private Set<SevereSymptoms> severeSymptomsSet;
    private int rescueCount = 0;
    @Nullable
    private Float currentPEF; // because it is optional we use the class instead
    private Date started;
    private TriageDecision decision = TriageDecision.UNDECIDED;


    public TriageModel() {
        severeSymptomsSet = new HashSet<>();
    }

    public void startTriageSession() {
        started = new Date();
        logger.startTriage(this, String.valueOf(this.getLogIdentifier()));
    }

    // Symptoms

    public boolean hasSevereSymptoms() {
        return !severeSymptomsSet.isEmpty();
    }

    // Rescue Count, Peak flow, & Zone

    public void setPEF(@Nullable Float f) {
        currentPEF = f;
    }

    public Zone getZone() {
        if (currentPEF == null) {
            // Get the PEF some other way...
            return Zone.calculateZone(provider.getPEF(), provider.getPB());
        } else {
            return Zone.calculateZone(this.currentPEF, provider.getPB());
        }
    }

    public void setRescueCount(Integer c) {
        rescueCount = c;
    }

    // Decision

    public void setDecision (TriageDecision d) {
        this.decision = d;
    }

    public void toggleSymptom(boolean b, SevereSymptoms s) {
        if (b) {
            severeSymptomsSet.add(s);
        } else {
            severeSymptomsSet.remove(s);
        }
    }

    // Logging

    public void updateLogEntry() {
        logger.updateTriage(this, getLogIdentifier());
    }

    // Triage log entry

    public TriageLogEntryData getTriageLogEntry() {
        return new TriageLogEntryData(this.currentPEF, this.rescueCount, this.getDecision().toString().toLowerCase(), "n/a");
    }

    // Helper

    public String getLogIdentifier() {
        return String.valueOf(this.started.getTime());
    }

    public TriageDecision getDecision() {
        return decision;
    }

    public Date getStartDate() {
        return started;
    }


}

