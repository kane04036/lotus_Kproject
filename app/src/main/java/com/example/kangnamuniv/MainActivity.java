package com.example.kangnamuniv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    EditText edtID;
    EditText edtPW;
    Button btnLogin, btnGoRegister;
    public static String Rid, Rpassword, key;
    SharedPreferences sharedPreferences;
    String SharedID, SharedPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("강남대 커뮤니티");

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        edtID = findViewById(R.id.edtID);
        edtPW = findViewById(R.id.edtPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        SharedID = sharedPreferences.getString("ID", null);
        SharedPW = sharedPreferences.getString("PW", null);


        if (SharedID != null && SharedPW != null) {
            Intent intent = new Intent(getApplicationContext(), MyInfoCheckActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rid = edtID.getText().toString();
                Rpassword = edtPW.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String result = jsonResponse.getString("res");
                            key = jsonResponse.getString("key");
                            String nickname = jsonResponse.getString("nickname");

                            Log.d("test", "onResponse: MainActivity "+result);

                            if (result.equals("SUCCESS")) {
                                //tvResult.setText(result + key);
                                editor.putString("session", key);
                                editor.putString("ID", Rid);
                                editor.putString("PW", Rpassword);
                                editor.putString("nickname", nickname);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), MyInfoCheckActivity.class);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                AlertDialog dialog = builder.setMessage("아이디와 비밀번호를 확인해주세요").setPositiveButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(Rid, Rpassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);


            }
        });

        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}