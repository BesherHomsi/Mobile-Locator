package com.example.besher.materialtest.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.R;
import com.example.besher.materialtest.helpers.LogManager;
import com.example.besher.materialtest.models.Logs;
import com.example.besher.materialtest.ui.adapters.LogsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogsListFragment extends Fragment implements View.OnClickListener{


    private ArrayList<Logs> logsList = new ArrayList<>();

    private LogsAdapter logsAdapter;
    private RecyclerView rv_logs;
    private FloatingActionButton mClearLogs;
    private Toolbar mToolBar;

    View view;

    public LogsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_logs_list, container, false);
        setUpView(view);
        mToolBar.setTitle("Mobile Locator");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);
        logsAdapter = new LogsAdapter(logsList,this);

        rv_logs.setAdapter(logsAdapter);
        logsList = LogManager.getListFromPref(getActivity());
        logsAdapter.setData(logsList);
        mClearLogs.setOnClickListener(this);

        return view;
    }

    public void setUpView(View view) {

        mToolBar = (Toolbar) view.findViewById(R.id.tool_bar_logs);
        rv_logs = (RecyclerView) view.findViewById(R.id.rv_logs);
        rv_logs.setLayoutManager(new LinearLayoutManager(getContext()));
        mClearLogs = (FloatingActionButton) view.findViewById(R.id.clearLogs);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clearLogs:
                ArrayList<Logs> logList = new ArrayList<>();

                LogManager.saveListToPref(ControllerApplication.getInstance(), logList);
                logsAdapter.setData(logList);

                break;
            default:
                break;
        }
    }
}
