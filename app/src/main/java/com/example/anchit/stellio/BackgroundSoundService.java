package com.example.anchit.stellio;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class BackgroundSoundService extends Service {

    private static final String TAG = null;
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service", "onCreate");
        player = new MediaPlayer();
        try {
            player.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/your file.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "onStartCommand");
        try {
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }
}
