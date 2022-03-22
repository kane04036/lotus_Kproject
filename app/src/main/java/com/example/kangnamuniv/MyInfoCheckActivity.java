package com.example.kangnamuniv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MyInfoCheckActivity extends AppCompatActivity {
    TextView tvLecture;
    EditText edtSchoolID, edtShoolPW, edtViewName;
    Button btnRenew, btnGoHome;
    public static String schoolID, schoolPW;
    ProgressBar progressBar;
    public static String[] lecturelist, seqlist;

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

        btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schoolID = edtSchoolID.getText().toString();
                schoolPW = edtShoolPW.getText().toString();
                progressBar.setVisibility(View.VISIBLE);


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String name = jsonResponse.getString("name");
                            String lectures = jsonResponse.getString("lectures");
                            String dataNums = jsonResponse.getString("seq");
                            lecturelist = lectureStrparsing(lectures);
                            seqlist = seqStrParsing(dataNums);


                            edtViewName.setVisibility(View.VISIBLE);
                            edtViewName.setText(name);

                            progressBar.setVisibility(View.INVISIBLE);
                          //  tvLecture.setText(lectures);
                            for(int i = 0; i < lecturelist.length; i++){
                               tvLecture.append(lecturelist[i] + "\n");
                            }
                            for(int i = 0; i < lecturelist.length; i++){
                                tvLecture.append(seqlist[i] + "\n");
                            }


                            btnGoHome.setVisibility(View.VISIBLE);



                        } catch (JSONException  e) {
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
               /* SharedPreferences sharedPreferences = getSharedPreferences("lecture",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> set = new HashSet<String>();
                set.addAll(Arrays.asList(lecturelist));
                editor.putStringSet("lectures", set);
                editor.commit();*/

                //PreferenceManager.saveSharedPreferences_Data(getApplicationContext(), "lectures", lecturelist);

                Intent intent = new Intent(getApplicationContext(), FragmentMainActivity.class);
                startActivity(intent);
            }
        });


    }

    public String[] lectureStrparsing(String lecture){

      /*  for(int i = 0; i< lecture.length(); i++){
            String newStr = lecture.replace("\"", "");
            lecture = newStr;
        }*/

        String newStr = lecture.replace("\"", "");
        String newStr2 = newStr.replace("[","");
        String newStr3 = newStr2.replace("]","");

        String[] strAry = newStr3.split(",");

        return strAry;

    }
    public String[] seqStrParsing(String seq){
        String newStr = seq.replace("\"", "");
        String newStr2 = newStr.replace("[","");
        String newStr3 = newStr2.replace("]","");

        String[] seqAry = newStr3.split(",");

        return seqAry;
    }




}
