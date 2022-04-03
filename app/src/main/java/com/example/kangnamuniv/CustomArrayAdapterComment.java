package com.example.kangnamuniv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomArrayAdapterComment extends ArrayAdapter<BoardView> {
    public CustomArrayAdapterComment(@NonNull Context context, @NonNull ArrayList<BoardView> arrayList) {
        super(context, 0, arrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.commentlistitem, parent, false);
        }

        BoardView currentPosition = getItem(position);

        TextView writer = currentItemView.findViewById(R.id.tvCommentWriter);
        TextView msg = currentItemView.findViewById(R.id.tvCommentMsg);

        writer.setText(currentPosition.getTitle());
        msg.setText(currentPosition.getWriter());

        //BoardView 수정하기 힘들어서 좀 뒤죽박죽임 ㅠㅠ

       return currentItemView;
    }
}
