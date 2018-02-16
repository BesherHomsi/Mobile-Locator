package com.example.besher.materialtest.models;

import java.util.ArrayList;

/**
 * Created by Besher on 02/04/18.
 */

public class SilenceItem {
    private String title;
    private String selectedDays;
    private long startDate;
    private long endDate;
    private int startHour;
    private int startMin;
    private String PM_AM;


    public SilenceItem(String name,String days,long startDate, long endDate, int startHour, int startMin,String PM_AM) {
        this.title = name;
        this.selectedDays = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startHour = startHour;
        this.startMin = startMin;
        this.PM_AM = PM_AM;
    }


    public String getTitle() {
        return title;
    }

    public int getStartHour() { return startHour; }

    public int getStartMin() { return startMin; }

    public String getPM_AM() { return PM_AM; }

    public String getSelectedDays() { return selectedDays; }

    //public String getYear() { return year; }

}
