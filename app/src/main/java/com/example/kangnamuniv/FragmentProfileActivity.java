package com.example.kangnamuniv;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class FragmentProfileActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnLogout;
        btnLogout = view.findViewById(R.id.btnLogout);
        String URL = "http://34.64.49.11/logout";//각 상황에 맞는 서버 url


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

                String URL = "http://34.64.49.11/logout";


                JsonObjectRequest logoutRequest = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("res"); //동일
                            Log.d("testLogout", res);
                            if (res.contains("Success")) {

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

                requestQueue.add(logoutRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨
            }
        });



        return view;
    }
}
