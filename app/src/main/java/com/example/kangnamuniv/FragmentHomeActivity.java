package com.example.kangnamuniv;

import static com.example.kangnamuniv.MyInfoCheckActivity.lecturelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class FragmentHomeActivity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView listView;
        TextView textView;
        textView = view.findViewById(R.id.tvHome);

        listView = (ListView) view.findViewById(R.id.listView);
        /*CustomAdapter customAdapter = new CustomAdapter(getActivity());
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getContext(), strlist[position], Toast.LENGTH_SHORT).show();
            }
        });*/

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lecturelist);

        listView.setAdapter(listViewAdapter);

        return view;
    }

    private class CustomAdapter extends BaseAdapter {
        Context mContext;

        public CustomAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return lecturelist.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = View.inflate(mContext, R.layout.listitem, null);
            TextView tvlecturelist = itemView.findViewById(R.id.tvLectureList);

            tvlecturelist.setText(lecturelist[position]);

            return itemView;
        }
    }


}



