package com.example.besher.materialtest.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.MyCLocation;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.Dialog.DisplayContactDialog;
import com.example.besher.materialtest.ui.fragment.AddEventFragment;
import com.example.besher.materialtest.ui.fragment.AddMapFragment;
import com.example.besher.materialtest.ui.fragment.MainFragment;

public class MainActivity extends AppCompatActivity  {
    public final static int FRAGMENT_MAIN = 0;
    public final static int FRAGMENT_CREATE_NEW_EVENT = 1;
    public final static int FRAGMENT_EDIT_EVENT = 2;
    public final static int FRAGMENT_EVENT_DETAILS = 3;
    public final static int FRAGMENT_ADD_LOCATION = 4;


    private MainFragment mMainFragment;
    private AddEventFragment mAddAnEvent;

    private int currentFragment = FRAGMENT_MAIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu1:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pagerFragment() {
        currentFragment = FRAGMENT_MAIN;
        if (mMainFragment == null)
            mMainFragment = MainFragment.newInstance();
        setFragment(mMainFragment);
    }


    public void addNewEvent(SilenceItem silenceItem) {
        currentFragment = FRAGMENT_CREATE_NEW_EVENT;
        if (silenceItem == null)
            mAddAnEvent = AddEventFragment.newInstance();
        else {
            mAddAnEvent = AddEventFragment.newInstance(silenceItem);
            //Toast.makeText(this , "Item"+silenceItem.getStartHour(), Toast.LENGTH_SHORT).show();
        }

        setFragment(mAddAnEvent);
    }

    public void addLocationToEvent() {
        currentFragment = FRAGMENT_ADD_LOCATION;
        AddMapFragment addMapFragment = new AddMapFragment();
        setFragment(addMapFragment);
    }

    public void returnToAddEvent(MyCLocation myCLocation) {
        currentFragment = FRAGMENT_CREATE_NEW_EVENT;
        if (mAddAnEvent == null) {
            mAddAnEvent = AddEventFragment.newInstance();
        }
        mAddAnEvent.setEventLocation(myCLocation);
        setFragment(mAddAnEvent);
    }

    public void editEventFragment(SilenceItem silenceItem) {
        currentFragment = FRAGMENT_EDIT_EVENT;
        //setFragment(new ContactsFragment());
    }

    public void eventDetails(SilenceItem silenceItem) {
        currentFragment = FRAGMENT_EVENT_DETAILS;
        //setFragment(new ContactsFragment());
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
                mAddAnEvent = null;
                pagerFragment();
                break;
            case FRAGMENT_EDIT_EVENT:
                pagerFragment();
                break;
            case FRAGMENT_EVENT_DETAILS:
                pagerFragment();
                break;
            case FRAGMENT_ADD_LOCATION:
                returnToAddEvent(null);
                break;
            case FRAGMENT_MAIN:
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
