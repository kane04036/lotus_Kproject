package com.lotus.kangnamuniv;

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
import java.util.Base64;

public class TTSrequest {
    boolean result;
    TTSrequest(int seq, Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "http://34.64.49.11/scheduledelete";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("seq", seq);

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest scheduleWriteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res"); //동일
                    String incoding = response.getString("incodidng");


                    if(res.contains("SUCCESS")){
                        result = true;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            byte[] binary = Base64.getDecoder().decode(incoding);
                        }else{
                            Log.d("test", "onResponse: 버전이 맞지 않아 해당 기능 사용이 불가능 합니다.");
                        }

                    }
                    else
                        result = false;





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(scheduleWriteRequest);
    }
}
