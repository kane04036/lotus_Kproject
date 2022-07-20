package com.lotus.kangnamuniv;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {
    String TAG = "test";
    private ArrayList<BoardView> list;
    ArrayList<Integer> seqArray = new ArrayList<>();
    Context context;
    EditText edtCommentReport;
    int Bnumber;


    CommentRecyclerViewAdapter(ArrayList<BoardView> list, Context context, int Bnumber) {
        Log.d(TAG, "생성자");
        this.context = context;
        this.list = list;
        this.Bnumber = Bnumber;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentlistitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.writer.setText(list.get(position).getWriter());
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.commentmoremenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.commentDelete:
                                SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
                                String session = sharedPreferences.getString("session", "");

                                RequestQueue requestQueueCommentDel = Volley.newRequestQueue(context);

                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("session", session);
                                    jsonObject.put("Cnumber", seqArray.get(position));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                String URL = "https://k-project-jgukj.run.goorm.io/commentdelete";//각 상황에 맞는 서버 url


                                JsonObjectRequest commentDelete = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String res = response.getString("res");
                                            Log.d("test", "onResponse: "+ res);
                                            if (res.contains("Success")) {
                                                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                                list.remove(position);
                                                seqArray.remove(position);
                                                notifyDataSetChanged();

                                            } else {
                                                Toast.makeText(context, "댓글 작성자만 삭제할 수 있습니다", Toast.LENGTH_SHORT).show();
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


                                requestQueueCommentDel.add(commentDelete);

                                break;
                            case R.id.commentReport:
                                edtCommentReport = new EditText(context);
                                AlertDialog.Builder dialogReport = new AlertDialog.Builder(context);
                                dialogReport.setTitle("댓글 신고");

                                dialogReport.setView(edtCommentReport);
                                dialogReport.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        commentReport();
                                    }
                                });
                                dialogReport.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                dialogReport.show();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView writer;
        private ImageButton btnMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tvCommentMsg);
            this.writer = itemView.findViewById(R.id.tvCommentWriter);
            this.btnMore = itemView.findViewById(R.id.btnCommentMore);
        }
    }

    void putSeqArray(int seq) {
        seqArray.add(seq);
    }

    void setSeqArray(ArrayList seq) {
        seqArray = seq;
    }

    void commentReport(){
        RequestQueue QueueReport = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", 1);
            jsonObject.put("seq", Bnumber);
            jsonObject.put("msg", edtCommentReport.getText().toString());

            Log.d(TAG, "postReport: " + edtCommentReport.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String URL = "https://k-project-jgukj.run.goorm.io/report";//각 상황에 맞는 서버 url


        JsonObjectRequest boardDeleteRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String res = response.getString("res");
                    Log.d("test : resport ", res);
                    if(res.equals("SUCCESS")){
                        Toast.makeText(context, "신고 되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
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
        QueueReport.add(boardDeleteRequest);



    }


}
