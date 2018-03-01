package com.example.besher.materialtest.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Virtualizer;
import android.provider.Contacts;
import android.provider.Settings;
import android.widget.Toast;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.helpers.SilenceManager;
import com.example.besher.materialtest.models.SilenceItem;


/**
 * Created by Besher on 02/23/18.
 */

public class SilenceTask extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        AudioManager audioManager = (AudioManager) ControllerApplication.getInstance()
                .getSystemService(ControllerApplication.getInstance()
                        .AUDIO_SERVICE);
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case SilenceManager.ACTION_SET_SILENCE:
                    //SilenceItem silenceItem = (SilenceItem) intent.getExtras().getSerializable("item");
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    break;
                case SilenceManager.ACTION_REMOVE_SILENCE:
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
            }
        }


/*
        MediaPlayer mediaPlayer = MediaPlayer.create(ControllerApplication.getInstance(),
                Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();*/
    }

}
