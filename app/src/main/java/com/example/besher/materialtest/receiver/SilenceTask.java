package com.example.besher.materialtest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.helpers.Constant;
import com.example.besher.materialtest.helpers.LogManager;
import com.example.besher.materialtest.helpers.MyLocationManager;
import com.example.besher.materialtest.helpers.SilenceManager;
import com.example.besher.materialtest.helpers.Utility;
import com.example.besher.materialtest.models.MyCLocation;
import com.example.besher.materialtest.ui.Interfaces.ItemEventTrigger;


/**
 * Created by Besher on 02/23/18.
 */

public class SilenceTask extends BroadcastReceiver {

    private MyCLocation myCLocation;

    @Override
    public void onReceive(Context context, Intent intent) {

        Double lat = 0.0;
        Double lng = 0.0;
        String status = "";
        AudioManager audioManager = (AudioManager) ControllerApplication.getInstance()
                .getSystemService(ControllerApplication.getInstance()
                        .AUDIO_SERVICE);
        if (intent.getExtras() != null) {

            Bundle bundle = intent.getExtras();
            String silenceItem = bundle.getString("item");
            if (silenceItem != null) {
                String[] eventLocation = silenceItem.split(":");
                status = eventLocation[0];
                lat = Double.valueOf(eventLocation[1]);
                lng = Double.valueOf(eventLocation[2]);
            }
        }
        switch (intent.getAction()) {
            case SilenceManager.ACTION_SET_SILENCE:
                if (status.equals("off")) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Utility.makeNotification(context, "The phone is in silent mode", null, null, "silent");
                    LogManager.logAction("The phone is silent");
                }
                else {
                    //check if gps enabled
                    //if enabled user gps to get location;
                    //not enabled use tower;
                    myCLocation = MyLocationManager.getLocationViaTower(context);
//                    if ((lng - Constant.RADUIES <= myCLocation.getLng() && myCLocation.getLng() <= lng + Constant.RADUIES) &&
//                            (lat - Constant.RADUIES <= myCLocation.getLat() && myCLocation.getLat() <= lat + Constant.RADUIES))
                    if (myCLocation != null) {
                        float[] distance = new float[2];
                        Location.distanceBetween(myCLocation.getLat(), myCLocation.getLng(), lat, lng, distance);
                        if (distance[0] <= Constant.RADUIES) {
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            Utility.makeNotification(context, "The phone is in silent mode",null,null,"silent");
                            LogManager.logAction("The phone is silent");
                        }
                    }

                }
                break;
            case SilenceManager.ACTION_REMOVE_SILENCE:
                if (SilenceManager.checkIfEventISActiveWithLocation(context)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    Utility.makeNotification(context, "The phone is in normal mode",null,null,"normal");
                    LogManager.logAction("The phone is normal");
                }
                break;
            case SilenceManager.ACTION_REMOVE_SILENCE_EVENT:
                ItemEventTrigger.notifyListenerOnEndDate();
                break;
        }
    }

}
