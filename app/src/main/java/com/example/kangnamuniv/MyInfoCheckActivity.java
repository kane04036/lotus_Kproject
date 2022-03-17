package com.example.kangnamuniv;

import android.content.Intent;
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

public class MyInfoCheckActivity extends AppCompatActivity {
    TextView tvLecture;
    EditText edtSchoolID, edtShoolPW, edtViewName;
    Button btnRenew, btnGoHome;
    public static String schoolID, schoolPW;
    ProgressBar progressBar;

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
                            String[] strlist = strparsing(lectures);

                            edtViewName.setVisibility(View.VISIBLE);
                            edtViewName.setText(name);

                            progressBar.setVisibility(View.INVISIBLE);
                            for(int i = 0; i < strlist.length; i++){
                                tvLecture.append(strlist[i] + "\n");
                            }
                            /*for(int i=0; i< strlist.length; i++){
                                tvLecture.setText(strlist[i]);
                                Thread.sleep(2000);
                            }*/


                           // ListView listView = findViewById(R.id.listViewLecture);

                           // ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(MyInfoCheckActivity.this, android.R.layout.simple_list_item_1, strlist);
                           // listView.setAdapter(arrayadapter);

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
                Intent intent = new Intent(getApplicationContext(), FragmentMainActivity.class);
                startActivity(intent);
            }
        });


    }

    public String[] strparsing(String str){

        for(int i = 0; i< str.length(); i++){
            String newStr = str.replace("\"", "");
            str = newStr;
        }
        str.replace("[", "");
        str.replace("]", "");
        String[] strAry = str.split(",");

        return strAry;

    }


}
