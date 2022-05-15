package com.lotus.kangnamuniv;


import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class changenik extends AppCompatActivity {
    EditText edtnewnik;
    Button btnchgnik1, btnchgnik2;
    public static String Mnickname;
    TextView tvNicknameCheck;
    private boolean nickname_validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_nick);



        edtnewnik = findViewById(R.id.newnick);
        btnchgnik1 = findViewById(R.id.btnNewNickCheck);
        btnchgnik2 = findViewById(R.id.btnChangeNick);
        tvNicknameCheck = findViewById(R.id.tvNickchange);

        btnchgnik1.setOnClickListener(new View.OnClickListener() {
            private AlertDialog dialog;

            @Override
            public void onClick(View view) {
                Mnickname = edtnewnik.getText().toString();
                if (nickname_validate) {
                    return;
                }
                if (Mnickname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(changenik.this);
                    dialog = builder.setMessage("닉네임 입력해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("res");

                            if (result.equals("변경완료")) {
                                tvNicknameCheck.setTextColor(Color.BLUE);
                                tvNicknameCheck.setText("사용가능한 닉네임 입니다.");
                                nickname_validate = true;
                                edtnewnik.setEnabled(false);
                                btnchgnik2.setEnabled(false);
                            } else {
                                tvNicknameCheck.setTextColor(Color.RED);
                                tvNicknameCheck.setText("이미 사용중인 닉네임 입니다.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                NicknameValidateRequest nicknameValidateRequest = new NicknameValidateRequest(Mnickname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(changenik.this);
                queue.add(nicknameValidateRequest);

            }
        });

        btnchgnik2.setOnClickListener(new View.OnClickListener() {
            private AlertDialog dialog;

            @Override
            public void onClick(View view) {
                Mnickname = edtnewnik.getText().toString();



                if (!nickname_validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(changenik.this);
                    dialog = builder.setMessage("닉네임을 확인해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("res");

                            if (result.equals("변경완료")) {

                                return;


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

            }
        });
    }


}
