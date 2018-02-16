package com.example.besher.materialtest.ui.Dialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.besher.materialtest.R;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        return new android.app.TimePickerDialog(getActivity(), (android.app.TimePickerDialog.OnTimeSetListener) getActivity(),hour,min, android.text.format.DateFormat.is24HourFormat(getActivity()));

    }
}
