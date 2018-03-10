package com.example.besher.materialtest.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Besher on 03/06/18.
 */

public class MyContact {

    public static String ON_STATUS = "on";
    public static String OFF_STATUS = "off";

    private String id;
    private String name;
    private String number;
    private String status;

    public MyContact(String id, String name, String number, String status) {

        this.id = id;
        this.name = name;
        this.number = number;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCheckedContact() {

        if(status.equals("on"))
            return true;
        return false;
    }
}
