package com.example.kangnamuniv;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsView {
    ArrayList<String> returnArray;

    public DetailsView(String session, int month, int day, Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "http://34.64.49.11/detailsview";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("month", month);
            jsonObject.put("day", day);

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest dayviewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res"); //동일
                    Log.d("testCalendar", res);
                    JSONArray msgArray = response.getJSONArray("msg");

                    // msgArrayList.clear();
                    ArrayList<String> msgArrayList = new ArrayList<>();

                    for (int i = 0; i < msgArray.length(); i++) {
                        JSONArray each = (JSONArray) msgArray.get(i);
                        for (int j = 0; j < each.length(); j++) {
                            msgArrayList.add(String.valueOf(each.get(j)));
                            Log.d("testArray", (String) each.get(j));
                        }
                    }
                    setArrayList(msgArrayList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(dayviewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
    }

    void setArrayList(ArrayList<String> arrayList){
        returnArray = arrayList;
    }
    public ArrayList<String> getMsgArrayList() {
        for(int i = 0; i<returnArray.size(); i++)
            Log.d("testMsgArrayInner", returnArray.get(i));
        Log.d("testReturn","resturn내부");
        return returnArray;
    }
}

