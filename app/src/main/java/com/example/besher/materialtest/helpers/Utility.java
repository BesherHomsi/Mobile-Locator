package com.example.besher.materialtest.helpers;

import android.content.pm.PackageManager;

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
}
