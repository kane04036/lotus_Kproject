package com.example.kangnamuniv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeNicknameActivity extends AppCompatActivity {
    String newNickname;
    boolean nickname_validate = false;
    EditText edtNewNick;
    Button btnNewNickCheck, btnChageNickname;
    TextView tvNickchange;
    String session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_nick);
        overridePendingTransition(R.anim.none, R.anim.none);


        edtNewNick = findViewById(R.id.newnick);
        btnNewNickCheck = findViewById(R.id.btnNewNickCheck);
        btnChageNickname = findViewById(R.id.btnChangeNick);
        tvNickchange = findViewById(R.id.tvNickchange);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");
        Log.d("test", "Change onCreate session : " + session);

        btnNewNickCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newNickname = edtNewNick.getText().toString();
                Log.d("test", "Change onCreate nickname  : " + newNickname);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("res");

                            if (result.equals("SUCCESS")) {
                                nickname_validate = true;
                                tvNickchange.setTextColor(Color.BLUE);
                                tvNickchange.setText("사용 가능한 닉네임 입니다.");
                                edtNewNick.setEnabled(false);
                                btnNewNickCheck.setEnabled(false);

                            } else {
                                nickname_validate = false;
                                tvNickchange.setTextColor(Color.RED);
                                tvNickchange.setText("이미 사용중인 닉네임 입니다.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                NicknameValidateRequest nicknameValidateRequest = new NicknameValidateRequest(newNickname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(nicknameValidateRequest);
            }
        });

        btnChageNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "onClick: 클릭되엇습니다 ");
                newNickname = edtNewNick.getText().toString();
                Log.d("test", "Change onCreate nickname2  : " + newNickname);

                AlertDialog dialog;

                if (nickname_validate) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    String URL = "http://34.64.49.11/changenk";

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("session", session);
                        jsonObject.put("nickname", edtNewNick.getText().toString());
                        Log.d("test", "Changenk: " + session + " and " + edtNewNick.getText().toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    JsonObjectRequest changeNkRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String res = response.getString("res"); //동일
                                Log.d("test", "onResponse: nickchange" + res);

                                if (res.contains("변경완료")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("nickname", edtNewNick.getText().toString());
                                    editor.commit();

                                    Intent intent = new Intent(getApplicationContext(), FragmentMainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                Log.d("testCalendar", res);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });

                } else if (!nickname_validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeNicknameActivity.this);
                    dialog = builder.setMessage("닉네임 중복체크가 되지 않았습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                } else if (edtNewNick.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeNicknameActivity.this);
                    dialog = builder.setMessage("닉네임 입력해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                } else if (newNickname.length() > 15) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeNicknameActivity.this);
                    dialog = builder.setMessage("닉네임의 최대 길이는 15글자 입니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFinishing()) {
            overridePendingTransition(R.anim.none, R.anim.none);
        }
    }
}
