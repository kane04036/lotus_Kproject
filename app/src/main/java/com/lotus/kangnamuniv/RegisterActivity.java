package com.lotus.kangnamuniv;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class RegisterActivity extends AppCompatActivity {

    EditText edtRegID, edtRegPW, edtRegPWcheck, edtRegNickname;
    Button btnRegister, btnIdCheck, btnNicknameCheck;
    public static String Rid, Rpassword, Rnickname;
    TextView resultTv, tvIdCheck, tvNicknameCheck, tvPwCheck;
    private boolean id_validate = false;
    private boolean nickname_validate = false;
    private boolean pw_check = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.none, R.anim.none);


        edtRegID = findViewById(R.id.edtRegID);
        edtRegPW = findViewById(R.id.edtRegPW);
        edtRegPWcheck = findViewById(R.id.edtRegPWcheck);
        edtRegNickname = findViewById(R.id.edtRegNickname);
        btnRegister = findViewById(R.id.btnRegister);
        btnIdCheck = findViewById(R.id.btnIdCheck);
        btnNicknameCheck = findViewById(R.id.btnNicknameCheck);
        tvIdCheck = findViewById(R.id.tvIdCheck);
        tvNicknameCheck = findViewById(R.id.tvNicknameCheck);
        tvPwCheck = findViewById(R.id.tvPwCheck);

        btnIdCheck.setOnClickListener(new View.OnClickListener() {
            private AlertDialog dialog;

            @Override
            public void onClick(View view) {
                Rid = edtRegID.getText().toString();

                if (id_validate) {
                    return;
                }
                if (Rid.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("res");

                            if (result.equals("SUCCESS")) {
                                tvIdCheck.setTextColor(Color.BLUE);
                                tvIdCheck.setText("사용가능한 아이디입니다.");
                                id_validate = true;
                                edtRegID.setEnabled(false);
                                btnIdCheck.setEnabled(false);
                            } else {
                                tvIdCheck.setTextColor(Color.RED);
                                tvIdCheck.setText("이미 사용중인 아이디 입니다.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                IDValidateRequest idValidateRequest = new IDValidateRequest(Rid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(idValidateRequest);
            }
        });

        edtRegPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((edtRegPW.getText().toString()).equals(edtRegPWcheck.getText().toString())) {
                    tvPwCheck.setTextColor(Color.BLUE);
                    tvPwCheck.setText("비밀번호가 일치합니다");
                    pw_check = true;
                } else {
                    tvPwCheck.setTextColor(Color.RED);
                    tvPwCheck.setText("비밀번호가 일치하지 않습니다.");
                    pw_check = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtRegPWcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((edtRegPW.getText().toString()).equals(edtRegPWcheck.getText().toString())) {
                    tvPwCheck.setTextColor(Color.BLUE);
                    tvPwCheck.setText("비밀번호가 일치합니다");
                    pw_check = true;
                } else {
                    tvPwCheck.setTextColor(Color.RED);
                    tvPwCheck.setText("비밀번호가 일치하지 않습니다.");
                    pw_check = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnNicknameCheck.setOnClickListener(new View.OnClickListener() {
            private AlertDialog dialog;

            @Override
            public void onClick(View view) {
                Rnickname = edtRegNickname.getText().toString();
                if (nickname_validate) {
                    return;
                }
                if (Rnickname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임 입력해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if(Rnickname.length() > 15){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임의 최대 길이는 15글자 입니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("res");

                            if (result.equals("SUCCESS")) {
                                tvNicknameCheck.setTextColor(Color.BLUE);
                                tvNicknameCheck.setText("사용가능한 닉네임 입니다.");
                                nickname_validate = true;
                                edtRegNickname.setEnabled(false);
                                btnNicknameCheck.setEnabled(false);

                            } else {
                                tvNicknameCheck.setTextColor(Color.RED);
                                tvNicknameCheck.setText("이미 사용중인 닉네임 입니다.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                NicknameValidateRequest nicknameValidateRequest = new NicknameValidateRequest(Rnickname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(nicknameValidateRequest);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            private AlertDialog dialog;

            @Override
            public void onClick(View view) {
                Rid = edtRegID.getText().toString();
                Rpassword = edtRegPWcheck.getText().toString();
                Rnickname = edtRegNickname.getText().toString();


                if (!id_validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 확인해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (!pw_check) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호를 확인해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;

                }

                if (!nickname_validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임을 확인해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if(Rpassword.length() > 20){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호의 최대 길이는 20글자 입니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("res");

                            if (result.equals("SUCCESS")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage(Rnickname + "님 회원가입이 완료되었습니다!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).create();
                                dialog.show();
                                return;


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(Rid, Rpassword, Rnickname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isFinishing()){
            overridePendingTransition(R.anim.none, R.anim.none);
        }
    }
}
