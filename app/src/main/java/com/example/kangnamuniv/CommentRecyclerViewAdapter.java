package com.example.kangnamuniv;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>{
    String TAG = "test";
    private ArrayList<BoardView> list;
    CommentRecyclerViewAdapter(ArrayList<BoardView> list){
        Log.d(TAG, "생성자");
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.writer.setText(list.get(position).getWriter());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView writer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tvCommentMsg);
            this.writer = itemView.findViewById(R.id.tvCommentWriter);
        }
    }
}
