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
    String TAG = "testCommentRecycler";
    private ArrayList<BoardView> list;
    CommentRecyclerViewAdapter(ArrayList<BoardView> list){
        Log.d(TAG, "생성자");
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "뷰홀더");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.boardlistitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "바인드홀더");
        holder.title.setText(list.get(position).getTitle());
        holder.writer.setText(list.get(position).getWriter());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "카운트");
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView writer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "응애");
            this.title = itemView.findViewById(R.id.tvBoardTitle);
            this.writer = itemView.findViewById(R.id.tvBoardWriter);
        }
    }
}
