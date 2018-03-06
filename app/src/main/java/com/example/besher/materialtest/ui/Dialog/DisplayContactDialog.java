package com.example.besher.materialtest.ui.Dialog;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayContactDialog extends DialogFragment {

    private List<String> phoneNumber = new ArrayList<String>();
    private List<String> contactName = new ArrayList<String>();
    private String[] namesStrings;
    private HashMap<String,List<String>> contact = new HashMap<>();


    private boolean[] checkedList;
    private List<String> selectedNames = new ArrayList<String>();

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    public static DisplayContactDialog newInstance() {

        Bundle args = new Bundle();
        DisplayContactDialog fragment = new DisplayContactDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ControllerApplication.getInstance().
                checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        }
        else
            retrieveContacts();
        String[] phones = new String[phoneNumber.size()];
        phones = phoneNumber.toArray(phones);

        String[] names = new String[contactName.size()];
        names = contactName.toArray(names);
        namesStrings = names;
        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)getActivity());
        builder.setTitle("Contact");
        builder.setMultiChoiceItems(names, checkedList,
                new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    if (!selectedNames.contains(namesStrings[which])) {
                        selectedNames.add(namesStrings[which]);
                    }
                } else {
                    selectedNames.remove(namesStrings[which]);
                }
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((MainActivity) getContext()).addNewEvent(silenceItem);
                getSelectedName();

            }
        });

        Dialog dialog = builder.create();
        return dialog;
    }

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
                Log.v("asdf","asdf");
            }
        }
        Log.v("asdf","asdf");
    }

    public void getSelectedName() {
        String results="";
        for (String s :
                selectedNames) {
            if(contact.containsKey(s)) {
                results = results + contact.get(s).toArray().toString();
                Log.v("asdf","asdf");
            }
        }


    }

}

