package com.example.softices.pankajtrainee.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;

public class BckgroundWebServicesActivity extends AppCompatActivity {
    Button btnstart,btnstop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bckground_web_services);
        btnstart=(Button)findViewById(R.id.btn_sarvicesstart);
        btnstop=(Button)findViewById(R.id.btn_srvicesstop);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(getApplicationContext(), Myservices.class));
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(),Myservices.class));
            }
        });
    }


}
