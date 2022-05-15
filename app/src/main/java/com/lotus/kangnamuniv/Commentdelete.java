package com.lotus.kangnamuniv;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Commentdelete {
    boolean result;
    Commentdelete(int seq, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        String session = sharedPreferences.getString("session", "");

        RequestQueue requestQueueCommentDel = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("Cnumber", seq);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String URL = "http://34.64.49.11/commentdelete";//각 상황에 맞는 서버 url


        JsonObjectRequest boardViewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res");
                    Log.d("test", "onResponse: "+ res);
                    if (res.contains("Success")) {
                        Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        result = true;
//                        CnumberAry.remove(position);
//                        cmtMsgAry.remove(position);
//                        cmtWritersAry.remove(position);
//                        cmtArray.remove(position);
//                        customArrayAdapterComment.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "댓글 작성자만 삭제할 수 있습니다", Toast.LENGTH_SHORT).show();
                        result = false;
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


        requestQueueCommentDel.add(boardViewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
    }
    boolean getResult(){
        return result;
    }
}
