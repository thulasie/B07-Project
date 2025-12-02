package com.example.smartAir.inventory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import javax.annotation.Nullable;

public class DateDialog extends DialogFragment {

    private DatePickerDialog.OnDateSetListener frag;

    public void setOnDateSetListener (DatePickerDialog.OnDateSetListener frag) {
        this.frag = frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(
                requireActivity(),
                frag,
                year, month, day);
    }

}
