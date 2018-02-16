package com.example.besher.materialtest.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.SilenceItem;
import com.example.besher.materialtest.ui.activity.MainActivity;
import com.example.besher.materialtest.ui.fragment.SilenceItemsListFragment;

import java.util.ArrayList;

/**
 * Created by Besher on 02/04/18.
 */

public class SilenceItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<SilenceItem> mValues;
    SilenceItemsListFragment silenceItemsListFragment;

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


        public SilenceItemHolder(View itemView) {
            super(itemView);

            setUpView(itemView);

            mDelete.setOnClickListener(this);
            mEdit.setOnClickListener(this);

        }

        void setUpView(View itemView) {

            mTitle = (TextView) itemView.findViewById(R.id.title);
            mStartTime = (TextView) itemView.findViewById(R.id.startTime);
            mAmPm = (TextView) itemView.findViewById(R.id.AmPm);
            mSelectedDays = (TextView) itemView.findViewById(R.id.selectedDays);
            mDelete = (Button) itemView.findViewById(R.id.delete);
            mEdit = (Button) itemView.findViewById(R.id.edit);
        }

        void setData(SilenceItem silenceItem) {
            this.silenceItem = silenceItem;

            mStartTime.setText(""+silenceItem.getStartHour()+":"+silenceItem.getStartMin());
            mAmPm.setText(silenceItem.getPM_AM());
            mTitle.setText(silenceItem.getTitle()+", ");
            mSelectedDays.setText(silenceItem.getSelectedDays());
            //mdays.setText(silenceItem.getDays());
            //mDate.setText(silenceItem.getDate());
            //mYear.setText(silenceItem.getYear());

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.delete:
                    mValues.remove(this.getAdapterPosition());
                    notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.edit:
                    ((MainActivity)v.getContext()).editEventFragment(this);
                    Toast.makeText(v.getContext(), "Item has been changed", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(v.getContext(), "Error!!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
