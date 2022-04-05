package com.example.kangnamuniv;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.HashMap;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    TextView tvLectureName;
    Button btnWrite;
    String res, data;
    String session;
    int Lnumber;
    ListView boardListView;

    public ArrayList<String> title = new ArrayList<>();
    public ArrayList<String> writers = new ArrayList<String>();
    public ArrayList<Integer> postNum = new ArrayList<Integer>();
    ArrayList<BoardView> boardView = new ArrayList<BoardView>();
    CustomArrayAdapter customArrayAdapter;


    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board); //액티비티는 onCreate 에서 작동하니까 무조건 oncreate 메소드?를 만들고 그 내부에서 실행할 코드를 작성하삼. 안도르이드 생명주기 검색해서 봐보시면 이해 될 거여요
        setTitle("글쓰기");

        tvLectureName = findViewById(R.id.tvLectureName);
        btnWrite = findViewById(R.id.btnWrite);
        boardListView = findViewById(R.id.boardListView);
        customArrayAdapter = new CustomArrayAdapter(getApplicationContext(), boardView);


        String lecture = getIntent().getStringExtra("lecture"); //intent 생성하고 putString해서 넘겼던 데이터들을 이렇게 받아올 수 있음
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");
        Lnumber = getIntent().getIntExtra("Lnumber", 0);


        tvLectureName.setText(lecture);//텍스트뷰를 설정하는 메소드 setText
        //만약에 editText에서 작성한 값을 자바 코드내에서 String 변수로 받아오고 싶으면 edtText변수이름.getText().toString(); 하면 문자열로 받아올 수 있음

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //post나 get방식으로 통신을 하기 위해서는 RequestQueue를 만들어야함 걍 이거 고대로 복붙해서 쓰면 될듯? 괄호 안에 파라미터가 오류가 날 수 있는데,,, getActivity getContext 뭐 이런 거 쳐보고 잘 되는 걸로 고고 ㅎ

        JSONObject jsonObject = new JSONObject();
        try {
            //서버로 보낼 데이터를 json형태 오브젝트로 보내기 위해서 위에 JSONObject를 만들어서 거기에 session 이름과 Lnumber이름으로 데이터를 넣었음.
            // name:value 쌍으로 들어가는 거라서 name부분에는 문자열로 넣어야함 value는 변수 값
            jsonObject.put("session", session);
            jsonObject.put("Lnumber", Lnumber);

        } catch (Exception e) {
            e.printStackTrace();
        }


        String URL = "http://34.64.49.11/mainview";//각 상황에 맞는 서버 url

        // 변수명은 그냥 알아듣기 쉬운 걸로~~ 앞으로는 거의 다 post로 사용할 거라서 거의 똑같이 작성하면 되긴함.
        //이거 그대로 따라 치다보면 new Resonse.Listener이 부분에서 자동으로 밑에 메소드들이 생길 거임
        JsonObjectRequest mainViewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //아마 try catch가 필수였던 걸로 기억함.

                Log.d("test", String.valueOf(this));
                ;
                try {
                    //dataArray = new JSONArray(response.getString("data")); //data이름으로 된 데이터를 받기위한 코드
                    data = response.getString("data");
                    res = response.getString("res"); //동일



                    if (res.contains("Success")) {
                        Log.d("testPost2", res);

                        if (data.equals("[]")) {
                            Log.d("test", "빈 게시판");
                        } else {
                            JSONArray dataJsonArray = new JSONArray(data);
                            if (postNum.isEmpty()) {
                                for (int i = 0; i < dataJsonArray.length(); i++) {
                                    JSONArray each = dataJsonArray.getJSONArray(i);

                                    boardView.add(new BoardView(String.valueOf(each.get(0)), String.valueOf(each.get(1))));
                                    postNum.add((Integer) each.get(2));

                                    Log.d("testArray", String.valueOf(boardView.get(i)));
                                    Log.d("testArray", String.valueOf(postNum.get(i)));
                                }
                                //맨처음 추가할때만 선언 후 set. 그 이후는 그냥 notifyChange로 변경값만 알려주기
                                boardListView.setAdapter(customArrayAdapter);
                            } else {
                                final int validateCheck = postNum.get(0);
                                for (int i = 0; i < dataJsonArray.length(); i++) {
                                    //for문 돌면서 중복체크해서 중복되지 않는 값만 리스트의 맨 앞에 추가. 맨 처음 값을 가려오고 arraylist 의 맨 처음 값과 중복 체크. 그 뒤도 똑같이 진행. 중복값이 발견되면 break;
                                    JSONArray each = dataJsonArray.getJSONArray(i);
                                    if (validateCheck != (Integer) each.get(2)) {

                                        boardView.add(0, new BoardView(String.valueOf(each.get(0)), String.valueOf(each.get(1))));
                                        postNum.add(0, (Integer) each.get(2));

                                        Log.d("testArray", String.valueOf(boardView.get(i)));
                                        Log.d("testArray", String.valueOf(postNum.get(i)));
                                    } else break;
                                }

                                //맨처음 추가할때만 선언 후 set. 그 이후는 그냥 notifyChange로 변경값만 알려주기
                                customArrayAdapter.notifyDataSetChanged();
                            }


                        }


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

        requestQueue.add(mainViewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨

        //밑에 코드는 버튼이 클릭됐을때 작동하는 코드 버튼변수명.setOnClickListener하고 괄호한 다음에 new 만 쳐도 자동으로 상황에 맞는 onClick메소드 작성해줄 것임
        //리스트뷰 클릭메소드랑 약간 다른데 그건 fragmenthome에 있는 클릭 메소드랑 비교하면 잘 보일듯!?
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);//여기도 intent로 액티비티 전환 intent이름은 마음대로 해도됨
                //Intent sueprloveintent = new Intent(get~~~~,~~.class); <<이렇게도 작성 가능
                //intent.putExtra("session", session);
                intent.putExtra("Lnumber", Lnumber);
                startActivity(intent);
            }
        });


        boardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getApplicationContext(), postNum.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                intent.putExtra("Bnumber", postNum.get(position));
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        /*RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //post나 get방식으로 통신을 하기 위해서는 RequestQueue를 만들어야함 걍 이거 고대로 복붙해서 쓰면 될듯? 괄호 안에 파라미터가 오류가 날 수 있는데,,, getActivity getContext 뭐 이런 거 쳐보고 잘 되는 걸로 고고 ㅎ

        JSONObject jsonObject = new JSONObject();
        try {
            //서버로 보낼 데이터를 json형태 오브젝트로 보내기 위해서 위에 JSONObject를 만들어서 거기에 session 이름과 Lnumber이름으로 데이터를 넣었음.
            // name:value 쌍으로 들어가는 거라서 name부분에는 문자열로 넣어야함 value는 변수 값
            jsonObject.put("session", session);
            jsonObject.put("Lnumber", Lnumber);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String URL = "http://34.64.49.11/mainview";//각 상황에 맞는 서버 url

        // 변수명은 그냥 알아듣기 쉬운 걸로~~ 앞으로는 거의 다 post로 사용할 거라서 거의 똑같이 작성하면 되긴함.
        //이거 그대로 따라 치다보면 new Resonse.Listener이 부분에서 자동으로 밑에 메소드들이 생길 거임
        JsonObjectRequest mainViewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //아마 try catch가 필수였던 걸로 기억함.

                try {
                    //dataArray = new JSONArray(response.getString("data")); //data이름으로 된 데이터를 받기위한 코드
                    data = response.getString("data");
                    res = response.getString("res"); //동일

                    Log.d("testPost1", res);


                    if (res.contains("Success")) {
                        JSONArray dataJsonArray = new JSONArray(data);

                        final int validateCheck = postNum.get(0);

                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            //for문 돌면서 중복체크해서 중복되지 않는 값만 리스트의 맨 앞에 추가. 맨 처음 값을 가려오고 arraylist 의 맨 처음 값과 중복 체크. 그 뒤도 똑같이 진행. 중복값이 발견되면 break;
                            JSONArray each = dataJsonArray.getJSONArray(i);
                            if (validateCheck != (Integer) each.get(2)) {

                                boardView.add(0, new BoardView(String.valueOf(each.get(0)), String.valueOf(each.get(1))));
                                postNum.add(0, (Integer) each.get(2));

                                Log.d("testArray", String.valueOf(boardView.get(i)));
                                Log.d("testArray", String.valueOf(postNum.get(i)));
                            } else break;
                        }

                        //맨처음 추가할때만 선언 후 set. 그 이후는 그냥 notifyChange로 변경값만 알려주기
                        customArrayAdapter.notifyDataSetChanged();


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

        requestQueue.add(mainViewRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
*/
    }


}

