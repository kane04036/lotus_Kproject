package com.example.kangnamuniv;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {
    String TAG = "test";
    private ArrayList<BoardView> list;
    ArrayList<Integer> seqArray = new ArrayList<>();
    Context context;
    Handler handler = new Handler();


    CommentRecyclerViewAdapter(ArrayList<BoardView> list, Context context) {
        Log.d(TAG, "생성자");
        this.context = context;
        this.list = list;
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
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + seqArray.get(position));
                Commentdelete commentdelete = new Commentdelete(seqArray.get(position), context);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(commentdelete.getResult()){
                            seqArray.remove(position);
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                }, 150);

//                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
//                dlg.setMessage("삭제하시겠습니까?");
//                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Commentdelete commentdelete = new Commentdelete(seqArray.get(position), context);
//                    }
//                });
//
//                dlg.show();
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
        private ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tvCommentMsg);
            this.writer = itemView.findViewById(R.id.tvCommentWriter);
            this.btnDelete = itemView.findViewById(R.id.btnCommentDelete);
        }
    }

    void putSeqArray(int seq) {
        seqArray.add(seq);
    }

    void setSeqArray(ArrayList seq) {
        seqArray = seq;
    }
}
