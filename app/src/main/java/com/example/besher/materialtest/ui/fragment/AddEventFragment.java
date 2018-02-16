package com.example.besher.materialtest.ui.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.Interfaces.ItemEventTrigger;
import com.example.besher.materialtest.ui.activity.MainActivity;
import com.example.besher.materialtest.ui.adapters.SilenceItemsAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment implements View.OnClickListener{

    private ArrayList<Integer> days = new ArrayList<>();
    private EditText mTitle;
    private TextView mbtn_days;
    private Button mbtn_addEvent;
    private TextView mtv_displayDays;
    private EditText mSetStartDate;
    private EditText mSetEndDate;
    private TimePicker mTimePicker;


    private SilenceItemsAdapter mAdapter;
    private String title;
    private String daysSelected;
    private String[] daysList;
    private boolean[] checkedList;
    ArrayList<Integer> selectedDays = new ArrayList<>();

    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    int hour, min;
    String AM_PM ;

    public static AddEventFragment newInstance() {

        Bundle args = new Bundle();
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
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_event_, container, false);

        setUpView(v);


        mSetStartDate.setOnClickListener(this);
        mSetEndDate.setOnClickListener(this);
        mbtn_days.setOnClickListener(this);
        mbtn_addEvent.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTitle.setText("");
        mSetStartDate.setText("");
        mSetEndDate.setText("");
        mbtn_days.setText("");
    }

    public void setUpView(View view) {
        mTitle = (EditText) view.findViewById(R.id.et_title);
        mbtn_days = (TextView) view.findViewById(R.id.btn_days);
        mbtn_addEvent = (Button) view.findViewById(R.id.btn_AddEvent);
        //mtv_displayDays = (TextView) view.findViewById(R.id.tv_displayDays);
        mSetStartDate = (EditText) view.findViewById(R.id.et_setStartDate);
        mSetEndDate = (EditText) view.findViewById(R.id.et_setEndDate);
        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);
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
                title = mTitle.getText().toString();
                setTime();
                addItem(title);
                break;
            case R.id.et_setStartDate:
                setStartDate();
                break;
            case R.id.et_setEndDate:
                setEndDate();
                break;
            default:
                break;
        }
    }


    public void addItem(String title) {

        ItemEventTrigger.notifyListenerOnNewItem(new SilenceItem(title,daysSelected,startDate.getTimeInMillis(),endDate.getTimeInMillis(),hour,min,AM_PM));
        getActivity().onBackPressed();
    }

    public void setTime(){

        if(Build.VERSION.SDK_INT >= 23) {

            hour = mTimePicker.getHour();
            min = mTimePicker.getMinute();

            if(hour < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
        }
        else{
            hour = mTimePicker.getCurrentHour();
            min = mTimePicker.getCurrentMinute();
        }
        //Toast.makeText((MainActivity) getActivity(), hour+":"+min, Toast.LENGTH_SHORT).show();
    }

    //In order to display the days in a list and choose from them
    public void displayDaysList(){

        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)getActivity());
        builder.setTitle("Days");
        builder.setMultiChoiceItems(R.array.days, checkedList, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                {
                    if(!selectedDays.contains(which)){
                        selectedDays.add(which);
                    }
                }else {
                    selectedDays.remove(which);
                }
                //Toast.makeText((MainActivity) getActivity(), "item was selected", Toast.LENGTH_SHORT).show();
                days.add(which);
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
                String item = "";
                mbtn_days.setText("");
                for (int i = 0; i < selectedDays.size(); i++) {
                    item = item + daysList[selectedDays.get(i)] + " ";
                }
                //Toast.makeText((MainActivity)getActivity(),"OK", Toast.LENGTH_SHORT).show();
                mbtn_days.setText(item);
                daysSelected = item;
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }


    public void setStartDate(){

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mSetStartDate.setText(dayOfMonth+"/"+month+"/"+ year);
                    startDate.set(year,month,dayOfMonth);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),listener,2018,02,12);

        datePickerDialog.show();
    }

    public void setEndDate(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mSetEndDate.setText(dayOfMonth+"/"+month+"/"+ year);
                endDate.set(year,month,dayOfMonth);

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),listener,2018,02,12);

        datePickerDialog.show();
    }
}
