package com.example.besher.materialtest.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by Besher on 02/25/18.
 */

public class ErorrHandler {

    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+" +
            "(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";

    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "###-#######";

    public static boolean isBlank(EditText editText) {

        String text = editText.getText().toString();
        if(text.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean startHourGreater(int startHour, int endHour, String startAmPm, String endAmPm) {

        if(startAmPm.equals(endAmPm))
            if(startHour > endHour)
                return true;
        return false;
    }

    public static boolean endDateCheck(long endDate) {

        Calendar now = Calendar.getInstance();
        Calendar myCalendarEndDate = Calendar.getInstance();

        myCalendarEndDate.setTimeInMillis(endDate);

        if( myCalendarEndDate.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                myCalendarEndDate.get(Calendar.MONTH)== now.get(Calendar.MONTH) &&
                myCalendarEndDate.get(Calendar.DAY_OF_WEEK) == now.get(Calendar.DAY_OF_WEEK)){
            return true;
        }
        return false;
    }
}

