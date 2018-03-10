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


    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private ArrayList<MyContact> myContactsList = new ArrayList<>();
    private ArrayList<MyContact> mySavedContactsList = new ArrayList<>();
    private Boolean flag = false;

    private DisplayContactsAdapter displayContactsAdapter;
    private RecyclerView rv_contactList;
    View root;



    private List<String> contactName = new ArrayList<String>();

    private HashMap<String,List<String>> contact = new HashMap<>();

    public ContactListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ControllerApplication.getInstance().
                checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            flag = false;
        }
        else
            flag = true;//retrieveContacts();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_contact_list, container, false);
            setUpView(root);
            mySavedContactsList = MyContactManager.getListFromPref(ControllerApplication.getInstance());
            //if(myContactsList.size() == 0 )
            displayContactsAdapter = new DisplayContactsAdapter(myContactsList, ContactListFragment.this);
            rv_contactList.setAdapter(displayContactsAdapter);
            displayContactsAdapter.setData(myContactsList);
        }
        return root;
    }

    void getContactBackground(){
        AsyncTask getContactTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                displayContactsAdapter.setData(myContactsList);
                super.onPostExecute(o);
            }
        };
    }

    public void setUpView(View v) {

        rv_contactList = (RecyclerView) v.findViewById(R.id.rv_myContacts);
        rv_contactList.setLayoutManager(new LinearLayoutManager(getContext()));
        //getContactBackground();
        if(flag)
            retrieveContacts();
    }


    public void retrieveContacts() {

        ContentResolver resolver = ControllerApplication.getInstance().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null,null,null,null);


        while(cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            if(name != null) {
                this.contactName.add(name);

                Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{id}, null);

                String s= "";
                while (phoneCursor.moveToNext()) {
                    //this.phoneNumber.add();
                    s = s + phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract
                            .CommonDataKinds.Phone.NUMBER))+",";

                }
                String status = "off";
                String Uid = UUID.randomUUID().toString();
                for (MyContact item:
                     mySavedContactsList) {
                    if(s.equals(item.getNumber()))
                        status = "on";
                }
                myContactsList.add(new MyContact(Uid,name,s,status));
                s="";
            }
        }
        //MyContactManager.saveListToPref(getActivity(), myContactsList);
    }

    public void addMySavedContact(MyContact myContact) {

        if(!mySavedContactsList.contains(myContact))
            mySavedContactsList.add(myContact);

        MyContactManager.saveListToPref(getActivity() ,mySavedContactsList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS:
                if (Utility.permissionsGranted(grantResults)) {
                    retrieveContacts();
                }
                break;
        }
    }

}
