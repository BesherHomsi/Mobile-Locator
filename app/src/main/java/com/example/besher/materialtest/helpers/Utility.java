package com.example.besher.materialtest.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.besher.materialtest.R;

/**
 * Created by Besher on 03/06/18.
 */

public class Utility {

    public static boolean permissionsGranted(int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }

        }
        return true;
    }

    public static void makeNotification(Context c, String messageBody, Bundle extra, String clickAction, String tag) {

        Intent intent = new Intent();
        PendingIntent pendingIntent = null;
        if (clickAction != null) {
            intent.setAction(clickAction);
            intent.putExtras(extra);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(c, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(c)
                        .setSmallIcon(R.mipmap.magnifying)
                        .setContentTitle(c.getResources().getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true);
        if (pendingIntent != null) {

            notificationBuilder.setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(tag, 0, notificationBuilder.build());
    }


}
