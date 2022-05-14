//package com.example.kangnamuniv;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.prolificinteractive.materialcalendarview.CalendarDay;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class MonthsView2 extends JsonObjectRequest {
//
//    final static private String URL = "http://34.64.49.11/monthsview";
//    private static final JSONObject jsonObject;
//
//    public MonthsView2(String session, int month, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) throws JSONException {
//        super(Method.POST, URL, jsonObject, listener, null);
//
//        jsonObject = new JSONObject();
//        jsonObject.put("session", session);
//        jsonObject.put("month", month);
//    }
//}
