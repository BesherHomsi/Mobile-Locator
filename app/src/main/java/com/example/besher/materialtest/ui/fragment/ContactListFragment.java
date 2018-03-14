package com.example.besher.materialtest.ui.fragment;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.R;
import com.example.besher.materialtest.helpers.Constant;
import com.example.besher.materialtest.helpers.MyContactManager;
import com.example.besher.materialtest.helpers.Utility;
import com.example.besher.materialtest.models.MyContact;
import com.example.besher.materialtest.ui.Dialog.DisplayContactDialog;
import com.example.besher.materialtest.ui.adapters.DisplayContactsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {


    public final static String PREF_SAVED_CONTACTS = "saved_contacts";


    public final static String KEY_SAVED_CONTACTS_LIST = "saved_contacts_list";
    public final static String KEY_MY_CONTACTS_LIST = "my_contacts_list";


    private ArrayList<MyContact> myContactsList = new ArrayList<>();
    private ArrayList<MyContact> mySavedContactsList = new ArrayList<>();

    private DisplayContactsAdapter displayContactsAdapter;
    private RecyclerView rv_contactList;
    View root;


    private List<String> contactName = new ArrayList<String>();

    private HashMap<String, List<String>> contact = new HashMap<>();

    public ContactListFragment() {
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
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_contact_list, container, false);
            setUpView(root);
            displayContactsAdapter = new DisplayContactsAdapter(myContactsList, this);
            rv_contactList.setAdapter(displayContactsAdapter);
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mySavedContactsList = MyContactManager.getListFromPref(ControllerApplication.getInstance(),
                KEY_SAVED_CONTACTS_LIST);
        myContactsList = MyContactManager.getListFromPref(ControllerApplication.getInstance(),
                KEY_MY_CONTACTS_LIST);
        if (myContactsList.size() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ControllerApplication.getInstance().
                    checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                getContactBackground();
            } else {
               // requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, Constant.PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            if (displayContactsAdapter != null)
                displayContactsAdapter.setData(myContactsList);
        }

    }


    void getContactBackground() {
        AsyncTask getContactTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                myContactsList.clear();
            }

            @Override
            protected Object doInBackground(Object[] params) {

                retrieveContacts();

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                displayContactsAdapter.setData(myContactsList);
                super.onPostExecute(o);
            }
        };
        getContactTask.execute();
    }


    public void setUpView(View v) {

        rv_contactList = (RecyclerView) v.findViewById(R.id.rv_myContacts);
        rv_contactList.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void retrieveContacts() {

        ContentResolver resolver = ControllerApplication.getInstance().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null)
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            if (name != null) {
                this.contactName.add(name);

                Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{id}, null);

                String s = "";
                while (phoneCursor.moveToNext()) {
                    //this.phoneNumber.add();
                    s = s + phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract
                            .CommonDataKinds.Phone.NUMBER)) + ",";

                }
                String status = "off";
                myContactsList.add(new MyContact(id, name, s, status));
            }
        }
        MyContactManager.saveListToPref(getActivity(), myContactsList,
                KEY_MY_CONTACTS_LIST);
    }

    public void addMySavedContact(MyContact myContact) {


        if (!mySavedContactsList.contains(myContact)) {
            myContact.setStatus("on");
            mySavedContactsList.add(myContact);
        }
/*
        for (int i = 0; i < myContactsList.size(); i++) {

            if (myContactsList.get(i).getId().equals(myContact.getId()))
                myContactsList.get(i).setStatus("on");
                break;
        }*/

        MyContactManager.saveListToPref(ControllerApplication.getInstance(), mySavedContactsList,
                KEY_SAVED_CONTACTS_LIST);

        MyContactManager.saveListToPref(ControllerApplication.getInstance(), myContactsList,
                KEY_MY_CONTACTS_LIST);
    }

    public void deleteSavedContact(MyContact myContact) {

        myContact.setStatus("off");
        /*
        for (int i = 0; i < myContactsList.size(); i++) {

            if (myContactsList.get(i).getId().equals(myContact.getId()))
                myContactsList.get(i).setStatus("off");
                break;
        }*/

        for (int i = 0; i < mySavedContactsList.size(); i++) {
            if (mySavedContactsList.get(i).getId().equals(myContact.getId())) {
                mySavedContactsList.remove(i);
                //displayContactsAdapter.setData(myContactsList);
                break;
            }
        }
        MyContactManager.saveListToPref(ControllerApplication.getInstance(), myContactsList,
                KEY_MY_CONTACTS_LIST);

        MyContactManager.deleteListPref(ControllerApplication.getInstance(), myContact,
                KEY_SAVED_CONTACTS_LIST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.PERMISSIONS_REQUEST_READ_CONTACTS:

                break;
        }
    }

}
