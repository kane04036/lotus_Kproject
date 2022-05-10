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

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

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
    ArrayList<TodoView> newArray = new ArrayList<>();
    public static Context context_main;
    Disposable backgroundTask;




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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        todoAdapter = new TodoRecyclerVeiwAdapter(todoArray, getActivity());
        recyclerView.setAdapter(todoAdapter);

        sharedPreferences = this.getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                DetailsView detailsView = new DetailsView(session, calendarView.getSelectedDate().getMonth(), calendarView.getSelectedDate().getDay(), getActivity());
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        newArray = detailsView.getMsgArrayList();
                        todoSeqArray = detailsView.getTodoSeqArray();
                        todoArray.clear();
                        for (int i = 0; i < newArray.size(); i++) {
                            todoArray.add(newArray.get(i));
                        }
                        todoAdapter.setSeqArray(todoSeqArray);
                        todoAdapter.notifyDataSetChanged();

                    }
                }, 150);

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
                MonthsView newMonth = new MonthsView(session, calendarView.getCurrentDate().getYear(), calendarView.getCurrentDate().getMonth(), getActivity());
                calendarView.setSelectedDate(calendarView.getCurrentDate());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        calendarView.addDecorator(new DotDecorator(Color.RED, newMonth.getDate(), getActivity()));
                    }
                }, 150);
                DetailsView detailsView = new DetailsView(session, calendarView.getCurrentDate().getMonth(), calendarView.getCurrentDate().getDay(), getActivity());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newArray = detailsView.getMsgArrayList();
                        todoSeqArray = detailsView.getTodoSeqArray();
                        todoArray.clear();
                        for (int i = 0; i < newArray.size(); i++) {
                            todoArray.add(newArray.get(i));
                        }
                        todoAdapter.setSeqArray(todoSeqArray);
                        todoAdapter.notifyDataSetChanged();

                    }
                }, 150);

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
                ScheduleWrite scheduleWrite = new ScheduleWrite(session, calendarView.getSelectedDate().getMonth(), calendarView.getSelectedDate().getDay(), msg, getActivity());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (scheduleWrite.getResult()) {
                            edtTodo.setText("");
                            btnCheck.setVisibility(View.INVISIBLE);
                            btnCheck.setEnabled(false);
                            btnPlus.setVisibility(View.VISIBLE);
                            btnPlus.setEnabled(true);
                            edtTodo.setEnabled(false);
                            edtTodo.setVisibility(View.INVISIBLE);
                            MonthsView monthsViewWrite = new MonthsView(session, calendarView.getSelectedDate().getYear(), calendarView.getSelectedDate().getMonth(), getActivity());
                            String newMsg = scheduleWrite.getMsg();
                            int newSeq = scheduleWrite.getSeq();
                            todoArray.add(new TodoView(newMsg));
                            todoSeqArray.add(newSeq);
                            todoAdapter.putSeqArray(newSeq);
                            todoAdapter.notifyDataSetChanged();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    calendarView.addDecorator(new DotDecorator(Color.RED, monthsViewWrite.getDate(), getActivity()));
                                }
                            }, 150);

                        } else {
                            Toast.makeText(getActivity(), "일정 작성 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 150);
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        calendarView.setSelectedDate(CalendarDay.today());
        MonthsView monthsView = new MonthsView(session, calendarView.getSelectedDate().getYear(), calendarView.getSelectedDate().getMonth(), getActivity());
        Log.d("dateValuetest", String.valueOf(calendarView.getSelectedDate().getYear() + "and" + calendarView.getSelectedDate().getMonth()));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                calendarView.addDecorator(new DotDecorator(Color.RED, monthsView.getDate(), getActivity()));
            }
        }, 150);

        DetailsView detailsView = new DetailsView(session, calendarView.getSelectedDate().getMonth(), calendarView.getSelectedDate().getDay(), getActivity());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newArray = detailsView.getMsgArrayList();
                todoSeqArray = detailsView.getTodoSeqArray();
                todoArray.clear();
                for (int i = 0; i < newArray.size(); i++) {
                    todoArray.add(newArray.get(i));
                }
                todoAdapter.setSeqArray(todoSeqArray);
                todoAdapter.notifyDataSetChanged();


            }
        }, 150);


    }

    ArrayList getDate() {
        ArrayList date = new ArrayList();
        date.add(calendarView.getSelectedDate().getMonth());
        date.add(calendarView.getSelectedDate().getDay());
        return date;
    }
}
