package com.example.smartAir.data;

import com.example.smartAir.medicine.ControllerDoseEvent;
import com.example.smartAir.inventory.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.dailies.z_s_l;
import com.google.android.gms.tasks.Task;

import java.util.Date;
import java.util.List;

public interface R6RepositoryInterface {
    interface Callback {
        void run();
    }

    Task<List<RescueEvent>> getRescueEvents(long fromMillis, long toMillis, Callback cb);

    Task<List<ControllerDoseEvent>> getControllerDoses(long fromMillis, long toMillis, Callback cb);

    Task<List<z_s_l>> getSymptomLogs(long fromMillis, Callback cb);

    Task<List<InventoryStatus>> getInventoryStatuses();

    default long now() {
        return new Date().getTime();
    }

    default long getStartMillisMonthsAgo(int months) {
        return (new Date().getTime() - (long) months * getDaysInMillis(30));
    }

    default long getDaysInMillis(int days) {
        return days * 24 * 60 * 60 * 1000;
    }
}
