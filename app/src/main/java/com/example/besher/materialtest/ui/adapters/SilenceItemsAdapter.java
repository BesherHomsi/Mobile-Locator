package com.example.besher.materialtest.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.activity.MainActivity;
import com.example.besher.materialtest.ui.fragment.SilenceItemsListFragment;

import java.util.ArrayList;

/**
 * Created by Besher on 02/04/18.
 */

public class SilenceItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    ArrayList<SilenceItem> mValues;
    SilenceItemsListFragment silenceItemsListFragment;
    private RecyclerView mRecyclerView;

    public SilenceItemsAdapter(ArrayList<SilenceItem> mValues, SilenceItemsListFragment silenceItemsListFragment) {
        this.mValues = mValues;
        this.silenceItemsListFragment = silenceItemsListFragment;
    }

    public void setData(ArrayList<SilenceItem> data) {
        this.mValues = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View silenceItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_silence, parent, false);

        return new SilenceItemHolder(silenceItemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SilenceItemHolder) {
            ((SilenceItemHolder) holder).setData(mValues.get(position));

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class SilenceItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        SilenceItem silenceItem;

        TextView mTitle;
        TextView mStartTime;
        TextView mAmPm;
        TextView mSelectedDays;
        Button mDelete;
        Button mEdit;
        Switch mCancelAlarm;


        public SilenceItemHolder(View itemView) {
            super(itemView);

            setUpView(itemView);
            itemView.setOnClickListener(this);
            //mDelete.setOnClickListener(this);
            //mEdit.setOnClickListener(this);
            mCancelAlarm.setOnClickListener(this);

        }

        void setUpView(View itemView) {

            mTitle = (TextView) itemView.findViewById(R.id.title);
            mStartTime = (TextView) itemView.findViewById(R.id.startTime);
            mAmPm = (TextView) itemView.findViewById(R.id.AmPm);
            //mSelectedDays = (TextView) itemView.findViewById(R.id.selectedDays);
            //mDelete = (Button) itemView.findViewById(R.id.delete);
            //mEdit = (Button) itemView.findViewById(R.id.edit);
            mCancelAlarm = (Switch) itemView.findViewById(R.id.cancelAlarm);
        }

        void setData(SilenceItem silenceItem) {
            this.silenceItem = silenceItem;
            int hour = this.silenceItem.getStartHour();
            int min = this.silenceItem.getStartMin();

            if (hour > 12) {
                hour = hour - 12;
            }

            if((hour < 10) && (min < 10))
                mStartTime.setText("0"+hour+":0"+min);
            else if((hour < 10))
                mStartTime.setText("0"+hour+":"+min);
            else if((min < 10))
                mStartTime.setText(""+hour+":0"+min);
            else mStartTime.setText(""+hour+":"+min);


            mAmPm.setText(silenceItem.getStart_AM_PM());
            mTitle.setText(silenceItem.getTitle());
            mCancelAlarm.setChecked(silenceItem.isSet());
            //mSelectedDays.setText(silenceItem.getSelectedDays());

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                /*
                case R.id.delete:
                    silenceItemsListFragment.deleteItem(silenceItem);
                    break;
                case R.id.edit:
                    silenceItemsListFragment.editEvent(silenceItem);
                    break;*/
                case R.id.cancelAlarm:
                    if(!mCancelAlarm.isChecked())
                        silenceItemsListFragment.cancelAlarm(silenceItem);
                    else
                        silenceItemsListFragment.setAlarm(silenceItem);
                    break;
                default:
                    silenceItemsListFragment.displayEvent(silenceItem);
                    break;
            }
        }
    }
}
