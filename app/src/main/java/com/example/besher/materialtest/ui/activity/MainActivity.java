package com.example.besher.materialtest.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.fragment.AddEventFragment;
import com.example.besher.materialtest.ui.fragment.EditEventFragment;
import com.example.besher.materialtest.ui.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {
    public final static int FRAGMENT_MAIN = 0;
    public final static int FRAGMENT_CREATE_NEW_EVENT = 1;
    public final static int FRAGMENT_EDIT_EVENT = 2;


    private MainFragment mMainFragment;
    private AddEventFragment mAddAnEvent;
    private EditEventFragment mEditEvent;

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


    public void addNewEvent(){
        currentFragment = FRAGMENT_CREATE_NEW_EVENT;
        if (mAddAnEvent == null)
            mAddAnEvent = AddEventFragment.newInstance();
        setFragment(mAddAnEvent);
    }

    public void editEventFragment() {
        currentFragment = FRAGMENT_EDIT_EVENT;
        if (mEditEvent == null)
            mEditEvent = mEditEvent.newInstance();
        setFragment(mEditEvent);
    }
    void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        switch (currentFragment){
            case FRAGMENT_CREATE_NEW_EVENT:
                pagerFragment();
                break;
            case FRAGMENT_EDIT_EVENT:
                pagerFragment();
                break;
            case FRAGMENT_MAIN:;
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
