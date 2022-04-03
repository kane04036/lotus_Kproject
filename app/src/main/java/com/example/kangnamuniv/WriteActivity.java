package com.example.kangnamuniv;

import android.content.DialogInterface;
import android.content.Intent;
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
    String session, Lnumber;
    int anonymous = 1;
    String title, msg;
    EditText edtTitle, edtMsg;
    CheckBox chkAnonymousWrite;
    Button btnPost, btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        edtTitle = findViewById(R.id.edtTitle);
        edtMsg = findViewById(R.id.edtMsg);
        chkAnonymousWrite = findViewById(R.id.chkAnonymousWrite);
        btnPost = findViewById(R.id.btnPost);
        btnBack = findViewById(R.id.btnBack);

        session = getIntent().getStringExtra("session");
        Lnumber = getIntent().getStringExtra("Lnumber");

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

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("test7", session);
                Log.d("test7", Lnumber);
                Log.d("test7", String.valueOf(anonymous));
                Log.d("test7", title);
                Log.d("test7", msg);

                String URL = "http://34.64.49.11/boardwrite";//각 상황에 맞는 서버 url


                JsonObjectRequest boardWriteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Bnumber = response.getString("seq");
                            String res = response.getString("res");


                            Log.d("testResult", res);
                            Log.d("testBnumber",Bnumber);
                            Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();

                            if(res.contains("Success")){
                                Log.d("testResult","작성완료");
                                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                                intent.putExtra("Bnumber",Bnumber);
                                startActivity(intent);
                                finish();
                            }

                            Log.d("test7", session);
                            Log.d("test7", Lnumber);
                            Log.d("test7", String.valueOf(anonymous));
                            Log.d("test7", title);
                            Log.d("test7", msg);

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
