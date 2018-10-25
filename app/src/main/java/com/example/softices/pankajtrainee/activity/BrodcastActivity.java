package com.example.softices.pankajtrainee.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;

public class BrodcastActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brodcast);
        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {

            int leval=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);

            int plug=intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);

            if(plug==1 || plug ==2){

            }else if(leval>50){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(BrodcastActivity.this)
                            .setSmallIcon(R.drawable.ic_account_box_black_24dp)
                            .setContentTitle("Bettry leval in form")
                            .setContentText("Your battry leval less 50% plase plagin your mobail");

                    Intent notificationIntent = new Intent(String.valueOf(BrodcastActivity.this));
                    PendingIntent contentIntent = PendingIntent.getActivity(BrodcastActivity.this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());
            }else {
                Toast.makeText(context, "PLUGIN", Toast.LENGTH_SHORT).show();
            }



        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
