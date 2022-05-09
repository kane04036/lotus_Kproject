package com.example.kangnamuniv;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TodoRecyclerVeiwAdapter extends RecyclerView.Adapter<TodoRecyclerVeiwAdapter.MyViewHolder> {
    private ArrayList<TodoView> list;
    private ArrayList<Integer> seqArray;
    Context context;
    String TAG = "test";

    TodoRecyclerVeiwAdapter(ArrayList<TodoView> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBox = itemView.findViewById(R.id.todoCheckbox);

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.checkBox.setText(list.get(position).getMsg());
        holder.checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked()) {
                    holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.checkBox.setTextColor(Color.GRAY);
                }

                else {
                    holder.checkBox.setPaintFlags(0);
                    holder.checkBox.setTextColor(Color.BLACK);

                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    void putSeqArray(int seq){
        seqArray.add(seq);
    }
    void setSeqArray(ArrayList seq){
        seqArray = seq;
    }

}

