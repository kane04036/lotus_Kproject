package com.example.kangnamuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
    TextView tvResult;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("강남대 커뮤니티");

        edtID = findViewById(R.id.edtID);
        edtPW = findViewById(R.id.edtPW);
        btnLogin = findViewById(R.id.btnLogin);
        tvResult = findViewById(R.id.tvResult);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        sharedPreferences = getSharedPreferences("UserInfo",Context.MODE_PRIVATE);


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


                            if(result.equals("SUCCESS")){
                                //tvResult.setText(result + key);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("session", key);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), MyInfoCheckActivity.class);
                                startActivity(intent);

                            }
                            else{
                                //tvResult.setText(result + key);
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
        Button btnGoMain;
        btnGoMain = findViewById(R.id.btnGoMain);
        btnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FragmentMainActivity.class);
                startActivity(intent);
            }
        });

        Button btnGoInfoCheck;
        btnGoInfoCheck = findViewById(R.id.btnGoInfoCheck);
        btnGoInfoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyInfoCheckActivity.class);
                startActivity(intent);
            }
        });

    }
}