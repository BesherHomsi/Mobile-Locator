package com.example.besher.materialtest.ui.Dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutDialog extends DialogFragment {



    public static AboutDialog newInstance() {

        Bundle args = new Bundle();
        AboutDialog fragment = new AboutDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)getActivity());
        builder.setTitle("About");
        builder.setMessage("Version: "+ControllerApplication.getVersionName());

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((MainActivity) getContext()).addNewEvent(silenceItem);

            }
        });

        Dialog dialog = builder.create();
        return dialog;
    }


/*
    public void retrieveContacts() {
        List<String> phoneNumbers;
        int i= 0;
        ContentResolver resolver = ControllerApplication.getInstance().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null,null,null,null);


        while(cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            this.phoneNumber.clear();
            if(name != null) {
                this.contactName.add(name);

                Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{id}, null);


                while (phoneCursor.moveToNext()) {
                    this.phoneNumber.add(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract
                            .CommonDataKinds.Phone.NUMBER)));

                }
                if(this.phoneNumber != null)
                    this.contact.put(name,this.phoneNumber);
            }
        }
    }

    public void getSelectedName() {
        String results="";
        for (String s :
                selectedNames) {
            if(contact.containsKey(s)) {
                results = results + contact.get(s).toArray().toString();
            }
        }


    }

*/
}

