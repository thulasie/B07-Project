package com.example.smartAir.data;

import com.example.smartAir.medicine.ControllerDoseEvent;
import com.example.smartAir.inventory.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.dailies.z_s_l;
import com.google.android.gms.tasks.Task;

import java.util.List;

public abstract class FirebaseRTDBR6Repository implements R6RepositoryInterface {
    @Override
    public Task<List<InventoryStatus>> getInventoryStatuses() {
        return null;
    }
}
