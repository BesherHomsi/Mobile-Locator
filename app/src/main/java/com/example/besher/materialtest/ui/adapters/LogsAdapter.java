package com.example.besher.materialtest.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.besher.materialtest.R;
import com.example.besher.materialtest.models.Logs;
import com.example.besher.materialtest.ui.fragment.LogsListFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Besher on 03/16/18.
 */

public class LogsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Logs> mValues = new ArrayList<>();
    LogsListFragment logsListFragment;

    public LogsAdapter(ArrayList<Logs> mValues, LogsListFragment logsListFragment) {
        this.mValues = mValues;
        this.logsListFragment = logsListFragment;

    }

    public void setData(ArrayList<Logs> data) {
        this.mValues = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myLogs = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_logs, parent, false);
        return new DisplayLogs(myLogs);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DisplayLogs) {
            ((DisplayLogs) holder).setData(mValues.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class DisplayLogs extends RecyclerView.ViewHolder {

        private Logs log;
        private TextView mLogName;
        private TextView mLogDate;


        public DisplayLogs(View itemView) {
            super(itemView);

            setUpView(itemView);
        }


        public void setUpView(View v) {

            mLogName = (TextView) v.findViewById(R.id.eventLog);
            mLogDate = (TextView) v.findViewById(R.id.eventLogDate);

        }

        public void setData(Logs log) {

            Calendar logDate = Calendar.getInstance();
            this.log = log;
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
            String formatted;

            logDate.setTimeInMillis(this.log.getLogtime());
            formatted = format1.format(logDate.getTime());
            mLogDate.setText(formatted);
            mLogName.setText(this.log.getLogName());

        }






    }


}
