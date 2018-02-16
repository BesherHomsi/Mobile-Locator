package com.example.besher.materialtest.ui.Dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.ui.activity.MainActivity;
import com.example.besher.materialtest.ui.fragment.AddEventFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaysDialog extends DialogFragment {


    public static DaysDialog newInstance(ArrayList<Integer> days) {

        Bundle args = new Bundle();
        args.putIntegerArrayList("days",days);
        DaysDialog fragment = new DaysDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle args = getArguments();
        final ArrayList<Integer> days = args.getIntegerArrayList("days");

        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)getActivity());
        builder.setTitle("Days");
        builder.setMultiChoiceItems(R.array.days, null, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    Toast.makeText((MainActivity) getActivity(), "item was selected", Toast.LENGTH_SHORT).show();
                    days.add(which);
                }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText((MainActivity) getActivity(), "Canceld", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText((MainActivity)getActivity(),"OK", Toast.LENGTH_SHORT).show();
            }
        });

        Dialog dialog = builder.create();

        return dialog;
    }

}
