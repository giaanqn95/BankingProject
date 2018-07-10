package com.example.e7440.bankingproject.components;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.e7440.bankingproject.AppConstants;
import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.module.introduce.IntroduceActivity;

public class SchedulingService extends IntentService {
    private static final int TIME_VIBRATE = 1000;

    public SchedulingService() {
        super(SchedulingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int index = intent.getIntExtra(AppConstants.KEY_TYPE, 0);
        Intent notificationIntent = new Intent(this, IntroduceActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent
                .getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("index = " + index)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(false)
                .setPriority(6)
                .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE,
                        TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE})
                .setContentIntent(contentIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(index, builder.build());
    }
}