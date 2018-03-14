package com.example.besher.materialtest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.besher.materialtest.ControllerApplication;
import com.example.besher.materialtest.helpers.MyContactManager;
import com.example.besher.materialtest.helpers.SilenceManager;
import com.example.besher.materialtest.models.MyContact;
import com.example.besher.materialtest.models.SilenceItem;

import java.util.ArrayList;

/**
 * Created by Besher on 03/04/18.
 */

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(lister, PhoneStateListener.LISTEN_CALL_STATE);

    }




    private static PhoneStateListener lister = new PhoneStateListener() {

        public final static String KEY_SAVED_CONTACTS_LIST = "saved_contacts_list";

        @Override
        public void onCallStateChanged(int state, String Number) {
            super.onCallStateChanged(state, Number);
            AudioManager audiomanager = (AudioManager) ControllerApplication.getInstance().getSystemService(Context.AUDIO_SERVICE);
            ArrayList<MyContact> savedContacts = MyContactManager.getListFromPref(ControllerApplication.getInstance(),
                    KEY_SAVED_CONTACTS_LIST);
            ArrayList<String> numbers = new ArrayList<>();
            for( int i = 0; i < savedContacts.size(); i++) {

                String[] temp = savedContacts.get(i).getNumber().split(",");
                for (String item :
                     temp) {
                    numbers.add(item);
                }
            }

            switch (state){
                case TelephonyManager.CALL_STATE_RINGING:

                    if (numbers.contains(Number)) {
                        // to make Ring state normal
                        if(SilenceManager.checkIfEventISActiveWithLocation(ControllerApplication.getInstance()))
                            audiomanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    audiomanager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    break;
            }
        }
    };

}
