package com.example.kangnamuniv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class TodoRecyclerVeiwAdapter extends RecyclerView.Adapter<TodoRecyclerVeiwAdapter.MyViewHolder> {
    private ArrayList<TodoView> list;
    private ArrayList<Integer> seqArray;
    ArrayList<Integer> checkedList = new ArrayList();
    Context context;
    String TAG = "test";
    String session;

    TodoRecyclerVeiwAdapter(ArrayList<TodoView> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        session = sharedPreferences.getString("session", "");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private EditText editText;
        private ImageButton btnTodoMore;
        private ImageButton btnTodoModify;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBox = itemView.findViewById(R.id.todoCheckbox);
            this.editText = itemView.findViewById(R.id.todoEdit);
            this.btnTodoMore = itemView.findViewById(R.id.btnTodoMore);
            this.btnTodoModify = itemView.findViewById(R.id.btnTodoModify);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todoitem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        checkedList = getIntegerArrayPref(context, "checkedlist");
        holder.checkBox.setChecked(false);
        holder.editText.setPaintFlags(0);
        holder.editText.setTextColor(Color.BLACK);
        holder.btnTodoModify.setEnabled(false);
        holder.editText.setText(list.get(position).getMsg());
        if (checkedList.contains(seqArray.get(position))) {
            holder.checkBox.setChecked(true);
            holder.editText.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.editText.setTextColor(Color.GRAY);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()) {
                    holder.editText.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.editText.setTextColor(Color.GRAY);

                    if (!checkedList.contains(seqArray.get(position)))
                        checkedList.add(seqArray.get(position));

                } else {
                    holder.editText.setPaintFlags(0);
                    holder.editText.setTextColor(Color.BLACK);
                    if (checkedList.contains(seqArray.get(position)))
                        checkedList.remove(checkedList.indexOf(seqArray.get(position)));
                }

                setIntegerArrayPref(context, "checkedlist", checkedList);

            }
        });

        holder.btnTodoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.todomenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.todoModify:
                                holder.editText.setEnabled(true);
                                holder.editText.requestFocus();
                                holder.btnTodoMore.setVisibility(View.INVISIBLE);
                                holder.btnTodoMore.setEnabled(false);
                                holder.btnTodoModify.setVisibility(View.VISIBLE);
                                holder.btnTodoModify.setEnabled(true);
                                holder.btnTodoModify.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String msg = holder.editText.getText().toString();
//                                        Upschedule upschedule = new Upschedule(session, seqArray.get(position), msg, context);
                                        {
                                            RequestQueue requestQueue = Volley.newRequestQueue(context);

                                            String URL = "http://34.64.49.11/upschedule";

                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("session", session);
                                                jsonObject.put("seq", seqArray.get(position));
                                                jsonObject.put("msg", msg);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            JsonObjectRequest scheduleWriteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String res = response.getString("res"); //동일


                                                        if (res.contains("SUCCESS")) {
                                                            holder.editText.setEnabled(false);
                                                            holder.btnTodoModify.setVisibility(View.INVISIBLE);
                                                            holder.btnTodoModify.setEnabled(false);
                                                            holder.btnTodoMore.setVisibility(View.VISIBLE);
                                                            holder.btnTodoMore.setEnabled(true);
                                                        } else {
                                                            Toast.makeText(context, "수정할 수 없습니다.", Toast.LENGTH_SHORT).show();
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

                                    }


                                });
                                break;

                            case R.id.todoDelete:
//                                Scheduledelete scheduledelete = new Scheduledelete(session, seqArray.get(position), context);
                                RequestQueue requestQueue = Volley.newRequestQueue(context);

                                String URL = "http://34.64.49.11/scheduledelete";

                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("session", session);
                                    jsonObject.put("seq", seqArray.get(position));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                JsonObjectRequest scheduleWriteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String res = response.getString("res"); //동일


                                            if (res.contains("Success")) {

                                                list.remove(position);
                                                seqArray.remove(position);
                                                notifyDataSetChanged();


                                            } else {
                                                Toast.makeText(context, "삭제할 수 없습니다.", Toast.LENGTH_SHORT).show();

                                            }
                                            Log.d("testDelete", res);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });

                                requestQueue.add(scheduleWriteRequest); //마지막에 이거 필수!!! jsonobjectRequest 변수명 넣어주면됨

                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    void putSeqArray(int seq) {
        seqArray.add(seq);
    }

    void setSeqArray(ArrayList seq) {
        seqArray = seq;
    }

    public void setIntegerArrayPref(Context context, String key, ArrayList<Integer> values) {

        SharedPreferences prefsInt = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefsInt.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }


        editor.apply();
    }

    public ArrayList<Integer> getIntegerArrayPref(Context context, String key) {

        SharedPreferences prefsInt = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefsInt.getString(key, null);
        ArrayList urls = new ArrayList();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    int url = a.optInt(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    private boolean upSchedule(String msg, int seq) {
        final boolean[] result = new boolean[1];
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "http://34.64.49.11/upschedule";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session", session);
            jsonObject.put("seq", seq);
            jsonObject.put("msg", msg);

        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest scheduleWriteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res"); //동일


                    if (res.contains("SUCCESS")) {
                        result[0] = true;
                    } else {
                        result[0] = false;
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

        return result[0];

    }


}

