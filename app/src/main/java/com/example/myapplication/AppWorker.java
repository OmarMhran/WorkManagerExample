package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.*;

import java.util.*;
import java.util.concurrent.TimeUnit;



public class AppWorker extends Worker {

    public static final String PRAYER_NUM ="prayer_num";
    public static final String TAG ="app_worker";

    public AppWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

            getPrayerTimes();

        return Result.success();
    }

    private void getPrayerTimes() {
        //هنا حنجيب المواقيت

        String prayer = "8:10";

        scheduleNotification(prayer);
    }

    private void scheduleNotification(String prayer)  {

        Data data = new Data.Builder()
                .putInt(PRAYER_NUM,1).build();

        long currentTime = System.currentTimeMillis();

//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        Date date = null;
//        try {
//            date = sdf.parse(prayer);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        int hour = date.getHours();
//        int min = date.getMinutes();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 55);
        cal.set(Calendar.SECOND, 0);




        long customTime = cal.getTimeInMillis();

        if(customTime > currentTime){
            long delay = customTime - currentTime;

            OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                        .setInputData(data)
                        .build();

            WorkManager workManager = WorkManager.getInstance(getApplicationContext());
            workManager.enqueue(work);
        }else {
            Log.d(TAG, "scheduleNotification: current is greater than custom");
        }

    }
}
