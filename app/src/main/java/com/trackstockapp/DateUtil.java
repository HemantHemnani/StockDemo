package com.trackstockapp;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private final static String TAG = DateUtil.class.getSimpleName();
    public static String getCurrentMonth()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        Log.d("Month",dateFormat.format(date));
        return dateFormat.format(date);
    }


    public  static String startDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdate= sdf.format(date);
        Log.e(TAG , "startDate::: "+sdf.format(date));
        return startdate;
    }


    public  static String endDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endDate= sdf.format(date);
        Log.e(TAG , "endDate::: "+sdf.format(date));
        return endDate;
    }

}
