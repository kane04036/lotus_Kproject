package com.example.kangnamuniv;

import android.util.Log;

public class DateParsing {
    int year, month, day;
    String fullDate;
    public DateParsing(String Date){
        this.fullDate = Date.replaceAll("CalendarDay", "");
        fullDate = fullDate.replace("{","");
        fullDate = fullDate.replace("}", "");
        Log.d("testParsing", fullDate);

        String[] dateList = fullDate.split("-");

        year = Integer.valueOf(dateList[0]);
        month = Integer.valueOf(dateList[1]);
        day = Integer.valueOf(dateList[2]);

        Log.d("testYear", String.valueOf(year));
        Log.d("testMonth", String.valueOf(month));
        Log.d("testDay", String.valueOf(day));

    }

    int getYear(){
        return year;
    }
    int getMonth(){
        return month;
    }
    int getDay(){
        return day;
    }
}
