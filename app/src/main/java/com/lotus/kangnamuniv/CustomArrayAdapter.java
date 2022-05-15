package com.lotus.kangnamuniv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<BoardView> {
    public CustomArrayAdapter(@NonNull Context context, @NonNull ArrayList<BoardView> arrayList) {
        super(context, 0, arrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.boardlistitem, parent, false);
        }

        BoardView currentPosition = getItem(position);

        TextView title = currentItemView.findViewById(R.id.tvBoardTitle);
        TextView writer = currentItemView.findViewById(R.id.tvBoardWriter);

        title.setText(currentPosition.getTitle());
        writer.setText(currentPosition.getWriter());

        return currentItemView;
    }
}
