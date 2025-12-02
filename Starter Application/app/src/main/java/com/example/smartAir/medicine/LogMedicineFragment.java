package com.example.smartAir.medicine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.NumberPicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//import com.example.smartAir.AppDatabase;
import com.example.smartAir.R;

import java.util.Date;

public class LogMedicineFragment extends Fragment {

    interface Callback {
        void run();
    }

    private MedicineLogAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_medicine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup typeGroup = view.findViewById(R.id.rg_type);
        NumberPicker dosePicker = view.findViewById(R.id.picker_dose);
        Button btnSave = view.findViewById(R.id.btn_save_log);
        RecyclerView rv = view.findViewById(R.id.rv_logs);

        dosePicker.setMinValue(1);
        dosePicker.setMaxValue(10);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MedicineLogAdapter();
        rv.setAdapter(adapter);

        loadLogs();

        btnSave.setOnClickListener(v -> { // TODO study this
            MedicineLog log = new MedicineLog();
            log.dose = dosePicker.getValue();
            int checked = typeGroup.getCheckedRadioButtonId();
            log.type = checked == R.id.rb_rescue ? "rescue" : "controller";
            log.timestamp = new Date().getTime();
            // Creates a new medicine log
            if (checked == R.id.rb_rescue) {
                MedicineDatabaseLogger.logRescueUse(log);
            } else {
                MedicineDatabaseLogger.logControllerUse(log);
            }

            loadLogs();
        });
    }

    private void loadLogs() {
        ArrayList<MedicineLog> buf = new ArrayList<>(); //AppDatabase.getInstance(getContext()).medicineLogDao().getAll();
        MedicineDatabaseLogger.getLogs(buf, () -> adapter.setItems(buf));
    }
}
