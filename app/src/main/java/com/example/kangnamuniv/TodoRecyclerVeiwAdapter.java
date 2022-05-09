package com.example.kangnamuniv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoRecyclerVeiwAdapter extends RecyclerView.Adapter<TodoRecyclerVeiwAdapter.MyViewHolder> {
    private ArrayList<TodoView> list;
    private ArrayList<Integer> seqArray;
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
        holder.btnTodoModify.setEnabled(false);
        holder.editText.setText(list.get(position).getMsg());
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
                    holder.editText.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.editText.setTextColor(Color.GRAY);
                }

                else {
                    holder.editText.setPaintFlags(0);
                    holder.editText.setTextColor(Color.BLACK);

                }

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
//                                        ScheduleWrite scheduleWrite = new ScheduleWrite(session, )
//                                        int month = ((FragmentCalendarActivity)FragmentCalendarActivity.context_main).calendarView.getSelectedDate().getMonth();

                                        String msg = holder.editText.getText().toString();
                                        Upschedule upschedule = new Upschedule(session, seqArray.get(position), msg, context);
                                        holder.editText.setEnabled(false);
                                        holder.btnTodoModify.setVisibility(View.INVISIBLE);
                                        holder.btnTodoModify.setEnabled(false);
                                        holder.btnTodoMore.setVisibility(View.VISIBLE);
                                        holder.btnTodoMore.setEnabled(true);

                                    }
                                });
                                break;

                            case R.id.todoDelete:
                                Scheduledelete scheduledelete = new Scheduledelete(session, seqArray.get(position), context);
                                list.remove(position);
                                seqArray.remove(position);
                                notifyDataSetChanged();
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

    void putSeqArray(int seq){
        seqArray.add(seq);
    }
    void setSeqArray(ArrayList seq){
        seqArray = seq;
    }

}

