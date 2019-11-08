package com.example.medicationadherence.background;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.medicationadherence.data.Repository;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class UpdateLogsWorker extends Worker {
    public UpdateLogsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        Repository repository = (Repository) getInputData().getKeyValueMap().get("repository");
        Calendar curr = Calendar.getInstance();
        Calendar first = Calendar.getInstance();
        first.clear();
        first.set(Calendar.YEAR, curr.get(Calendar.YEAR));
        first.set(Calendar.MONTH, curr.get(Calendar.MONTH));
        first.set(Calendar.DAY_OF_YEAR, curr.get(Calendar.DAY_OF_YEAR));
        if (first.getTimeInMillis() < curr.getTimeInMillis())
            first.add(Calendar.DAY_OF_YEAR, 1);
        long initialDelay = first.getTimeInMillis() - curr.getTimeInMillis();
        Data repoData = new Data.Builder().put("repository", repository).build();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UpdateLogsWorker.class).setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .setInputData(repoData).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
        return null;
    }
}
