package com.example.kangnamuniv;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegID = findViewById(R.id.edtRegID);
        edtRegPW = findViewById(R.id.edtRegPW);
        edtRegPWcheck = findViewById(R.id.edtRegPWcheck);
        edtRegNickname = findViewById(R.id.edtRegNickname);
        btnRegister = findViewById(R.id.btnRegister);
        resultTv = findViewById(R.id.resultTv);
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

                if(id_validate){
                    return;
                }
                if(Rid.equals("")){
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

                            if(result.equals("SUCCESS")){
                                //tvIdCheck.setText("사용가능한 아이디입니다.");
                                tvIdCheck.setText(result);
                                id_validate = true;
                                edtRegID.setEnabled(false);
                                btnIdCheck.setEnabled(false);
                            }
                            else{
                                //tvIdCheck.setText("이미 사용중인 아이디 입니다.");
                                tvIdCheck.setText(result);
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

        edtRegPWcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if((edtRegPW.getText().toString()).equals(edtRegPWcheck.getText().toString())){
                    tvPwCheck.setTextColor(Color.BLUE);
                    tvPwCheck.setText("비밀번호가 일치합니다");
                }
                else{
                    tvPwCheck.setTextColor(Color.RED);
                    tvPwCheck.setText("비밀번호가 일치하지 않습니다.");
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
                if(nickname_validate){
                    return;
                }
                if(Rnickname.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

                            if(result.equals("SUCCESS")){
                                //tvNicknameCheck.setText("사용가능한 닉네임 입니다.");
                                tvNicknameCheck.setText(result);
                                nickname_validate = true;
                                edtRegNickname.setEnabled(false);
                                btnNicknameCheck.setEnabled(false);
                            }
                            else{
                                //tvNicknameCheck.setText("이미 사용중인 닉네임 입니다.");
                                tvNicknameCheck.setText(result);
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


                if(!id_validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 확인해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(!nickname_validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

                            if(result.equals("SUCCESS")){
                               // resultTv.setText("회원가입 완료");
                                resultTv.setText(result);

                            }
                            else{
                                //tvNicknameCheck.setText("이미 사용중인 닉네임 입니다.");
                                resultTv.setText(result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(Rid, Rpassword,Rnickname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }


}
