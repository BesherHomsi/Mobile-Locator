package com.example.besher.materialtest.ui.Dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.Interfaces.ItemEventTrigger;
import com.example.besher.materialtest.ui.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsDialog extends DialogFragment {

    //private TextView mStartDate;
    //private TextView mEndDate;
    private TextView mStartTime;
    private TextView mEndTime;
    private TextView mDays;
    private SilenceItem silenceItem;

    public static EventDetailsDialog newInstance(SilenceItem silenceItem) {

        Bundle args = new Bundle();
        args.putSerializable("item",silenceItem);
        EventDetailsDialog fragment = new EventDetailsDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_event_details, null);
        silenceItem = (SilenceItem) getArguments().get("item");

        setUpView(v);
        displayDetails();
        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)getActivity());
        builder.setTitle(silenceItem.getTitle());
        builder.setView(v);
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItemEventTrigger.notifyListenerOnDelete(silenceItem);
            }
        });

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((MainActivity) getContext()).addNewEvent(silenceItem);
            }
        });

        Dialog dialog = builder.create();

        return dialog;
    }
    public void setUpView(View v){

        //mStartDate = (TextView) v.findViewById(R.id.startDateDetailsValue);
        //mEndDate = (TextView) v.findViewById(R.id.endDateDetailsValue);
        mStartTime = (TextView) v.findViewById(R.id.startTimeDetailsValue);
        mEndTime = (TextView) v.findViewById(R.id.endTimeDetailsValue);
        mDays = (TextView) v.findViewById(R.id.daysDetailsList);
    }

    public void displayDetails() {

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        String formatted;

        // Output "2012-09-26"
        startDate.setTimeInMillis(this.silenceItem.getStartDate());
        formatted = format1.format(startDate.getTime());
        //mStartDate.setText(formatted);

        // Output "2012-09-26"
        endDate.setTimeInMillis(this.silenceItem.getEndDate());
        formatted = format1.format(startDate.getTime());
        //mEndDate.setText(formatted);



        mStartTime.setText(setTime(this.silenceItem.getStartHour(),this.silenceItem.getStartMin(),
                this.silenceItem.getStart_AM_PM()));

        mEndTime.setText(setTime(this.silenceItem.getEndHour(),this.silenceItem.getEndMin(),
                this.silenceItem.getEnd_AM_PM()));

        mDays.setText(this.silenceItem.getSelectedDays());

    }

    public String setTime(int hour,int min,String AM_PM) {

        if (hour > 12)
            hour = hour - 12;

        if (hour < 10 && min < 10)
            return "0" + hour + ":" + "0" + min + " " + AM_PM;
        else if (hour < 10)
            return "0" + hour + ":" + min + " " + AM_PM;
        else if (min < 10)
            return hour + ":" + "0" + min+ " " + AM_PM;
        else
            return hour + ":" + min + " " + AM_PM;
    }
}
