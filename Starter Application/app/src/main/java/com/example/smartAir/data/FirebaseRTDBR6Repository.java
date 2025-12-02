package com.example.smartAir.data;

import com.example.smartAir.medicine.ControllerDoseEvent;
import com.example.smartAir.medicine.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.symptom.z_s_l;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class FirebaseRTDBR6Repository implements R6RepositoryInterface {

    @Override
    public Task<List<RescueEvent>> getRescueEvents(long fromMillis, long toMillis) {
        return null;
    }

    @Override
    public Task<List<ControllerDoseEvent>> getControllerDoses(long fromMillis, long toMillis) {
        return null;
    }

    @Override
    public Task<List<z_s_l>> getSymptomLogs(long fromMillis, long toMillis) {
        return null;
    }

    @Override
    public Task<List<InventoryStatus>> getInventoryStatuses() {
        return null;
    }

    @Override
    public long now() {
        return 0;
    }

    @Override
    public long getStartMillisMonthsAgo(int months) {
        return 0;
    }
}
