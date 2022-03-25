package com.example.kangnamuniv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

public class BoardActivity extends AppCompatActivity {
    TextView tvLectureName, boardResult;
    Button btnWrite;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        setTitle("글쓰기");

        tvLectureName = findViewById(R.id.tvLectureName);
        boardResult = findViewById(R.id.boardResult);
        btnWrite = findViewById(R.id.btnWrite);

        String res = getIntent().getStringExtra("res");
        String data = getIntent().getStringExtra("data");
        String lecture = getIntent().getStringExtra("lecture");


        tvLectureName.setText(lecture);
        boardResult.append(res);
        boardResult.append(data);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });

        //json으로 post 요청 보내기

       /* JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("session", key);
            requestJsonObject.put("Lnumber", seq);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(BoardActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://34.64.49.11/mainview", requestJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonResponse = new JSONObject();

                try{
                    String res = jsonResponse.getString("res");
                    String data = jsonResponse.getString("data");

                    boardResult.setText(res);
                    boardResult.append(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);*/

      /*  Response.Listener<JSONObject> jsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonResponse = new JSONObject();

                try{
                    String res = jsonResponse.getString("res");
                    String data = jsonResponse.getString("data");

                    boardResult.setText(res);
                    boardResult.append(data);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        MainViewRequest mainViewRequest = new MainViewRequest(key, seq, jsonObjectListener);*/

    }
}
