package com.example.smartAir.ui;

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

import com.example.smartAir.AppDatabase;
import com.example.smartAir.R;
import com.example.smartAir.adapter.MedicineLogAdapter;
import com.example.smartAir.firebase.Sync;
import com.example.smartAir.model.MedicineLog;

import java.util.List;

public class LogMedicineFragment extends Fragment {

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

        btnSave.setOnClickListener(v -> {
            MedicineLog log = new MedicineLog();
            int checked = typeGroup.getCheckedRadioButtonId();
            log.type = checked == R.id.rb_rescue ? "rescue" : "controller";
            log.dose = dosePicker.getValue();
            log.timestamp = System.currentTimeMillis();

            long id = AppDatabase.getInstance(getContext()).medicineLogDao().insert(log);
            log.id = id;

            new Sync().syncMedicineLog(log);

            loadLogs();
        });
    }

    private void loadLogs() {
        List<MedicineLog> items = AppDatabase.getInstance(getContext()).medicineLogDao().getAll();
        adapter.setItems(items);
    }
}
