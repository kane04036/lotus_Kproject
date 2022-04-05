package com.example.kangnamuniv;

import static android.content.Context.MODE_PRIVATE;
import static com.example.kangnamuniv.MyInfoCheckActivity.lecturelist;
import static com.example.kangnamuniv.MyInfoCheckActivity.seqlist;


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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomeActivity extends Fragment {
    ListView listView;
    String session;
    SharedPreferences sharedPreferences;
    PreferenceManagers preferenceManagers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false); /*현재 설정해놓은 layout을 view로 만드는 부분. fragmentProfileActivity 작성할때는
        그대로 복사하고 R.layout.fragment_profile 로 작성하면됨. 근데 무조건 return 해야해서 현재 onCreateView 메소드가 끝날때 return view 를 해줘야함*/

        sharedPreferences = this.getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", ""); //내가 세션키를 session으로 저장해놔서 데이터를 빼서 쓰려면 이렇게 코드 작성하면 데이터를 꺼내서 쓸 수 있음
        //나중에 서버로 데이터 보낼때 session키가 필요하면 위에처럼 작성해서 session 값 쓰면됨
        //근데 fragment에서는 오류가 나서 this.getActivity().getSharedPreferences(~~~~); <<이렇게 작성했는데 일반 activity에서는 getSharedPreferences(~~); 만 써도될 것임 안 되면 말해주세요,,,,


        listView = (ListView) view.findViewById(R.id.listViewLecture);

        //ArrayList<String> arrayList = preferenceManagers.getStringArrayPref(this.getActivity(),"lectures");
        //Log.d("testShared", arrayList.get(3));


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, lecturelist);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //String selected_item = (String)adapterView.getItemAtPosition(position);
                Log.d("test6", lecturelist.get(position));
                Log.d("test6", session);
                Log.d("test6", String.valueOf(seqlist.get(position)));


                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("lecture", lecturelist.get(position));
                intent.putExtra("session", session);
                intent.putExtra("Lnumber", seqlist.get(position));
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}



