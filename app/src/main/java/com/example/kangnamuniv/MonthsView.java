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

public class MonthsView {

    public MonthsView(String session, int month, Context context) {

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
                    Log.d("testCalendar", res);
                    //String ds = response.getString("ds");
                    //Log.d("testCalendar", ds);

                    JSONArray dateArray = response.getJSONArray("ds");
                    for (int i = 0; i < dateArray.length(); i++) {
                        JSONArray each = (JSONArray) dateArray.get(i);
                        for (int j = 0; j < each.length(); j++)
                            Log.d("testArray", String.valueOf(each.get(j)));
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
}
