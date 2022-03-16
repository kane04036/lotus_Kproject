package com.example.kangnamuniv;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    Button btnRenew;
    public static String schoolID, schoolPW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfocheck);

        edtSchoolID = findViewById(R.id.edtSchoolID);
        edtShoolPW = findViewById(R.id.edtSchoolPW);
        btnRenew = findViewById(R.id.btnRenew);
        edtViewName = findViewById(R.id.edtViewName);
        tvLecture = findViewById(R.id.tvLectures);

        btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schoolID = edtSchoolID.getText().toString();
                schoolPW = edtShoolPW.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String name = jsonResponse.getString("name");
                            String lectures = jsonResponse.getString("lectures");
                            String[] strlist = strparsing(lectures);

                            edtViewName.setText(name);
                            /*for(int i=0; i< strlist.length; i++){
                                tvLecture.setText(strlist[i]);
                                Thread.sleep(2000);
                            }*/


                           // ListView listView = findViewById(R.id.listViewLecture);

                           // ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(MyInfoCheckActivity.this, android.R.layout.simple_list_item_1, strlist);
                           // listView.setAdapter(arrayadapter);


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


    }

    public String[] strparsing(String str){

        String[] strAry = str.split(",");

        return strAry;

    }


}
