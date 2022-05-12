package com.example.kangnamuniv;

import static android.media.CamcorderProfile.get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    TextView tvDetailTitle, tvDetailNickname, tvDetailMsg, tvPostLecture;
    Button btnComment;
    EditText edtComment;
    CheckBox chkAnonymousComment;
    String commentMsg, lecture;
    int commentAnonymous;
    RecyclerView listViewComment;
    ImageButton btnMore;
    CommentRecyclerViewAdapter commentAdapater;

    ArrayList<String> cmtWritersAry = new ArrayList<String>();
    ArrayList<String> cmtMsgAry = new ArrayList<String>();
    ArrayList<Integer> CnumberAry = new ArrayList<>();
    ArrayList<BoardView> cmtArray = new ArrayList<>();
    String TAG = "test";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        overridePendingTransition(R.anim.none, R.anim.none);


        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailNickname = findViewById(R.id.tvDetailNickname);
        tvDetailMsg = findViewById(R.id.tvDetailMsg);
        btnComment = findViewById(R.id.btnComment);
        edtComment = findViewById(R.id.edtComment);
        chkAnonymousComment = findViewById(R.id.chkAnonymousCommnet);
        listViewComment = findViewById(R.id.listViewComment);
        tvPostLecture = findViewById(R.id.tvPostLecture);
        btnMore = findViewById(R.id.btnMore);

        commentAdapater = new CommentRecyclerViewAdapter(cmtArray, getApplicationContext());
        listViewComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listViewComment.setAdapter(commentAdapater);

        Bnumber = getIntent().getIntExtra("Bnumber", 0);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");
        lecture = getIntent().getStringExtra("Lecture");

        Log.d("testLecture2", lecture);

        tvPostLecture.setText(lecture);

        super.onResume();
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
            @SuppressLint("LongLogTag")
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
//                        if (CnumberAry.isEmpty()) {
                            Log.d("testPostActivityAdapter1","어뎁터 설정 전");
                            for (int i = 0; i < commentarray.length(); i++) {
                                JSONArray each = commentarray.getJSONArray(i);
                                cmtWritersAry.add( String.valueOf(each.get(0)));
                                cmtMsgAry.add( String.valueOf(each.get(1)));
                                CnumberAry.add( (Integer) each.get(2));
                                cmtArray.add( new BoardView(String.valueOf(each.get(1)), String.valueOf(each.get(0))));
                            }

//                        } else {
//                            final int validateCmt = CnumberAry.get(0);
//                            for (int i = 0; i < commentarray.length(); i++) {
//                                JSONArray each = commentarray.getJSONArray(i);
//                                if (validateCmt != (Integer) each.get(2)) {
//                                    Log.d("testPostActivityAdapter2","어뎁터 설정 전");
//                                    cmtWritersAry.add( String.valueOf(each.get(0)));
//                                    cmtMsgAry.add(String.valueOf(each.get(1)));
//                                    CnumberAry.add( (Integer) each.get(2));
//                                    cmtArray.add( new BoardView(String.valueOf(each.get(1)), String.valueOf(each.get(0))));
//                                }
//                            }
//
//
//                        }

                        commentAdapater.setSeqArray(CnumberAry);
                        Log.d(TAG, "onResponse: seq set 완료");
                        commentAdapater.notifyDataSetChanged();

                        tvDetailTitle.setText(title);
                        tvDetailNickname.setText(writer);
                        tvDetailMsg.setText(msg);


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



        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(PostActivity.this, view);
                getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menu_delete:
                                postDelete();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

                //return true;
            }
        });

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
                    Log.d(TAG, "onClick: object:" + session + "and" + Bnumber+"and"+commentAnonymous+"and"+msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String commentURL = "http://34.64.49.11/commentwrite";//각 상황에 맞는 서버 url

                JsonObjectRequest commentWriteRequest = new JsonObjectRequest(Request.Method.POST, commentURL, commentObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("res");
                            String msg = response.getString("msg");
                            int seq = response.getInt("seq");
                            String writer = response.getString("writer");
                            Log.d(TAG, "onResponse: res:"+ res);
                            Log.d(TAG, "onResponse: commentData:" + msg);
                            Log.d(TAG, "onResponse: seq: "+ seq);
                            Log.d(TAG, "onResponse: writer: "+ writer);

                            if (res.contains("Success")) {
                                edtComment.setText("");
                                cmtArray.add(new BoardView(msg, writer));
                                CnumberAry.add(seq);

                                commentAdapater.putSeqArray(seq);
                                Log.d(TAG, "onResponse: seq put 완료");
                                commentAdapater.notifyDataSetChanged();

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

//        listViewComment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                AlertDialog.Builder dlg = new AlertDialog.Builder(PostActivity.this);
//                dlg.setMessage("삭제하시겠습니까?");
//                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        RequestQueue requestQueueCommentDel = Volley.newRequestQueue(getApplicationContext());
//
//                        JSONObject jsonObject = new JSONObject();
//                        try {
//                            jsonObject.put("session", session);
//                            jsonObject.put("Cnumber", CnumberAry.get(position));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        String URL = "http://34.64.49.11/commentdelete";//각 상황에 맞는 서버 url
//
//
//                        JsonObjectRequest boardViewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    String res = response.getString("res");
//                                    if(res.contains("Success")){
//                                        Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
//
//                                        CnumberAry.remove(position);
//                                        cmtMsgAry.remove(position);
//                                        cmtWritersAry.remove(position);
//                                        cmtArray.remove(position);
//                                        customArrayAdapterComment.notifyDataSetChanged();
//                                    }
//                                    else{
//                                        Toast.makeText(getApplicationContext(), "댓글 작성자만 삭제할 수 있습니다", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//
//
//                        requestQueueCommentDel.add(boardViewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
//                        //Toast.makeText(getApplicationContext(), "삭제할까요?", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                dlg.show();
//
//
//                return false;
//            }
//        });

    }

    private void postDelete(){
        RequestQueue QeueDelete = Volley.newRequestQueue(getApplicationContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("Bnumber", Bnumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("test7", session);
        Log.d("test7", String.valueOf(Bnumber));


        String URL = "http://34.64.49.11/boarddelete";//각 상황에 맞는 서버 url


        JsonObjectRequest boardDeleteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res");
                    Log.d("testDelete", res);

                    if (res.contains("Success")) {
                        finish();
                        Log.d("testDelete", res);
                        Toast.makeText(getApplicationContext(), "삭제되었습니다.",Toast.LENGTH_SHORT).show();
                        int position = ((BoardActivity)BoardActivity.context_main).postNum.indexOf(Bnumber);
                        ((BoardActivity)BoardActivity.context_main).postNum.remove(position);
                        ((BoardActivity)BoardActivity.context_main).boardView.remove(position);
                        //Intent intent = new Intent(PostActivity.this, BoardActivity.class);
                        //startActivity(intent);


                    } else {
                        Log.d("testDelete", res);
                        Toast.makeText(getApplicationContext(), "글 작성자만 삭제할 수 있습니다",Toast.LENGTH_SHORT).show();
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
        QeueDelete.add(boardDeleteRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isFinishing())
            overridePendingTransition(R.anim.none, R.anim.none);

    }
}
