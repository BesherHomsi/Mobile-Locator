package com.example.besher.materialtest.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {

    private Toolbar mToolBar;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mStartTime;
    private TextView mEndTime;
    private TextView mDays;

    SilenceItem silenceItem;

    public static EventDetailsFragment newInstance(SilenceItem silenceItem) {

        Bundle args = new Bundle();
        args.putSerializable("item", silenceItem);
        EventDetailsFragment fragment = new EventDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((Serializable) getArguments().getSerializable("item") != null) {
            this.silenceItem = (SilenceItem) getArguments().getSerializable("item");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_details, container, false);


        setUpView(v);
        displayDetails(silenceItem);
        mToolBar.setTitle(this.silenceItem.getTitle());
        mToolBar.setTitleMarginStart(5);
        mToolBar.setLogo(R.drawable.ic_action_info_outline);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);


        return v;
    }

    public void setUpView(View v){
        mToolBar = (Toolbar) v.findViewById(R.id.tool_bar_display_details);
        mStartDate = (TextView) v.findViewById(R.id.startDateDetailsValue);
        mEndDate = (TextView) v.findViewById(R.id.endDateDetailsValue);
        mStartTime = (TextView) v.findViewById(R.id.startTimeDetailsValue);
        mEndTime = (TextView) v.findViewById(R.id.endTimeDetailsValue);
        mDays = (TextView) v.findViewById(R.id.daysDetailsList);
    }

    public void displayDetails(SilenceItem silenceItem) {

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        String formatted;

        // Output "2012-09-26"
        startDate.setTimeInMillis(this.silenceItem.getStartDate());
        formatted = format1.format(startDate.getTime());
        mStartDate.setText(formatted);

        // Output "2012-09-26"
        endDate.setTimeInMillis(this.silenceItem.getEndDate());
        formatted = format1.format(startDate.getTime());
        mEndDate.setText(formatted);

        mStartTime.setText(setTime(this.silenceItem.getStartHour(),this.silenceItem.getStartMin(),
                this.silenceItem.getStart_AM_PM()));

        mEndTime.setText(setTime(this.silenceItem.getEndHour(),this.silenceItem.getEndMin(),
                this.silenceItem.getEnd_AM_PM()));

        mDays.setText(this.silenceItem.getSelectedDays());

    }

    public String setTime(int hour,int min,String AM_PM) {

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
