package com.example.besher.materialtest.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.helpers.SilenceManager;
import com.example.besher.materialtest.ui.Dialog.EventDetailsDialog;
import com.example.besher.materialtest.ui.Interfaces.ItemEventTrigger;
import com.example.besher.materialtest.ui.activity.MainActivity;
import com.example.besher.materialtest.ui.adapters.SilenceItemsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SilenceItemsListFragment extends Fragment implements View.OnClickListener, ItemEventTrigger.ItemEvent {

    private FloatingActionButton mBtnNewEvent;

    private ArrayList<SilenceItem> mSilenceItemList = new ArrayList<>();
    private SilenceItemsAdapter mAdapter;
    private RecyclerView mRVList;
    private SilenceItem silenecItem = null;
    private int i = 0;
    View root;

    public SilenceItemsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemEventTrigger.addListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null) {

            root = inflater.inflate(R.layout.fragment_silence_items_list, container, false);

            setUpView(root);
            mSilenceItemList = SilenceManager.getListFromPref(ControllerApplication.getInstance());

            mAdapter = new SilenceItemsAdapter(mSilenceItemList, SilenceItemsListFragment.this);
            mRVList.setAdapter(mAdapter);
            mBtnNewEvent.setOnClickListener(this);
            mAdapter.setData(mSilenceItemList);
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setUpView(View view) {
        mRVList = (RecyclerView) view.findViewById(R.id.rv_items);
        mBtnNewEvent = (FloatingActionButton) view.findViewById(R.id.btn_new_event);
        mRVList.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // ItemEventTrigger.removeListner(this);
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).addNewEvent(silenecItem);
    }

    public void addNewItem(SilenceItem silenceItem) {
        if (!mSilenceItemList.contains(silenceItem))
            mSilenceItemList.add(silenceItem);
        SilenceManager.saveListToPref(ControllerApplication.getInstance(), mSilenceItemList);
        SilenceManager.registerEvent(ControllerApplication.getInstance(), silenceItem);
        mAdapter.setData(mSilenceItemList);

    }

    @Override
    public void onNewItem(SilenceItem silenceItem) {
        addNewItem(silenceItem);
    }

    @Override
    public void onUpdateItem(SilenceItem silenceItem) {

        SilenceManager.updateListPref(ControllerApplication.getInstance(),silenceItem);
        for (int i = 0; i < mSilenceItemList.size(); i++) {
            if (mSilenceItemList.get(i).getId() == silenceItem.getId()   ) {
                mSilenceItemList.set(i,silenceItem);
                mAdapter.setData(mSilenceItemList);
                break;
            }
        }
    }

    @Override
    public void onDeleteItem(SilenceItem silenceItem) {

        SilenceManager.deleteListPref(ControllerApplication.getInstance(), silenceItem);
        SilenceManager.cancelAlarm(ControllerApplication.getInstance(), silenceItem);
        for (int i = 0; i < mSilenceItemList.size(); i++) {
            if (mSilenceItemList.get(i).getId() == silenceItem.getId()   ) {
                mSilenceItemList.remove(i);
                mAdapter.setData(mSilenceItemList);
                break;
            }
        }


    }

    public void displayEvent(SilenceItem silenceItem) {
        EventDetailsDialog daysDialog = EventDetailsDialog.newInstance(silenceItem);
        daysDialog.show(getFragmentManager(),"any");

    }

    public void setAlarm(SilenceItem silenceItem) {

        SilenceManager.registerEvent(ControllerApplication.getInstance(), silenceItem);
    }

    public void cancelAlarm(SilenceItem silenceItem) {
        SilenceManager.cancelAlarm(ControllerApplication.getInstance(),silenceItem);
    }

  /*  public void editEvent(SilenceItem silenceItem) {
        ((MainActivity) getContext()).addNewEvent(silenceItem);
    }*/

    /*public void deleteItem(SilenceItem silenceItem) {

        SilenceManager.deleteListPref(ControllerApplication.getInstance(), silenceItem);
        SilenceManager.cancelAlarm(ControllerApplication.getInstance(), silenceItem);
        for (int i = 0; i < mSilenceItemList.size(); i++) {
            if (mSilenceItemList.get(i).getId() == silenceItem.getId()   ) {
                mSilenceItemList.remove(i);
                mAdapter.setData(mSilenceItemList);
                break;
            }
        }
    }*/
}
