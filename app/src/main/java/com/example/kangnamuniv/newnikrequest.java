package com.example.kangnamuniv;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class newnikrequest extends StringRequest {
               RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

               String URL = "http://34.64.49.11/changenk";


               JsonObjectRequest newnikRequest = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
                  @Override
                       try {
                           String res = response.getString("res"); //동일
                           Log.d("testLogout", res);
                          if (res.contains("변경완료")) {

                          }


                          e.printStackTrace();
                        }

                   }
                }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                   }
               });

               requestQueue.add(newnikRequest);


}