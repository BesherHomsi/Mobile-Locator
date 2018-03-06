package com.example.besher.materialtest.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.Dialog.DisplayContactDialog;

import java.util.ArrayList;

/**
 * Created by Besher on 03/02/18.
 */

public class DisplayContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<String> mValues = new ArrayList<>();


    public void setData(ArrayList<String> data) {
        this.mValues = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
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

        TextView mContactName;
        TextView mContactPhoneNumber;

        public DisplayContactsHolder(View itemView) {
            super(itemView);
        }

        public void setUpView() {
        }

        public void setData(String phoneNumber){

        }

        @Override
        public void onClick(View v) {

        }
    }
}
