package com.example.besher.materialtest.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.besher.materialtest.R;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEventFragment extends Fragment {

    private TextView mEditTitle;

    public static EditEventFragment newInstance(RecyclerView.ViewHolder holder) {

        Bundle args = new Bundle();
        args.putSerializable("key", (Serializable) holder);
        EditEventFragment fragment = new EditEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView.ViewHolder r;
        Bundle args = new Bundle();
        r = (RecyclerView.ViewHolder) args.getSerializable("key");

        View v = inflater.inflate(R.layout.fragment_edit_event, container, false);

        setUpView(v);

        return v;
    }

    public void setUpView(View v) {

        mEditTitle = (TextView) v.findViewById(R.id.editTitle);
    }

}
