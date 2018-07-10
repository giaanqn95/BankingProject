package com.example.e7440.bankingproject.components;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.e7440.bankingproject.AppConstants;

import java.util.Calendar;

import static com.example.e7440.bankingproject.module.introduce.IntroduceActivity.mHour;
import static com.example.e7440.bankingproject.module.introduce.IntroduceActivity.mMinute;
import static com.example.e7440.bankingproject.module.introduce.IntroduceActivity.mSecond;

public class AlarmUtils {
    private static int INDEX = 1;

    public static void create(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SchedulingService.class);
        intent.putExtra(AppConstants.KEY_TYPE, INDEX);
        PendingIntent pendingIntent = PendingIntent.getService(context, INDEX, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        INDEX++;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, mHour);
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.SECOND, mSecond);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}