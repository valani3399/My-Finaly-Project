package com.example.softices.pankajtrainee.activity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Myservices extends Service {
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer=MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();

    }
}
