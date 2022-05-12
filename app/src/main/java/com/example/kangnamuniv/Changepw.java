package com.example.kangnamuniv;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Changepw {
    boolean result;
    Changepw(String session, String newPw, Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "http://34.64.49.11/changepw";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("password", newPw);

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest changeNkRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res"); //동일


                    if (res.contains("변경완료"))
                        result = true;
                    else
                        result = false;
                    Log.d("testChangePw", res);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(changeNkRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
    }
    boolean getResult(){
        return true;
    }
}
