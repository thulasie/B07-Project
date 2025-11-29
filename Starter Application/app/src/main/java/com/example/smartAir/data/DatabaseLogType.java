package com.example.smartAir.data;

import com.example.smartAir.triaging.TriageLogEntryData;

public enum DatabaseLogType {
    TRIAGE {
        Class getAssociatedClass () {
            return TriageLogEntryData.class;
        }
    }, MEDICINE {
        Class getAssociatedClass () {
            return TriageLogEntryData.class;
        }
    }, OTHER {
        Class getAssociatedClass () {
            return TriageLogEntryData.class;
        }
    };

    abstract Class getAssociatedClass ();
}