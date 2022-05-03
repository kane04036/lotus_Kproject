package com.example.kangnamuniv;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleWrite {
    String res;
    boolean result;

    public ScheduleWrite(String session, int month, int day, String msg, Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "http://34.64.49.11/schedulewrite";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("month", month);
            jsonObject.put("day", day);
            jsonObject.put("msg", msg);
            Log.d("testWrite", session + String.valueOf(month) + String.valueOf(day) + msg);

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest scheduleWriteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    res = response.getString("res"); //동일
                    if(res.contains("SUCCESS"))
                        result = true;
                    else
                        result = false;
                    Log.d("testCalendar", res);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(scheduleWriteRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
    }
    boolean getResult(){
        return result;
    }
}
