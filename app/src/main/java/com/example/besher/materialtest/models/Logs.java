package com.example.besher.materialtest.models;

/**
 * Created by Besher on 03/16/18.
 */

public class Logs {

    private String logName;
    private long logTime;


    public Logs(String logName, long time) {
        this.logName = logName;
        this.logTime = time;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public long getLogtime() {
        return logTime;
    }

    public void segLogtime(long time) {
        this.logTime = time;
    }
}
