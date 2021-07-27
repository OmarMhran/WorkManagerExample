package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class TimeChangedReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_DATE_CHANGED.equals(intent.getAction()) || Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
            PeriodicWorkRequest work = new PeriodicWorkRequest.Builder(AppWorker.class,1, TimeUnit.DAYS).build();
            WorkManager workManager = WorkManager.getInstance(context);
            workManager.enqueue(work);
        }
    }
}
