package com.chanifq.gardenIOT;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    ProgressDialog progressDialog;

    Handler delayHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        delayHandler= new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },2000L);

    }


}
