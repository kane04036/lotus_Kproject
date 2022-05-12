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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeNicknameActivity extends AppCompatActivity {
    String newNickname;
    boolean nickname_validate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_nick);
        overridePendingTransition(R.anim.none, R.anim.none);



        EditText edtNewNick = findViewById(R.id.newnick);
        Button btnNewNickCheck = findViewById(R.id.btnNewNickCheck);
        Button btnChageNickname = findViewById(R.id.btnChangeNick);
        TextView tvNickchange = findViewById(R.id.tvNickchange);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        String session = sharedPreferences.getString("session", "");

        btnNewNickCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNickname = edtNewNick.getText().toString();
                if (nickname_validate) {
                    return;
                }
                AlertDialog dialog;
                if (newNickname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    dialog = builder.setMessage("닉네임 입력해주세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (newNickname.length() > 15) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
                if (nickname_validate) {
                    Changenk changenk = new Changenk(session, edtNewNick.getText().toString(), getApplicationContext());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(changenk.getResult()){
                                newNickname = changenk.getNewNickname();
                                SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("nickname", newNickname);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), FragmentMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        }
                    }, 200);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    AlertDialog dialog = builder.setMessage("닉네임 중복체크가 되지 않았습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
            }
        });
    }
}
