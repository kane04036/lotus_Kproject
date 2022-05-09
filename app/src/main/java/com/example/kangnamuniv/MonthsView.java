package com.example.kangnamuniv;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MonthsView {
    ArrayList<CalendarDay> date = new ArrayList<>();

    public MonthsView(String session, int year,  int month, Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "http://34.64.49.11/monthsview";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("month", month);

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest monthviewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res"); //동일
                    Log.d("testCalendarRes", res);
                    String ds = response.getString("day");
                    Log.d("testDay", ds);
                    JSONArray dateArray = response.getJSONArray("day");
                    date.clear();
                    for(int i = 0; i< dateArray.length(); i++){
                        JSONArray each = dateArray.getJSONArray(i);
                        //Log.d("eachValueTest", (String) each.get(0) + "<<End");
                        CalendarDay day = CalendarDay.from(year, month, (Integer) each.get(0));
                        //Log.d( "testDateinDetailview", each.get(0) + "and" + year + "and" + month + "<<End");

                       // Log.d("testCalArray", String.valueOf(day));
                        date.add(day);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(monthviewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
    }
    public ArrayList<CalendarDay> getDate() {
        return date;
    }
}
