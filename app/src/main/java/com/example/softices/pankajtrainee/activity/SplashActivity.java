package com.example.softices.pankajtrainee.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


public class SplashActivity extends AppCompatActivity {
    private Handler mWaitHandler = new Handler();
    private boolean isLogin;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstscreen);


        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this );
        isLogin = sharedPreferences.getBoolean("is_login",false);

        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isLogin) {
                    Intent intent = new Intent(SplashActivity.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 100);  // Give a 2 seconds delay.

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }

}

