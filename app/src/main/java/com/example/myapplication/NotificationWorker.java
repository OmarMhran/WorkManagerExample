package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;

public class NotificationWorker extends Worker {

    public static final String TAG ="app_worker";


    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int prayingNum = getInputData().getInt(AppWorker.PRAYER_NUM, 0);

        Log.d(TAG, "doWork: ");

        displayPrayerNotification(prayingNum);
        return Result.success();
    }

    private void displayPrayerNotification(int prayingNum) {
        String prayingName = "";

        switch (prayingNum) {
            case 1: prayingName = getApplicationContext().getString(R.string.fajr_prayer);
                break;
            case 2: prayingName = getApplicationContext().getString(R.string.sunrize_prayer);
                break;
            case 3: Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);
                    if (day == Calendar.FRIDAY) {
                        prayingName = getApplicationContext().getString(R.string.jummah_prayer);
                    } else{
                        prayingName = getApplicationContext().getString(R.string.zuhr_prayer);
                    }
                break;
            case 4: prayingName = getApplicationContext().getString(R.string.asr_prayer);
                break;
            case 5: prayingName = getApplicationContext().getString(R.string.maghreb_prayer);
                break;
            case 6 : prayingName = getApplicationContext().getString(R.string.ishaa_prayer);
                break;
        }

        Log.d(TAG, "displayPrayerNotification: $prayingName");
        PendingIntent intent = PendingIntent.getActivity(
                getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "prayer_channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentText(prayingName)
                        .setContentTitle("test")
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setLights(-0xff0100, 1000, 1000)
                        .setAutoCancel(true)
                        .setContentIntent(intent);
        NotificationManager notificationManager = (NotificationManager)
               getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(prayingNum, builder.build());

    }
}
