package com.example.besher.materialtest.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.fragment.AddEventFragment;
import com.example.besher.materialtest.ui.fragment.EditEventFragment;
import com.example.besher.materialtest.ui.fragment.EventDetailsFragment;
import com.example.besher.materialtest.ui.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {
    public final static int FRAGMENT_MAIN = 0;
    public final static int FRAGMENT_CREATE_NEW_EVENT = 1;
    public final static int FRAGMENT_EDIT_EVENT = 2;
    public final static int FRAGMENT_EVENT_DETAILS = 3;


    private MainFragment mMainFragment;
    private AddEventFragment mAddAnEvent;
    private EventDetailsFragment mEventDetailsFragment;

    private int currentFragment = FRAGMENT_MAIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerFragment();

    }

    public void pagerFragment() {
        currentFragment = FRAGMENT_MAIN;
        if (mMainFragment == null)
            mMainFragment = MainFragment.newInstance();
        setFragment(mMainFragment);
    }


    public void addNewEvent(SilenceItem silenceItem) {
        currentFragment = FRAGMENT_CREATE_NEW_EVENT;
        if(silenceItem == null)
            mAddAnEvent = AddEventFragment.newInstance();
        else {
            mAddAnEvent = AddEventFragment.newInstance(silenceItem);
            //Toast.makeText(this , "Item"+silenceItem.getStartHour(), Toast.LENGTH_SHORT).show();
        }
        setFragment(mAddAnEvent);
    }

    public void editEventFragment(SilenceItem silenceItem) {
        currentFragment = FRAGMENT_EDIT_EVENT;
        setFragment(EditEventFragment.newInstance(silenceItem));
    }

    public void eventDetails(SilenceItem silenceItem) {
        currentFragment = FRAGMENT_EVENT_DETAILS;
        setFragment(EventDetailsFragment.newInstance(silenceItem));
    }

    void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, fragment).commit();
    }

    public void setSilent() {

    }


    @Override
    public void onBackPressed() {
        switch (currentFragment) {
            case FRAGMENT_CREATE_NEW_EVENT:
                pagerFragment();
                break;
            case FRAGMENT_EDIT_EVENT:
                pagerFragment();
                break;
            case FRAGMENT_EVENT_DETAILS:
                pagerFragment();
                break;
            case FRAGMENT_MAIN:
                ;
            default:
                super.onBackPressed();
        }
    }
/*
    public void addNewItem(SilenceItem silenceItem) {
        if(mMainFragment !=null){
            mMainFragment.addNewItem(silenceItem);
        }
    }*/
}
