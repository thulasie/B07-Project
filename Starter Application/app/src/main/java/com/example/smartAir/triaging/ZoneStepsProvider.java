package com.example.smartAir.triaging;

import android.view.LayoutInflater;
import android.view.View;

import com.example.smartAir.domain.Zone;

import java.util.zip.Inflater;

public interface ZoneStepsProvider {
    View getZoneAlignedSteps(LayoutInflater i, Zone z);
}
