package com.example.kangnamuniv;

import android.content.Context;
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
    private ArrayList<Integer> seq;
    Context context;

    TodoRecyclerVeiwAdapter(ArrayList<TodoView> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView todoTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBox = itemView.findViewById(R.id.todoCheckbox);
//            this.todoTv = itemView.findViewById(R.id.todoTv);

//            todoTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                }
//            });

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
        Log.d("testTodoValue", list.get(position).getMsg());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}

