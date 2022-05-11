package com.example.kangnamuniv;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.nio.channels.InterruptedByTimeoutException;

public class FragmentProfileActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//      과목갱신
        TextView TextView1 = (TextView) view.findViewById(R.id.pr_lec_renew);

        TextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefLecture = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor1 = prefLecture.edit();
                editor1.remove("lecture");
                editor1.commit();
                SharedPreferences prefSeq = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor2 = prefSeq.edit();
                editor2.remove("seq");
                editor2.commit();

                Intent intent = new Intent(getActivity(),MyInfoCheckActivity.class);
                startActivity(intent);

            }
        });

//      로그아웃
        TextView btnLogout;
        btnLogout = view.findViewById(R.id.pr_logout);
        String URL = "http://34.64.49.11/logout";//각 상황에 맞는 서버 url

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefLecture = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor1 = prefLecture.edit();
                editor1.remove("lecture");
                editor1.commit();
                SharedPreferences prefSeq = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor2 = prefSeq.edit();
                editor2.remove("seq");
                editor2.commit();
                SharedPreferences prefLogout = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor3 = prefLogout.edit();
                editor3.clear();
                editor3.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


//                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
//
//                String URL = "http://34.64.49.11/logout";
//
//
//                JsonObjectRequest logoutRequest = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String res = response.getString("res"); //동일
//                            Log.d("testLogout", res);
//                            if (res.contains("Success")) {
//
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                });
//
//                requestQueue.add(logoutRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
            }
        });


//      회원탈퇴

        return view;
    }
}
