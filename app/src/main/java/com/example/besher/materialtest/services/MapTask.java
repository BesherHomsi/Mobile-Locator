package com.example.besher.materialtest.services;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.besher.materialtest.ControllerApplication;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
/**
 * Created by Besher on 02/23/18.
 */

public class MapTask extends GcmTaskService {

    @Override
    public int onRunTask(TaskParams taskParams) {

        Log.v("sdfsdf","sdfdfsf");

        return GcmNetworkManager.RESULT_SUCCESS;
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
    }
}
