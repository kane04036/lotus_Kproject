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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("TEST", Context.MODE_PRIVATE);
        session = sharedPreferences.getString("session", "");

        resultCheck = view.findViewById(R.id.resultCheck);
        listView = (ListView) view.findViewById(R.id.listViewLecture);
        CustomAdapter customAdapter = new CustomAdapter(getActivity());
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getContext(), seqlist[position], Toast.LENGTH_SHORT).show();

                resultCheck.append(session);
                resultCheck.append(seqlist[position]);

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("session", session);
                    jsonObject.put("Lnumber", seqlist[position]);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                String URL = "http://34.64.49.11/mainview";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonResponse = new JSONObject();

                        try {
                            res = jsonResponse.getString("res");
                            data = jsonResponse.getString("data");
                            resultCheck.append(res);
                            resultCheck.append(data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                requestQueue.add(jsonObjectRequest);


                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("res", res);
                intent.putExtra("data", data);
                intent.putExtra("lecture", lecturelist[position]);
                startActivity(intent);
            }
        });


        return view;
    }

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



