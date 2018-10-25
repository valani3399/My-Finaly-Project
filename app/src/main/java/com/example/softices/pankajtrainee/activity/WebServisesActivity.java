package com.example.softices.pankajtrainee.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.softices.pankajtrainee.R;


public class WebServisesActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnpost,btnput,btndelet,btnget,btnnotification,btnbattry,btncon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_servises);

        btnget=(Button)findViewById(R.id.btn_get);
        btnpost=(Button)findViewById(R.id.btn_post);
        btnput=(Button)findViewById(R.id.btn_put);
        btnnotification=(Button)findViewById(R.id.btn_notification);
        btndelet=(Button)findViewById(R.id.btn_delet_web_services);
        btncon=(Button)findViewById(R.id.btn_contectweb);
        btnbattry=(Button)findViewById(R.id.btn_notificationbettryweb);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("WEBSARVICES");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(WebServisesActivity.this, Dashboard.class);
                startActivity(f);
                finish();
            }
        });
        btnget.setOnClickListener(this);
        btnpost.setOnClickListener(this);
        btnput.setOnClickListener(this);
        btndelet.setOnClickListener(this);
        btnnotification.setOnClickListener(this);
        btnbattry.setOnClickListener(this);
        btncon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_get:
                Intent f = new Intent(WebServisesActivity.this, GetActivity.class);
                startActivity(f);
                break;
            case R.id.btn_post:
                Intent s = new Intent(WebServisesActivity.this, PostActivity.class);
                startActivity(s);
                break;

            case R.id.btn_put:
                Intent a = new Intent(WebServisesActivity.this, PutActivity.class);
                startActivity(a);
                break;
            case R.id.btn_delet_web_services:
                Intent d = new Intent(WebServisesActivity.this, DeletActivity.class);
                startActivity(d);
                break;
            case R.id.btn_notification:
                addNotification();
            case R.id.btn_notificationbettryweb:

                Intent brodcast = new Intent(WebServisesActivity.this, BrodcastActivity.class);
                startActivity(brodcast);
            case R.id.btn_contectweb:
                Intent con = new Intent(WebServisesActivity.this, ContentproviderActivity.class);
                startActivity(con);
        }
    }

    private void addNotification() {

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this)
                                        .setSmallIcon(R.drawable.ic_arrow_back_black_24dp)
                                        .setContentTitle("Heloo I Am Pankja")
                                         .setContentText("You Are Android Devloper");
        Intent notificationIntent = new Intent(this, WebServisesActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
