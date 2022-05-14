package com.example.kangnamuniv;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.function.Consumer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentCalendarActivity extends Fragment {
    MaterialCalendarView calendarView;
    ImageButton btnPlus, btnCheck;
    EditText edtTodo;
    String session, msg;
    SharedPreferences sharedPreferences;
    Handler handler = new Handler();
    ArrayList<TodoView> todoArray = new ArrayList<>();
    ArrayList<Integer> todoSeqArray = new ArrayList<>();
    RecyclerView recyclerView;
    TodoRecyclerVeiwAdapter todoAdapter;
    public static Context context_main;
    ArrayList<CalendarDay> date = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        setHasOptionsMenu(true);

        context_main = getActivity();
        calendarView = view.findViewById(R.id.calendarview);
        btnPlus = view.findViewById(R.id.btnPlus);
        btnCheck = view.findViewById(R.id.btnCheck);
        edtTodo = view.findViewById(R.id.edtTodo);
        btnCheck.setEnabled(false);
        recyclerView = view.findViewById(R.id.todoRecyclerView);

//        todoArray.add(new TodoView("오늘의 할일 "));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        todoAdapter = new TodoRecyclerVeiwAdapter(todoArray, getActivity());
        recyclerView.setAdapter(todoAdapter);

        sharedPreferences = this.getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                detailsView();
                edtTodo.setText("");
                btnCheck.setVisibility(View.INVISIBLE);
                btnCheck.setEnabled(false);
                btnPlus.setVisibility(View.VISIBLE);
                btnPlus.setEnabled(true);
                edtTodo.setEnabled(false);
                edtTodo.setVisibility(View.INVISIBLE);

            }
        });


        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                calendarView.setSelectedDate(calendarView.getCurrentDate());
                monthView();
                detailsView();

            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTodo.setVisibility(View.VISIBLE);
                edtTodo.setEnabled(true);
                btnPlus.setEnabled(false);
                btnPlus.setVisibility(View.INVISIBLE);
                btnCheck.setVisibility(View.VISIBLE);
                btnCheck.setEnabled(true);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = edtTodo.getText().toString();
                scheduleWrite(msg);

            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        calendarView.setSelectedDate(CalendarDay.today());
        monthView();
        detailsView();
    }

    void scheduleWrite(String msg){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String URL = "http://34.64.49.11/schedulewrite";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("month", calendarView.getSelectedDate().getMonth());
            jsonObject.put("day", calendarView.getSelectedDate().getDay());
            jsonObject.put("msg", msg);

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest scheduleWriteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res"); //동일
                    String message = response.getString("msg");
                    int seq = response.getInt("seq");


                    if(res.contains("SUCCESS")) {
                        edtTodo.setText("");
                        btnCheck.setVisibility(View.INVISIBLE);
                        btnCheck.setEnabled(false);
                        btnPlus.setVisibility(View.VISIBLE);
                        btnPlus.setEnabled(true);
                        edtTodo.setEnabled(false);
                        edtTodo.setVisibility(View.INVISIBLE);
                        monthView();
                        todoArray.add(new TodoView(message));
                        todoSeqArray.add(seq);
                        todoAdapter.putSeqArray(seq);
                        todoAdapter.notifyDataSetChanged();
//                        calendarView.addDecorator(new DotDecorator(Color.RED, monthsViewWrite.getDate(), getActivity()));
                        monthView();

                    }
                    else{
                        Toast.makeText(getActivity(),"댓글 작성을 실패했습니다.",Toast.LENGTH_SHORT).show();
                    }
                    Log.d("testCalendar", res);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(scheduleWriteRequest);
    }

    public void monthView() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String URL = "http://34.64.49.11/monthsview";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("month", calendarView.getSelectedDate().getMonth());

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest monthviewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res"); //동일
                    Log.d("testCalendarRes", res);
                    if (res.contains("SUCCESS")) {
                        String ds = response.getString("day");
                        Log.d("testDay", ds);
                        JSONArray dateArray = response.getJSONArray("day");
                        date.clear();
                        for (int i = 0; i < dateArray.length(); i++) {
                            JSONArray each = dateArray.getJSONArray(i);
                            CalendarDay day = CalendarDay.from(calendarView.getSelectedDate().getYear(), calendarView.getSelectedDate().getMonth(), (Integer) each.get(0));
                            date.add(day);
                        }
                        calendarView.addDecorator(new DotDecorator(Color.RED, date, getActivity()));
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

        requestQueue.add(monthviewRequest);
    }

    void detailsView() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String URL = "http://34.64.49.11/detailsview";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("month", calendarView.getSelectedDate().getMonth());
            jsonObject.put("day", calendarView.getSelectedDate().getDay());

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest dayviewRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res"); //동일
                    Log.d("testCalendarResult", res);
                    JSONArray msgArray = response.getJSONArray("msg");
                    JSONArray seqArray = response.getJSONArray("seq");
                    String seq = response.getString("seq");
                    Log.d("testSeq", seq);

                    if (res.contains("SUCCESS")) {
                        todoArray.clear();
                        todoSeqArray.clear();

                        for (int i = 0; i < msgArray.length(); i++) {
                            JSONArray each = (JSONArray) msgArray.get(i);
                            for (int j = 0; j < each.length(); j++) {
                                todoArray.add(new TodoView(String.valueOf(each.get(j))));
                            }
                        }
                        for (int i = 0; i < seqArray.length(); i++) {
                            JSONArray each = (JSONArray) seqArray.get(i);
                            for (int j = 0; j < each.length(); j++) {
                                todoSeqArray.add((Integer) each.get(j));
                            }
                        }
                        todoAdapter.setSeqArray(todoSeqArray);
                        todoAdapter.notifyDataSetChanged();
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

        requestQueue.add(dayviewRequest);
    }


}
