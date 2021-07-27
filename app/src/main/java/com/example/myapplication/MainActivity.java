package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Build;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAppWorker();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initAppWorker() {
        PeriodicWorkRequest work = new PeriodicWorkRequest.Builder(AppWorker.class,1,TimeUnit.DAYS).build();
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork("UniqueName", ExistingPeriodicWorkPolicy.KEEP,work);
    }
}