package com.example.kangnamuniv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WriteActivity extends AppCompatActivity {
    String session;
    int Lnumber;
    int anonymous;
    String title, msg, lecture;
    EditText edtTitle, edtMsg;
    CheckBox chkAnonymousWrite;
    Button btnPost, btnBack;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        edtTitle = findViewById(R.id.edtTitle);
        edtMsg = findViewById(R.id.edtMsg);
        chkAnonymousWrite = findViewById(R.id.chkAnonymousWrite);
        btnPost = findViewById(R.id.btnPost);
        btnBack = findViewById(R.id.btnBack);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");
        Lnumber = getIntent().getIntExtra("Lnumber",0);
        lecture = getIntent().getStringExtra("Lecture");

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = edtTitle.getText().toString();
                msg = edtMsg.getText().toString();
                if(chkAnonymousWrite.isChecked())
                    anonymous = 1;
                else
                    anonymous = 0;


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("session", session);
                    jsonObject.put("Lnumber", Lnumber);
                    jsonObject.put("anonymous",anonymous);
                    jsonObject.put("title",title);
                    jsonObject.put("msg",msg);
                    Log.d("test7", session);
                    Log.d("test7", String.valueOf(Lnumber));
                    Log.d("test7", String.valueOf(anonymous));
                    Log.d("test7", title);
                    Log.d("test7", msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                String URL = "http://34.64.49.11/boardwrite";//각 상황에 맞는 서버 url


                JsonObjectRequest boardWriteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int Bnumber = response.getInt("seq");
                            String res = response.getString("res");


                            Log.d("testResult", res);
                            Log.d("testBnumber",String.valueOf(Bnumber));
                           // Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();

                            if(res.contains("Success")){
                                Log.d("testResult","작성완료");
                                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                                intent.putExtra("Bnumber",Bnumber);
                                intent.putExtra("Lecture", lecture);
                                startActivity(intent);
                                finish();
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

                requestQueue.add(boardWriteRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
            }
        });


    }

}
