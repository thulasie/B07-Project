package com.example.smartAir.data;

import com.example.smartAir.medicine.ControllerDoseEvent;
import com.example.smartAir.inventory.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.symptom.z_s_l;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface R6RepositoryInterface {
    Task<List<RescueEvent>> getRescueEvents(long fromMillis, long toMillis);

    Task<List<ControllerDoseEvent>> getControllerDoses(long fromMillis, long toMillis);

    Task<List<z_s_l>> getSymptomLogs(long fromMillis, long toMillis);

    Task<List<InventoryStatus>> getInventoryStatuses();

    long now();

    long getStartMillisMonthsAgo(int months);
}
