package com.example.besher.materialtest.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEventFragment extends Fragment {

    private TextView mEditTitle;
    private Toolbar mToolBar;
    private SilenceItem silenceItem;


    public static EditEventFragment newInstance() {
        Bundle args = new Bundle();
        EditEventFragment fragment = new EditEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static EditEventFragment newInstance(SilenceItem silenceItem) {
        Bundle args = new Bundle();
        args.putSerializable("item", silenceItem);

        EditEventFragment fragment = new EditEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            silenceItem = (SilenceItem) getArguments().getSerializable("item");
        }
    }

    public EditEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_edit_event, container, false);

        setUpView(v);
        mToolBar.setTitle("Edit Event");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);


        return v;
    }

    public void setUpView(View v) {


        mToolBar = (Toolbar) v.findViewById(R.id.tool_bar);
    }

}
