package com.example.kangnamuniv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePWActivity extends AppCompatActivity {
    EditText edtOriginPW, edtNewPW, edtNewPWCheck;
    Button btnPWChange;
    TextView tvPWChangeCheck;
    boolean newpw_check, originPW_check;
    String session, originPW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        overridePendingTransition(R.anim.none, R.anim.none);


        edtOriginPW = findViewById(R.id.edtOriginPW);
        edtNewPW = findViewById(R.id.edtNewPW);
        edtNewPWCheck = findViewById(R.id.edtNewPWCheck);
        btnPWChange = findViewById(R.id.btnPWChange);
        tvPWChangeCheck = findViewById(R.id.tvPWChangeCheck);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");

        SharedPreferences prefPWChange = getSharedPreferences("UserInfo", MODE_PRIVATE);
        originPW = prefPWChange.getString("PW", "");
        Log.d("test", "onCreate:OriginPW: "+originPW);


        edtNewPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((edtNewPW.getText().toString()).equals(edtNewPWCheck.getText().toString())) {
                    tvPWChangeCheck.setTextColor(Color.BLUE);
                    tvPWChangeCheck.setText("비밀번호가 일치합니다");
                    newpw_check = true;
                } else {
                    tvPWChangeCheck.setTextColor(Color.RED);
                    tvPWChangeCheck.setText("비밀번호가 일치하지 않습니다.");
                    newpw_check = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((edtNewPW.getText().toString()).equals(edtNewPWCheck.getText().toString())) {
                    tvPWChangeCheck.setTextColor(Color.BLUE);
                    tvPWChangeCheck.setText("비밀번호가 일치합니다");
                    newpw_check = true;
                } else {
                    tvPWChangeCheck.setTextColor(Color.RED);
                    tvPWChangeCheck.setText("비밀번호가 일치하지 않습니다.");
                    newpw_check = false;
                }
            }
        });

        edtNewPWCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((edtNewPW.getText().toString()).equals(edtNewPWCheck.getText().toString())) {
                    tvPWChangeCheck.setTextColor(Color.BLUE);
                    tvPWChangeCheck.setText("비밀번호가 일치합니다");
                    newpw_check = true;
                } else {
                    tvPWChangeCheck.setTextColor(Color.RED);
                    tvPWChangeCheck.setText("비밀번호가 일치하지 않습니다.");
                    newpw_check = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((edtNewPW.getText().toString()).equals(edtNewPWCheck.getText().toString())) {
                    tvPWChangeCheck.setTextColor(Color.BLUE);
                    tvPWChangeCheck.setText("비밀번호가 일치합니다");
                    newpw_check = true;
                } else {
                    tvPWChangeCheck.setTextColor(Color.RED);
                    tvPWChangeCheck.setText("비밀번호가 일치하지 않습니다.");
                    newpw_check = false;
                }
            }
        });

        btnPWChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtOriginPW.getText().toString().equals(originPW)){
                    originPW_check = true;

                    if(newpw_check){
                        Changepw changepw = new Changepw(session, edtNewPWCheck.getText().toString(), getApplicationContext());
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (changepw.getResult()) {
                                    SharedPreferences prefPWChange = getSharedPreferences("UserInfo", MODE_PRIVATE);
                                    SharedPreferences.Editor editor3 = prefPWChange.edit();
                                    editor3.remove("PW");
                                    editor3.commit();

                                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("PW", edtNewPWCheck.getText().toString());
                                    editor.commit();

                                    Intent intent = new Intent(getApplicationContext(), FragmentMainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                }
                            }
                        }, 200);
                    }else{
                        AlertDialog dialog;
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePWActivity.this);
                        dialog = builder.setMessage("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.").setPositiveButton("확인", null).create();
                        dialog.show();
                        return;
                    }
                }
                else {
                    originPW_check = false;
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePWActivity.this);
                    dialog = builder.setMessage("기존 비밀번호와 일치하지 않습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }



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
