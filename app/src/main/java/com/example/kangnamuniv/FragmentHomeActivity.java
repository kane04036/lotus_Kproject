package com.example.kangnamuniv;

import static android.content.Context.MODE_PRIVATE;
import static com.example.kangnamuniv.MyInfoCheckActivity.lecturelist;
import static com.example.kangnamuniv.MyInfoCheckActivity.seqlist;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.List;

public class FragmentHomeActivity extends Fragment {
    ListView listView;
    TextView resultCheck;
    public String res, data;
    String session;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false); /*현재 설정해놓은 layout을 view로 만드는 부분. fragmentProfileActivity 작성할때는
        그대로 복사하고 R.layout.fragment_profile 로 작성하면됨. 근데 무조건 return 해야해서 현재 onCreateView 메소드가 끝날때 return view 를 해줘야함*/

        sharedPreferences = this.getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", ""); //내가 세션키를 session으로 저장해놔서 데이터를 빼서 쓰려면 이렇게 코드 작성하면 데이터를 꺼내서 쓸 수 있음
        //나중에 서버로 데이터 보낼때 session키가 필요하면 위에처럼 작성해서 session 값 쓰면됨
        //근데 fragment에서는 오류가 나서 this.getActivity().getSharedPreferences(~~~~); <<이렇게 작성했는데 일반 activity에서는 getSharedPreferences(~~); 만 써도될 것임 안 되면 말해주세요,,,,

        resultCheck = view.findViewById(R.id.resultCheck); //fragment에서 findViewById를 하려면 앞에 view. 를 해줘야함
        listView = (ListView) view.findViewById(R.id.listViewLecture);/*findId를 하는 이유는 layout에 있는 아이템들을 자바 코드와 연결시켜서
         이 코드 내에서 그 아이템을 처리할 수 있도록 하기 위해서! 여기서 find 안 하면 그 아이템 사용 불가함*/
        CustomAdapter customAdapter = new CustomAdapter(getActivity()); //list뷰를 만들고나서 어뎁터를 만들어서 현재 fragmenthome과 리스트뷰를 연결시켜줘야
        listView.setAdapter(customAdapter);

        //밑에 코드는 리스트뷰를 터치했을때 동작하는 코드
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getContext(), seqlist[position], Toast.LENGTH_SHORT).show();

                resultCheck.append(session);
                resultCheck.append(seqlist[position]); //이것들은 그냥 session 값이랑 강의 번호가 잘 맞는지 확인하려고 해놓은 거고 나중에 삭제할 예정

                Intent intent = new Intent(getActivity(), BoardActivity.class);/*다른 액티비티로 전환하기 위한 코드 fragment내부에서 activity로 이동할때는 됨
                new Intent(getActivity(), 이동할 액티비티 이름.class) 이렇게 작성하면됨*/
                //activity에서 activity로 이동할때는 new Intent(getApplicationContext(), 이동할액티비티이름.class);
                intent.putExtra("lecture", lecturelist[position]); //전환할 화면으로 넘어갈때 같이 가지고 갈 데이터들을 이런 형태로 추가할 수 있음.함
                // 게시판 메인홈으로 넘어갈때 리스트뷰에 눌린 강의 이름과 강의 번호, 세션키를 같이 넘기려고 저렇게 세 개 작성
                intent.putExtra("session",session);
                intent.putExtra("seq",seqlist[position]);
                startActivity(intent);//이렇게 startActivity해줘야 화면 전환이 됨.
                //전환할 화면와 자바코드가 이어져 있고 manifests 에 새로운 액티비티를 등록해줘야 정상작동함.
                //액티비티 자바 코드에 setcontent(R.layout.래이아웃이름); 해줘야 실행했을때 그 레이아웃임 정상적으로 보임.
            }
        });


        return view;
    }

    //밑에 부분은 내가 설명할 정도로 잘 알지 못해서 그냥 구글에 listview customadapter 검색해서 찾아보는 게,,, 더 편할 것임...
    // 내가 설명을 못 하겠는 거지 코드 작성하다가 막히는 건 수정해줄 수 있으니까 하다가 모르겠으면 물어보삼!!!
    private class CustomAdapter extends BaseAdapter {
        Context mContext;

        public CustomAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return lecturelist.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = View.inflate(mContext, R.layout.listitem, null);
            TextView tvlecturelist = itemView.findViewById(R.id.tvLectureList);

            tvlecturelist.setText(lecturelist[position]);

            return itemView;
        }
    }




}



