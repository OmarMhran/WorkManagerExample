package com.example.myapplication;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Configuration;
import androidx.work.WorkerFactory;

import static com.example.myapplication.Constants.PRAYER_CHANNEL_ID;


public class MyApplication extends Application implements Configuration.Provider {

    public WorkerFactory workerFactory;
    public NotificationManager notificationManager;

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                 .setWorkerFactory(workerFactory)
                 .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);

        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createPrayerTimesNotificationChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createPrayerTimesNotificationChannel() {
        NotificationChannel mChannel = new NotificationChannel(
                "prayer_channel",
                "prayer_times_channel",
                NotificationManager.IMPORTANCE_HIGH
        );

        mChannel.setDescription("prayer times notification channel");
        mChannel.enableLights(true);
        mChannel.setLightColor(getColor(R.color.purple_700));
        mChannel.enableVibration(true);
        mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        mChannel.setShowBadge(false);

        notificationManager.createNotificationChannel(mChannel);
    }
}
