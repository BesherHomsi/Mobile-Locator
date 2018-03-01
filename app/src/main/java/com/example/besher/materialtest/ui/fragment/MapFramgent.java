package com.example.besher.materialtest.ui.fragment;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFramgent extends Fragment implements View.OnClickListener {

    FloatingActionButton btnNewEventTrial;
    private SilenceItem silenceItem = null;

    public MapFramgent() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        setUpView(root);

        btnNewEventTrial.setOnClickListener(this);


        return root;
    }

    private void setUpView(View view) {
        btnNewEventTrial = (FloatingActionButton) view.findViewById(R.id.btn_new_event_trial);
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
    }

    @Override
    public void onClick(View v) {
        ((MainActivity)getActivity()).addNewEvent(silenceItem);
    }
}
