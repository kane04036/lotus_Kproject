package com.lotus.kangnamuniv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

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
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.todoitem, parent, false);
        }

        TodoView currentPosition = getItem(position);

        CheckBox todoCheckbox = currentItemView.findViewById(R.id.todoCheckbox);

        return currentItemView;
    }
}
