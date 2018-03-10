package com.example.besher.materialtest.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.MyContact;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.Dialog.DisplayContactDialog;
import com.example.besher.materialtest.ui.fragment.ContactListFragment;

import java.util.ArrayList;

/**
 * Created by Besher on 03/02/18.
 */

public class DisplayContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<MyContact> mValues = new ArrayList<>();
    ContactListFragment contactListFragment;


    public DisplayContactsAdapter(ArrayList<MyContact> mValues,ContactListFragment contactListFragment ) {

        this.mValues = mValues;
        this.contactListFragment = contactListFragment;
    }

    public void setData(ArrayList<MyContact> data) {
        this.mValues = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View myContactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_contact, parent, false);
        return new DisplayContactsHolder(myContactView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DisplayContactsHolder) {
            ((DisplayContactsHolder) holder).setData(mValues.get(position));

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class DisplayContactsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MyContact myContact;
        TextView mContactName;
        TextView mContactPhoneNumber;
        CheckBox mSavedContact;

        public DisplayContactsHolder(View itemView) {
            super(itemView);

            setUpView(itemView);

            //itemView.setOnClickListener(this);
            mSavedContact.setOnClickListener(this);
        }

        public void setUpView(View itemView) {

            mContactName = (TextView) itemView.findViewById(R.id.contactName);
            mContactPhoneNumber = (TextView) itemView.findViewById(R.id.contactNumber);
            mSavedContact = (CheckBox) itemView.findViewById(R.id.chosenContact);

        }

        public void setData(MyContact myContact){

            this.myContact = myContact;
            mContactName.setText(myContact.getName());
            mContactPhoneNumber.setText(myContact.getNumber().toString());

            if(myContact.isCheckedContact())
                mSavedContact.setChecked(true);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.chosenContact:
                    //if(!mSavedContact.isChecked())
                    contactListFragment.addMySavedContact(myContact);
                    break;

            }
        }
    }
}
