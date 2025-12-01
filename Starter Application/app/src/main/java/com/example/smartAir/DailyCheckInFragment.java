package com.example.smartAir.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;
import com.example.smartAir.data.InMemorySymptomRepository;
import com.example.smartAir.domain.EntryAuthor;
import com.example.smartAir.domain.SymptomEntry;
import com.example.smartAir.domain.TriggerType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DailyCheckInFragment extends Fragment {

    private RadioGroup rgNight, rgActivity, rgCough;
    private CheckBox cbExercise, cbColdAir, cbDustPets, cbSmoke, cbIllness, cbOdors;
    private EditText etNotes;
    private Button btnSave;

    private EntryAuthor author = EntryAuthor.CHILD; // 默认 CHILD
    private String childId = "unknown";

    public void setUserID (String childId) {
        this.childId = childId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // read author（"CHILD" or "PARENT"）
        Bundle args = getArguments();
        if (args != null) {
            String authorStr = args.getString("author");
            if (authorStr != null) {
                try {
                    author = EntryAuthor.valueOf(authorStr);
                } catch (IllegalArgumentException e) {
                    author = EntryAuthor.CHILD;
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_daily_check_in, container, false);

        rgNight = v.findViewById(R.id.rgNightWaking);
        rgActivity = v.findViewById(R.id.rgActivity);
        rgCough = v.findViewById(R.id.rgCough);

        cbExercise = v.findViewById(R.id.cbExercise);
        cbColdAir = v.findViewById(R.id.cbColdAir);
        cbDustPets = v.findViewById(R.id.cbDustPets);
        cbSmoke = v.findViewById(R.id.cbSmoke);
        cbIllness = v.findViewById(R.id.cbIllness);
        cbOdors = v.findViewById(R.id.cbOdors);

        etNotes = v.findViewById(R.id.etNotes);
        btnSave = v.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(view -> {
            saveEntry();
            requireActivity().onBackPressed();
        });

        return v;
    }

    private void saveEntry() {
        int night = mapNight(rgNight.getCheckedRadioButtonId());
        int activity = mapActivity(rgActivity.getCheckedRadioButtonId());
        int cough = mapCough(rgCough.getCheckedRadioButtonId());

        List<TriggerType> triggers = new ArrayList<>();
        if (cbExercise.isChecked()) triggers.add(TriggerType.EXERCISE);
        if (cbColdAir.isChecked()) triggers.add(TriggerType.COLD_AIR);
        if (cbDustPets.isChecked()) triggers.add(TriggerType.DUST_PETS);
        if (cbSmoke.isChecked()) triggers.add(TriggerType.SMOKE);
        if (cbIllness.isChecked()) triggers.add(TriggerType.ILLNESS);
        if (cbOdors.isChecked()) triggers.add(TriggerType.STRONG_ODORS);

        String notes = etNotes.getText().toString();

        String id = UUID.randomUUID().toString();
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US)
                .format(new Date());

        SymptomEntry entry = new SymptomEntry(
                id,
                childId,
                today,
                night,
                activity,
                cough,
                triggers,
                author,    // Child-entered / Parent-entered
                notes
        );

        InMemorySymptomRepository.getInstance().addEntry(entry);
    }

    private int mapNight(int id) {
        if (id == R.id.rbNightNone) return 0;
        if (id == R.id.rbNightOnce) return 1;
        if (id == R.id.rbNightMany) return 2;
        return 0;
    }

    private int mapActivity(int id) {
        if (id == R.id.rbActNone) return 0;
        if (id == R.id.rbActSome) return 1;
        if (id == R.id.rbActSevere) return 2;
        return 0;
    }

    private int mapCough(int id) {
        if (id == R.id.rbCoughNone) return 0;
        if (id == R.id.rbCoughSome) return 1;
        if (id == R.id.rbCoughSevere) return 2;
        return 0;
    }
}
