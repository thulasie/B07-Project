package com.example.smartAir.pefAndRecovery;

import com.example.smartAir.data.DatabaseLogEntryData;
import com.example.smartAir.domain.Zone;

public class ZoneChangeData extends DatabaseLogEntryData {
    public Zone oldZone;
    public Zone newZone;

    public ZoneChangeData() {
        // For the constructor
    }

    public ZoneChangeData(Zone old, Zone _new) {
        oldZone = old;
        newZone = _new;
    }

    @Override
    public String getLogEntry() {
        return "Zone changed from " + oldZone + " to " + newZone + ".";
    }
}
