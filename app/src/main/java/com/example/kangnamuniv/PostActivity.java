package com.example.kangnamuniv;

import static android.media.CamcorderProfile.get;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    static String title;
    static String writer;
    static String msg;
    static String session;
    static int Bnumber;

    TextView tvDetailTitle, tvDetailNickname, tvDetailMsg;
    Button btnComment;
    EditText edtComment;
    CheckBox chkAnonymousComment;
    String commentMsg;
    int commentAnonymous;
    ListView listViewComment;

    ArrayList<String> cmtWritersAry = new ArrayList<String>();
    ArrayList<String> cmtMsgAry = new ArrayList<String>();
    ArrayList<Integer> CnumberAry = new ArrayList<>();
    ArrayList<BoardView> cmtArray = new ArrayList<>();

    CustomArrayAdapterComment customArrayAdapterComment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailNickname = findViewById(R.id.tvDetailNickname);
        tvDetailMsg = findViewById(R.id.tvDetailMsg);
        btnComment = findViewById(R.id.btnComment);
        edtComment = findViewById(R.id.edtComment);
        chkAnonymousComment = findViewById(R.id.chkAnonymousCommnet);
        listViewComment = findViewById(R.id.listViewComment);
        customArrayAdapterComment= new CustomArrayAdapterComment(getApplicationContext(), cmtArray);


        Bnumber = getIntent().getIntExtra("Bnumber",0);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("Bnumber", Bnumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("test7", session);
        Log.d("test7", String.valueOf(Bnumber));


        String URL = "http://34.64.49.11/boardview";//각 상황에 맞는 서버 url


        JsonObjectRequest boardViewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res");
                    JSONArray boardarray = response.getJSONArray("board");
                    JSONArray commentarray = response.getJSONArray("comment");



                    if (res.contains("Success")) {
                        Log.d("testPost", res);

                        for (int i = 0; i < boardarray.length(); i++) {
                            JSONArray each = boardarray.getJSONArray(i);
                            title = String.valueOf(each.get(0));
                            writer = String.valueOf(each.get(1));
                            msg = String.valueOf(each.get(2));

                            Log.d("test: board", title);
                            Log.d("test: board", writer);
                            Log.d("test: board", msg);

                        }
                        for (int i = 0; i < commentarray.length(); i++) {
                            JSONArray each = commentarray.getJSONArray(i);
                            cmtWritersAry.add(String.valueOf(each.get(0)));
                            cmtMsgAry.add(String.valueOf(each.get(1)));
                            CnumberAry.add((Integer) each.get(2));
                            cmtArray.add(new BoardView(String.valueOf(each.get(0)), String.valueOf(each.get(1))));


                            Log.d("test: comment", cmtWritersAry.get(i));
                            Log.d("test: comment", cmtMsgAry.get(i));
                            Log.d("test: comment", String.valueOf(CnumberAry.get(i)));
                            Log.d("test: comment", String.valueOf(cmtArray.get(i)));



                        }
                        tvDetailTitle.setText(title);
                        tvDetailNickname.setText(writer);
                        tvDetailMsg.setText(msg);

                        listViewComment.setAdapter(customArrayAdapterComment);

                    } else {
                        Log.d("test: res", res);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(boardViewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentMsg = edtComment.getText().toString();
                if (chkAnonymousComment.isChecked())
                    commentAnonymous = 1;
                else commentAnonymous = 0;

                RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());

                JSONObject commentObject = new JSONObject();
                try {
                    commentObject.put("session", session);
                    commentObject.put("Bnumber", Bnumber);
                    commentObject.put("anonymous", commentAnonymous);
                    commentObject.put("msg", commentMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String commentURL = "http://34.64.49.11/commentwrite";//각 상황에 맞는 서버 url

                JsonObjectRequest commentWriteRequest = new JsonObjectRequest(Request.Method.POST, commentURL, commentObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("res");

                            if (res.contains("Success")) {
                                edtComment.setText("");
                                Log.d("testComment", res);
                                finish();
                                startActivity(getIntent());

                            } else Log.d("testResult", res);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                requestQueue2.add(commentWriteRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("Bnumber", Bnumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("test7", session);
        Log.d("test7", String.valueOf(Bnumber));


        String URL = "http://34.64.49.11/boardview";//각 상황에 맞는 서버 url


        JsonObjectRequest boardViewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res");
                    JSONArray boardarray = response.getJSONArray("board");
                    JSONArray commentarray = response.getJSONArray("comment");



                    if (res.contains("Success")) {
                        Log.d("testPost", res);

                        for (int i = 0; i < boardarray.length(); i++) {
                            JSONArray each = boardarray.getJSONArray(i);
                            title = String.valueOf(each.get(0));
                            writer = String.valueOf(each.get(1));
                            msg = String.valueOf(each.get(2));

                            Log.d("test: board", title);
                            Log.d("test: board", writer);
                            Log.d("test: board", msg);

                        }
                        final int validateCmt = CnumberAry.get(0);
                        for (int i = 0; i < commentarray.length(); i++) {
                            JSONArray each = commentarray.getJSONArray(i);
                            if(validateCmt != (Integer)each.get(2) ) {
                                cmtWritersAry.add(0,String.valueOf(each.get(0)));
                                cmtMsgAry.add(0,String.valueOf(each.get(1)));
                                CnumberAry.add(0,(Integer) each.get(2));
                                cmtArray.add(0,new BoardView(String.valueOf(each.get(0)), String.valueOf(each.get(1))));
                            }

//                            Log.d("test: comment", cmtWritersAry.get(i));
//                            Log.d("test: comment", cmtMsgAry.get(i));
//                            Log.d("test: comment", String.valueOf(CnumberAry.get(i)));
//                            Log.d("test: comment", String.valueOf(cmtArray.get(i)));

                        }

                        tvDetailTitle.setText(title);
                        tvDetailNickname.setText(writer);
                        tvDetailMsg.setText(msg);
                        customArrayAdapterComment.notifyDataSetChanged();


                    } else {
                        Log.d("test: res", res);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(boardViewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
*/

    }
}
