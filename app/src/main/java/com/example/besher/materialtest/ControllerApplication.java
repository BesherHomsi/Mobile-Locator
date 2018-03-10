package com.example.besher.materialtest;

import android.app.Application;

import com.example.besher.materialtest.helpers.Constant;
import com.example.besher.materialtest.services.MapTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

/**
 * Created by Besher on 02/02/18.
 */

public class ControllerApplication extends Application {

    private static ControllerApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(this);
        if (code == ConnectionResult.SUCCESS) {
            PeriodicTask task = new PeriodicTask.Builder()
                    .setService(MapTask.class)
                    .setTag(Constant.CHECK_LOCATION_TASK)
                    .setPeriod(900L)
                    .setFlex(5L)
                    .setPersisted(true)
                    .setUpdateCurrent(true)
                    .build();

            GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(this);
            mGcmNetworkManager.schedule(task);
        }
    }

    public static ControllerApplication getInstance() {
        return instance;
    }
}
