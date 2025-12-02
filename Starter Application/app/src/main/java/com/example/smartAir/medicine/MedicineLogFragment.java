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
import com.example.smartAir.domain.Rating;

import java.util.Date;

public class MedicineLogFragment extends Fragment {

    interface Callback {
        void run();
    }

    private MedicineLogAdapter adapter;
    private Callback goBack = () -> System.out.println("MedicineLogFragment: Please set goBack before using.");

    public void setGoBack(Callback goBack) {
        this.goBack = goBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.medicine_log_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup typeGroup = view.findViewById(R.id.rg_type);
        RadioGroup ratingGroup = view.findViewById(R.id.rg_rating);
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
            log.dose = dosePicker.getValue();


            log.timestamp = new Date().getTime();

            int ratingCheck = typeGroup.getCheckedRadioButtonId();

            log.rating = getFromRadioGroup(ratingCheck);

            int typeChecked = typeGroup.getCheckedRadioButtonId();
            // Creates a new medicine log
            if (typeChecked == R.id.rb_rescue) {
                log.type = "rescue";
                MedicineDatabaseLogger.logRescueUse(log);
            } else {
                log.type = "controller";
                MedicineDatabaseLogger.logControllerUse(log);
            }

            loadLogs();
        });

        Button exitButton = view.findViewById(R.id.btn_exit_log);
        exitButton.setOnClickListener((v) -> goBack.run());
    }

    private void loadLogs() {
        ArrayList<MedicineLog> buf = new ArrayList<>(); //AppDatabase.getInstance(getContext()).medicineLogDao().getAll();
        MedicineDatabaseLogger.getLogs(buf, () -> adapter.setItems(buf));
    }

    private Rating getFromRadioGroup(int id) {
        if (id == R.id.rb_better) {
            return Rating.BETTER;
        }
        if (id == R.id.rb_worse) {
            return Rating.WORSE;
        }
        return Rating.SAME;
    }
}
