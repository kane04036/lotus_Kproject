package com.example.kangnamuniv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyInfoCheckActivity extends AppCompatActivity {
    TextView tvLecture, notice;
    EditText edtSchoolID, edtShoolPW, edtViewName;
    Button btnRenew, btnGoHome;
    public static String schoolID, schoolPW;
    ProgressBar progressBar;
    public static ArrayList<String> lecturelist = new ArrayList<String>();
    public static ArrayList<Integer> seqlist = new ArrayList<>();

    PreferenceManagers preferenceManagers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfocheck);

        edtSchoolID = findViewById(R.id.edtSchoolID);
        edtShoolPW = findViewById(R.id.edtSchoolPW);
        btnRenew = findViewById(R.id.btnRenew);
        edtViewName = findViewById(R.id.edtViewName);
        tvLecture = findViewById(R.id.tvLectures);
        btnGoHome = findViewById(R.id.btnGoHome);
        progressBar = findViewById(R.id.progressBar);
        notice = findViewById(R.id.notice);

        btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schoolID = edtSchoolID.getText().toString();
                schoolPW = edtShoolPW.getText().toString();
                notice.setText("학번과 비밀번호를 잘못 입력할 시 \n강의 목록이 불러와지지 않습니다.\n정확히 입력해 주세요.");
                notice.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String name = jsonResponse.getString("name");
                            JSONArray lectureArray = jsonResponse.getJSONArray("lectures");
                            JSONArray seqArray = jsonResponse.getJSONArray("seq");


                            //여기를 아예 sharedPreference arraylist로 바꾸기
                            for (int i = 0; i < lectureArray.length(); i++) {
                                lecturelist.add((String) lectureArray.get(i));
                                seqlist.add((Integer) seqArray.get(i));
                                Log.d("testmyinfo", lecturelist.get(i));
                                Log.d("testmyinfo", String.valueOf(seqlist.get(i)));

                            }
                            Log.d("test","setter실행 전");

                           // preferenceManagers.setStringArrayPref(MyInfoCheckActivity.this, "lectures", lecturelist);


                            edtViewName.setVisibility(View.VISIBLE);
                            edtViewName.setText(name);
                            notice.setText("");
                            progressBar.setVisibility(View.INVISIBLE);
                            notice.setVisibility(View.INVISIBLE);
                            for (int i = 0; i < lecturelist.size(); i++) {
                                tvLecture.append(lecturelist.get(i) + "\n");
                            }

                            btnGoHome.setVisibility(View.VISIBLE);

                            Log.d("test","setter실행 ");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                };
                MyInfoCheckRequest myInfoCheckRequest = new MyInfoCheckRequest(schoolID, schoolPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MyInfoCheckActivity.this);
                queue.add(myInfoCheckRequest);


            }
        });

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FragmentMainActivity.class);
                startActivity(intent);
                //finish();
            }
        });


    }
}
