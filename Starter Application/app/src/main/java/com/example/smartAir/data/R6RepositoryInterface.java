package com.example.smartAir.data;

import com.example.smartAir.medicine.ControllerDoseEvent;
import com.example.smartAir.medicine.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.symptom.SymptomLog;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface R6RepositoryInterface {
    Task<List<RescueEvent>> getRescueEvents(long fromMillis, long toMillis);

    Task<List<ControllerDoseEvent>> getControllerDoses(long fromMillis, long toMillis);

    Task<List<SymptomLog>> getSymptomLogs(long fromMillis, long toMillis);

    Task<List<InventoryStatus>> getInventoryStatuses();

    long now();

    long getStartMillisMonthsAgo(int months);
}
