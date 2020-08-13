package com.superboringservicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.superboringservicedemo.App.CHANNEL_ID;

public class BoringService extends Service {

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.sunshine);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();

        Intent notifIntent = new Intent(this, MainActivity.class);
        PendingIntent pI = PendingIntent.getActivity(
                this,
                21,
                notifIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Music Service")
                .setSmallIcon(R.drawable.ic_boring_name)
                .setContentIntent(pI)
                .build();

        startForeground(1,notification);
        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
