package com.superboringservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Chronometer;

public class TimerBService extends Service {

    IBinder binder = new TimeBinder();
    private Chronometer chronometer;

    @Override
    public void onCreate() {
        super.onCreate();
        chronometer = new Chronometer(this);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chronometer.stop();
    }

    public String getTimestamp() {
        long realtime = SystemClock.elapsedRealtime() - chronometer.getBase();

        int hrs = (int) (realtime / 3600000);
        int min = (int) (realtime - hrs * 3600000) / 60000;
        int sec = (int) ((realtime - hrs * 3600000 - min * 60000) / 1000);
        int ms = (int) (realtime - hrs * 3600000 - min * 60000 - sec * 1000);

        return hrs + ":" + min + ":" + sec + ":" + ms;
    }

    private class TimeBinder extends Binder {
        TimerBService getService() {
            return TimerBService.this;
        }
    }
}