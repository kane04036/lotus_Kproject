package com.example.kangnamuniv;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomTodoArrayAdapter extends ArrayAdapter<TodoView> {
    public CustomTodoArrayAdapter(@NonNull Context context, @NonNull ArrayList<TodoView> arrayList) {
        super(context, 0, arrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.todolistitem, parent, false);
        }

        TodoView currentPosition = getItem(position);

        TextView todoTv = currentItemView.findViewById(R.id.todoTv);
        CheckBox todoCheckbox = currentItemView.findViewById(R.id.todoCheckbox);
        todoTv.setText(currentPosition.getMsg());

        return currentItemView;
    }
}
