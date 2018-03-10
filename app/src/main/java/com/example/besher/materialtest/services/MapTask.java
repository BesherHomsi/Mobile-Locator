package com.example.besher.materialtest.services;

import android.media.AudioManager;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.helpers.SilenceManager;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

/**
 * Created by Besher on 02/23/18.
 */

public class MapTask extends GcmTaskService {

    @Override
    public int onRunTask(TaskParams taskParams) {
        AudioManager audioManager = (AudioManager) ControllerApplication.getInstance()
                .getSystemService(ControllerApplication.getInstance()
                        .AUDIO_SERVICE);
        if (SilenceManager.checkIfEventISActiveWithLocation(this)) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        } else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
    }
}
