package com.example.besher.materialtest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.besher.materialtest.ControllerApplication;

/**
 * Created by Besher on 03/04/18.
 */

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) ControllerApplication.getInstance()
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //Create Listner
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {

        }

    }

    private class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {

            if (state == 1) {

                String msg = "New Phone Call Event. Incomming Number : "+incomingNumber;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(ControllerApplication.getInstance(), msg, duration);
                toast.show();

            }
        }
    }
}
