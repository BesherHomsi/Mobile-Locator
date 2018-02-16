package com.example.besher.materialtest.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.besher.materialtest.ControlerApplication;
import com.example.besher.materialtest.R;

public class SplashActivity extends AppCompatActivity {

    public final static String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        },2000);



    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "i'm resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.v(TAG, "i'm paused");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
