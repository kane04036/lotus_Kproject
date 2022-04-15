package com.example.kangnamuniv;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentCalendarActivity extends Fragment {
    MaterialCalendarView calendarView;
    ImageButton btnPlus, btnCheck;
    EditText edtTodo;
    String fullDate;
    int year, month, day;
    String session, msg;
    SharedPreferences sharedPreferences;
    int newMonth, newDay;
    ArrayAdapter<String> arrayAdapter;
    ListView calendarListView;
    ArrayList<String> msgArrayList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarview);
        btnPlus = view.findViewById(R.id.btnPlus);
        btnCheck = view.findViewById(R.id.btnCheck);
        edtTodo = view.findViewById(R.id.edtTodo);
        btnCheck.setEnabled(false);
        calendarListView = view.findViewById(R.id.calendarListView);

        sharedPreferences = this.getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //Log.d("testMonthChange", String.valueOf(calendarView.getCurrentDate()));
                fullDate = String.valueOf(calendarView.getSelectedDate());
                DateParsing dateParsing = new DateParsing(fullDate);
                newMonth = dateParsing.getMonth();
                newDay = dateParsing.getDay();
                DetailsView detailsView = new DetailsView(session, newMonth, newDay, getActivity());
                //msgArrayList = detailsView.getMsgArrayList();
//                for(int i = 0; i<detailsView.msgArrayList.size(); i++)
//                    Log.d("testMsgArray", msgArrayList.get(i));
//                msgArrayList = detailsView.getMsgArrayList();
//                arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 ,msgArrayList);
//                calendarListView.setAdapter(arrayAdapter);
            }
        });
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Log.d("testMonthChange", String.valueOf(calendarView.getCurrentDate()));
                String newDate = String.valueOf(calendarView.getCurrentDate());
                DateParsing dateParsing = new DateParsing(newDate);
                MonthsView newMonth = new MonthsView(session, dateParsing.getMonth(), getActivity());
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
                ScheduleWrite scheduleWrite = new ScheduleWrite(session, newMonth, newDay, msg, getActivity());
                if(scheduleWrite.getResult()){
                    edtTodo.setText("");
                    btnCheck.setVisibility(View.INVISIBLE);
                    btnCheck.setEnabled(false);
                    btnPlus.setVisibility(View.VISIBLE);
                    btnPlus.setEnabled(true);
                    edtTodo.setEnabled(false);
                    edtTodo.setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        calendarView.setSelectedDate(CalendarDay.today());
        fullDate = String.valueOf(CalendarDay.today());
        DateParsing todayDateParsing = new DateParsing(fullDate);
        MonthsView monthsView = new MonthsView(session, todayDateParsing.getMonth(), this.getActivity());
    }
}
