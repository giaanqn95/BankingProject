package com.example.e7440.bankingproject.components;

import android.util.Log;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by E7440 on 21/12/2017.
 */

public class TimeHelper {
    public static final String DATE_NORMAL_FORMAT_HISTORY = "yyyy-MM-dd hh:mm:ss";

    public static String convertDate(String strDate) {
        String mDate = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_NORMAL_FORMAT_HISTORY);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            Date date = simpleDateFormat.parse(strDate);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
            df.setTimeZone(timeZone);
            mDate = df.format(date);
            Log.d("TIME", mDate);
        } catch (Exception e) {
        }
        return mDate;
    }

    public static String convertDateAndTime(String strDate) {
        String formattedDate = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = simpleDateFormat.parse(strDate);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm dd-MM-yyyy");
            formattedDate = simpleDateFormat2.format(date);
        } catch (Exception e) {
        }
        return formattedDate;
    }

//    public static String convertDateAndTime(String strDate) {
//        String mDate = "";
//        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_NORMAL_FORMAT_HISTORY);
//            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
//            Date date = simpleDateFormat.parse(strDate);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//
//            SimpleDateFormat simpleDateFormat1 =
//                    new SimpleDateFormat("HH:mm dd/MM/yyyy");
//            simpleDateFormat1.setTimeZone(TimeZone.getTimeZone("GMT+7"));
//            mDate = simpleDateFormat1.format(calendar.getTime());
//
////            DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
////
////            df.setTimeZone(timeZone);
////            mDate = df.format(date);
////            Log.d("TIME", mDate);
//        } catch (Exception e) {
//        }
//        return mDate;
//    }

    public static String convertResultCalendarToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // calendar.setTimeZone(timeZone);

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        simpleDateFormat.setTimeZone(timeZone);
        Log.d("TIME", simpleDateFormat.format(calendar.getTime()));
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String convertResultCalendarToDate(int year, int month, int day, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hourOfDay, minute, 0);
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        // calendar.setTimeZone(timeZone);

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        simpleDateFormat.setTimeZone(timeZone);
        Log.d("TIME", simpleDateFormat.format(calendar.getTime()));
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String convertResultCalendarBirthday(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        //  calendar.setTimeZone(timeZone);

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        simpleDateFormat.setTimeZone(timeZone);

        Log.d("TIME", simpleDateFormat.format(calendar.getTime()));
        return simpleDateFormat.format(calendar.getTime());
    }

    public static boolean isStartLargeThanEnd(String timeStart, String end) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return simpleDateFormat.parse(timeStart).before(simpleDateFormat.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String formatDateAndTimeToShow(int hourOfDay, int minute, int day, int month, int year) {
        return String.format("%d:%d - %d/%d/%d ", hourOfDay, minute, day, month, year);
    }

    public static String formatDateAndTimeToShow(int day, int month, int year) {
        return String.format("%d/%d/%d ",day, month, year);
    }

    public static int getMonth(String strDate) {
        int mMonth = -1;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_NORMAL_FORMAT_HISTORY);
            Date date = simpleDateFormat.parse(strDate);
            DateFormat df = new SimpleDateFormat("MM");
            mMonth = Integer.parseInt(df.format(date));
            Log.d("MONTH", "" + mMonth);
        } catch (Exception e) {
        }
        return mMonth;
    }

    public static int getWeekDay(String strDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_NORMAL_FORMAT_HISTORY);
        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public static String getDayInWeek(int weekDay) {
        Log.d("WEEK", ""+weekDay);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, weekDay);
        calendar.setMinimalDaysInFirstWeek(4);
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        //calendar.setTimeZone(timeZone);

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd-MM-yyyy" );
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String convertMoney(int price) {
        if (price >= 0) {
            return NumberFormat.getNumberInstance(Locale.GERMANY).format(price);
        } else {
            return NumberFormat.getNumberInstance(Locale.GERMANY).format(price * -1);
        }
    }
}
