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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailsView {
    ArrayList<String> returnArray;
    public ArrayList<TodoView> msgArrayList = new ArrayList<>();
    public ArrayList<Integer> todoSeqArray = new ArrayList<>();


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
                    Log.d("testCalendarResult", res);
                    JSONArray msgArray = response.getJSONArray("msg");
                    JSONArray seqArray = response.getJSONArray("seq");
                    String seq = response.getString("seq");
                    Log.d("testSeq", seq);

                    msgArrayList.clear();
                    todoSeqArray.clear();

                    for (int i = 0; i < msgArray.length(); i++) {
                        JSONArray each = (JSONArray) msgArray.get(i);
                        for (int j = 0; j < each.length(); j++) {
                            msgArrayList.add(new TodoView(String.valueOf(each.get(j))));
                        }
                    }
                    for (int i = 0; i < seqArray.length(); i++) {
                        JSONArray each = (JSONArray) seqArray.get(i);
                        for (int j = 0; j < each.length(); j++) {
                            todoSeqArray.add((Integer) each.get(j));
                        }
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

        requestQueue.add(dayviewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
    }

    public ArrayList<TodoView> getMsgArrayList() {
        Log.d("test", "getMsgArrayList: " + "return Arraylist");
        return msgArrayList;
    }
    public ArrayList<Integer> getTodoSeqArray() {
        return todoSeqArray;
    }
}

