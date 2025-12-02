package com.example.smartAir.triaging;

import android.view.LayoutInflater;
import android.view.View;

import com.example.smartAir.R;
import com.example.smartAir.domain.Zone;
import com.google.android.material.chip.Chip;

import java.util.zip.Inflater;

public interface ZoneStepsProvider {
    View getZoneAlignedSteps(LayoutInflater i, Zone z);
}

class DefaultZoneSteps implements ZoneStepsProvider{
    @Override
    public View getZoneAlignedSteps(LayoutInflater i, Zone z) {
        Chip view = (Chip) i.inflate(R.layout.triage_symptom_chip, null);
        view.setText(z.name());

        return view;
    }
}
