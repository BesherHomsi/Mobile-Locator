package com.example.besher.materialtest.ui.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.helpers.ErorrHandler;
import com.example.besher.materialtest.models.Days;
import com.example.besher.materialtest.models.MyCLocation;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.Interfaces.ItemEventTrigger;
import com.example.besher.materialtest.ui.activity.MainActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment implements View.OnClickListener {

    private final String ADDED_LOCATION = "Location has been added";
    private EditText mTitle;
    private EditText mbtn_days;
    private EditText mStartTime;
    private EditText mEndTime;
    private EditText mLocation;
    private EditText mEndDate;
    private Button mbtn_addEvent;
    private Button mBtn_deleteEvent;
    private Button mBtn_editEvent;


    private String title = "";
    private String daysSelected;
    private String[] daysList;
    private boolean[] checkedList;
    ArrayList<Integer> selectedDays = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> alarmIDS = new ArrayList<>();


    private TextInputLayout titleWrapper;
    private TextInputLayout startTimeWrapper;
    private TextInputLayout endTimeWrapper;
    private TextInputLayout daysWrapper;
    private TextInputLayout mEndDateWrapper;


    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    private Boolean isDateSet = false;

    int hour, min;

    private Toolbar mToolBar;
    SilenceItem silenceItem;
    private int startHour = 0;
    private int startMin = 0;
    private String start_AM_PM = "";
    private int endHour = 0;
    private int endMin = 0;
    private String end_AM_PM = "";
    String daysString = "";
    private MyCLocation eventLocation;


    public static AddEventFragment newInstance() {

        Bundle args = new Bundle();
        AddEventFragment fragment = new AddEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddEventFragment newInstance(SilenceItem silenceItem) {

        Bundle args = new Bundle();
        args.putSerializable("item", silenceItem);
        AddEventFragment fragment = new AddEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daysList = getResources().getStringArray(R.array.daysShortCut);
        checkedList = new boolean[daysList.length];
        if (getArguments().getSerializable("item") != null) {
            this.silenceItem = (SilenceItem) getArguments().getSerializable("item");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_event_, container, false);


        setUpView(v);

        if (this.silenceItem == null) {
            mToolBar.setTitle("Add Event");
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);
            mbtn_addEvent.setText("Add Event");
            mBtn_deleteEvent.setVisibility(View.GONE);
            mBtn_editEvent.setVisibility(View.GONE);
        } else {
            mToolBar.setTitle("Edit Event");
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);
            setUpEditView();
            mbtn_addEvent.setText("Edit Event");
            mbtn_addEvent.setVisibility(View.GONE);

        }
        mStartTime.setOnClickListener(this);
        mEndTime.setOnClickListener(this);
        mbtn_days.setOnClickListener(this);
        mbtn_addEvent.setOnClickListener(this);
        mLocation.setOnClickListener(this);
        mEndDate.setOnClickListener(this);
        mBtn_editEvent.setOnClickListener(this);
        mBtn_deleteEvent.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setUpView(View view) {
        mToolBar = (Toolbar) view.findViewById(R.id.tool_bar);
        mTitle = (EditText) view.findViewById(R.id.et_title);
        mbtn_days = (EditText) view.findViewById(R.id.btn_days);
        mbtn_addEvent = (Button) view.findViewById(R.id.btn_AddEvent);

        mStartTime = (EditText) view.findViewById(R.id.startTime);
        mEndTime = (EditText) view.findViewById(R.id.endTime);
        mLocation = (EditText) view.findViewById(R.id.location);
        mEndDate = (EditText) view.findViewById(R.id.endDate);

        titleWrapper = (TextInputLayout) view.findViewById(R.id.titleWrapper);
        startTimeWrapper = (TextInputLayout) view.findViewById(R.id.startTimeWrapper);
        endTimeWrapper = (TextInputLayout) view.findViewById(R.id.endTimeWrapper);
        daysWrapper = (TextInputLayout) view.findViewById(R.id.daysWrapper);
        mEndDateWrapper = (TextInputLayout) view.findViewById(R.id.endDateWrapper);

        mBtn_deleteEvent = (Button) view.findViewById(R.id.deleteButton);
        mBtn_editEvent = (Button) view.findViewById(R.id.editButton);
    }

    public void setUpEditView() {

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        String formatted;

        /*
        if (this.silenceItem.getStart_AM_PM().equals("PM"))
            this.startHour = this.silenceItem.getStartHour() + 12;
        else
            this.startHour = this.silenceItem.getStartHour();
        this.startMin = this.silenceItem.getStartMin();

        if (this.silenceItem.getEnd_AM_PM().equals("PM"))
            this.endHour = this.silenceItem.getEndHour() + 12;
        else
            this.endHour = this.silenceItem.getEndHour();
        this.endMin = this.silenceItem.getEndMin();
*/

        this.startHour = this.silenceItem.getStartHour();
        this.startMin = this.silenceItem.getStartMin();

        this.endHour = this.silenceItem.getEndHour();
        this.endMin = this.silenceItem.getEndMin();

        this.start_AM_PM = this.silenceItem.getStart_AM_PM();
        this.end_AM_PM = this.silenceItem.getEnd_AM_PM();

        this.mStartTime.setText(this.startHour + ":" + this.startMin + " " + this.silenceItem.getStart_AM_PM());
        this.mEndTime.setText(this.endHour + ":" + this.endMin + " " + this.silenceItem.getEnd_AM_PM());



        if (silenceItem.getTitle() != null) {
            this.title = silenceItem.getTitle();
            if (!silenceItem.getTitle().isEmpty())
                mTitle.setText(this.title);
        }


        if (silenceItem.getSelectedDays() != null) {
            this.daysSelected = this.silenceItem.getSelectedDays();
            if (!silenceItem.getSelectedDays().isEmpty())
                mbtn_days.setText(this.daysSelected);
        }

        // Output "2012-09-26"
        this.startDate.setTimeInMillis(this.silenceItem.getStartDate());
        formatted = format1.format(this.startDate.getTime());
        //mSetStartDate.setText(formatted);

        // Output "2012-09-26"
        if(this.endDate != null) {
            if(this.silenceItem.getEndDate() != 0) {
                this.endDate.setTimeInMillis(this.silenceItem.getEndDate());
                formatted = format1.format(this.endDate.getTime());
                mEndDate.setText(formatted);
            }

        }
    }

    public void displayDays(View view) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_days:
                displayDaysList();
                break;
            case R.id.btn_AddEvent:
                String t = mTitle.getText().toString();
                if (silenceItem == null) {
                    if(isInputValid())
                        addItem(t);
                    else {
                        break;
                    }
                }
                break;
            case R.id.startTime:
                setStartTime();
                break;
            case R.id.endTime:
                setEndTime();
                break;
            case R.id.location:
                getLocation();
                break;
            case R.id.endDate:
                setEndDate();
                break;
            case R.id.editButton:
                t = mTitle.getText().toString();
                if(isInputValid())
                    editItem(t);
                break;
            case R.id.deleteButton:
                ItemEventTrigger.notifyListenerOnDelete(silenceItem);

                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    public void addItem(String title) {
        String id = UUID.randomUUID().toString();
        String status = "on";
        if(eventLocation != null) {
            if(isDateSet) {
                ItemEventTrigger.notifyListenerOnNewItem(new SilenceItem(id, title, daysString,
                        startDate.getTimeInMillis(), endDate.getTimeInMillis(), startHour, startMin,
                        start_AM_PM, endHour, endMin, end_AM_PM, alarmIDS, status,
                        eventLocation.getLat(), eventLocation.getLng(), "on"));
            } else {
                ItemEventTrigger.notifyListenerOnNewItem(new SilenceItem(id, title, daysString,
                        startDate.getTimeInMillis(), 0, startHour, startMin,
                        start_AM_PM, endHour, endMin, end_AM_PM, alarmIDS, status,
                        eventLocation.getLat(), eventLocation.getLng(), "on"));
            }
        }
        else {
            if(isDateSet) {
                ItemEventTrigger.notifyListenerOnNewItem(new SilenceItem(id, title, daysString,
                        startDate.getTimeInMillis(), endDate.getTimeInMillis(), startHour, startMin,
                        start_AM_PM, endHour, endMin, end_AM_PM, alarmIDS, status,
                        0.0, 0.0, "off"));
            } else {
                ItemEventTrigger.notifyListenerOnNewItem(new SilenceItem(id, title, daysString,
                        startDate.getTimeInMillis(), 0, startHour, startMin,
                        start_AM_PM, endHour, endMin, end_AM_PM, alarmIDS, status,
                        0.0, 0.0, "off"));
            }

        }

        //method()

        getActivity().onBackPressed();
    }

    public void editItem(String t) {

        if (t.isEmpty())
            this.silenceItem.setTitle(this.title);
        else
            this.silenceItem.setTitle(t);

        this.silenceItem.setSelectedDays(this.daysString);
        this.silenceItem.setStartDate(this.startDate.getTimeInMillis());
        this.silenceItem.setEndDate(this.endDate.getTimeInMillis());
        this.silenceItem.setStartHour(this.startHour);
        this.silenceItem.setStartMin(this.startMin);
        this.silenceItem.setStart_AM_PM(this.start_AM_PM);
        this.silenceItem.setEndHour(this.endHour);
        this.silenceItem.setEndMin(this.endMin);
        this.silenceItem.setEnd_AM_PM(this.end_AM_PM);


        ItemEventTrigger.notifyListenerOnUpdate(this.silenceItem);
        getActivity().onBackPressed();
    }

    //In order to display the days in a list and choose from them
    public void displayDaysList() {
        final HashMap<Integer, String> daysMap = Days.getDays();
        mbtn_days.setText(" ");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Days");
        builder.setMultiChoiceItems(R.array.days, checkedList,
                new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    if (!days.contains(daysMap.get(which))) {
                        days.add(daysMap.get(which));
                    }
                } else {
                    days.remove(daysMap.get(which));
                }
                //Toast.makeText((MainActivity) getActivity(), "item was selected", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Toast.makeText((MainActivity) getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                daysString=" ";
                for (int i = 0; i < days.size(); i++) {
                    daysString = daysString + days.get(i) + " ";
                }
                if (!days.isEmpty()) {
                    daysString = daysString.substring(0, daysString.length() - 1);
                }
                //Toast.makeText((MainActivity)getActivity(),"OK", Toast.LENGTH_SHORT).show();
                mbtn_days.setText(daysString);
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    public void setStartDate() {
        Calendar Now = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //mSetStartDate.setText(dayOfMonth + "-" + month + "-" + year);
                startDate.set(year, month, dayOfMonth);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, Now.get(Calendar.YEAR),
                Now.get(Calendar.MONTH), Now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void setEndDate() {
        Calendar Now = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int monthOfYear = month+1;
                mEndDate.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                endDate.set(year, month, dayOfMonth);
                isDateSet = true;
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener,
                Now.get(Calendar.YEAR), Now.get(Calendar.MONTH), Now.get(Calendar.DAY_OF_MONTH));


        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        mEndDate.setText(" ");
                        isDateSet = false;
                        break;
                }
            }
        };

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Clear", clickListener);
        datePickerDialog.show();
    }

    public void setStartTime() {
        Calendar Now = Calendar.getInstance();
        android.app.TimePickerDialog.OnTimeSetListener listener = new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                startHour = hourOfDay;
                startMin = minute;
                if (hourOfDay > 12) {
                    hour = hourOfDay - 12;
                    start_AM_PM = "PM";
                } else if(hourOfDay == 12) {
                    hour = 12;
                    start_AM_PM = "PM";
                } else {
                    hour = hourOfDay;
                    start_AM_PM = "AM";
                }

                min = minute;
                if (hour < 10 && startMin < 10)
                    mStartTime.setText("0" + hour + ":" + "0" + min + " " + start_AM_PM);
                else if (hour < 10)
                    mStartTime.setText("0" + hour + ":" + min + " " + start_AM_PM);
                else if (min < 10)
                    mStartTime.setText(hour + ":" + "0" + min + " " + start_AM_PM);
                else
                    mStartTime.setText(hour + ":" + min + " " + start_AM_PM);
            }
        };

        android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(
                getContext(), listener, Now.get(Calendar.HOUR_OF_DAY),
                Now.get(Calendar.MINUTE), false);

        timePickerDialog.show();

    }

    public void setEndTime() {
        Calendar Now = Calendar.getInstance();
        android.app.TimePickerDialog.OnTimeSetListener listener = new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endHour = hourOfDay;
                endMin = minute;
                if (hourOfDay > 12) {
                    hour = hourOfDay - 12;
                    end_AM_PM = "PM";
                } else if (hourOfDay == 12) {
                    hour = 12;
                    end_AM_PM = "PM";
                } else {
                    hour = hourOfDay;
                    end_AM_PM = "AM";
                }

                min = minute;
                if (hour < 10 && min < 10)
                    mEndTime.setText("0" + hour + ":" + "0" + min + " " + end_AM_PM);
                else if (hour < 10)
                    mEndTime.setText("0" + hour + ":" + min + " " + end_AM_PM);
                else if (min < 10)
                    mEndTime.setText(hour + ":" + "0" + min + " " + end_AM_PM);
                else
                    mEndTime.setText(hour + ":" + min + " " + end_AM_PM);

            }
        };

        android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(
                getContext(), listener, Now.get(Calendar.HOUR_OF_DAY), Now.get(Calendar.MINUTE),
                false);

        timePickerDialog.show();

    }

    private void getLocation() {
        ((MainActivity) getContext()).addLocationToEvent();
    }

    public boolean isInputValid() {


        if (ErorrHandler.isBlank(mTitle)) {
            titleWrapper.setError("Title is required");
            return false;
        }else{
            titleWrapper.setErrorEnabled(false);
        }

        if (ErorrHandler.isBlank(mStartTime)) {
            startTimeWrapper.setError("Start Time is required");
            return false;
        }else{
            startTimeWrapper.setErrorEnabled(false);
        }

        if (ErorrHandler.isBlank(mEndTime)) {
            endTimeWrapper.setError("End Time is required");
            return false;
        }else{
            endTimeWrapper.setErrorEnabled(false);
        }

        if (ErorrHandler.isBlank(mbtn_days)) {
            daysWrapper.setError("Day is required");
            return false;
        }else{
            daysWrapper.setErrorEnabled(false);
        }

        if (ErorrHandler.startHourGreater(this.startHour, this.endHour, this.start_AM_PM, this.end_AM_PM)) {
            startTimeWrapper.setError("Start time is less than end tine");
            return false;
        }else {
            startTimeWrapper.setErrorEnabled(false);
        }

        if (ErorrHandler.endDateCheck(this.endDate.getTimeInMillis()) && isDateSet) {
            mEndDateWrapper.setError("End date is Today!");
            return false;
        }else{
            mEndDateWrapper.setErrorEnabled(false);
        }

        return true;
    }

    public void setEventLocation(MyCLocation eventLocation) {

        this.eventLocation = eventLocation;
        if(eventLocation != null) {
            mLocation.setText(this.ADDED_LOCATION);
        }


    }
}
