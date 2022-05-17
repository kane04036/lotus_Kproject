package com.lotus.kangnamuniv;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class FragmentProfileActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentprofilenew, container, false);

        TextView btnLogout;
        btnLogout = view.findViewById(R.id.btnLogout);
        String URL = "http://34.64.49.11/logout";//각 상황에 맞는 서버 url

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE); //이건 안드로이드 어플 내에 약간의 데이터를 저장해놓은 거. 필요할때 데이터 꺼내서 쓸 수 있음
        String nickname = sharedPreferences.getString("nickname", "");



        TextView TextView1 = (TextView) view.findViewById(R.id.EditText01);
        TextView tvNickname = view.findViewById(R.id.EditText0);
        tvNickname.setText(nickname);
        TextView tvNickChange = view.findViewById(R.id.EditText02);
        TextView tvPWChange = view.findViewById(R.id.EditText03);
        TextView tvInfo = view.findViewById(R.id.Btn_1);
        TextView tvQnA = view.findViewById(R.id.Btn_2);

        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
            }
        });

        tvQnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QnAActivity.class);
                startActivity(intent);
            }
        });
        tvNickChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeNicknameActivity.class);
                startActivity(intent);
            }
        });

        TextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog dialog = builder.setMessage("확인을 누르는 즉시 게시판 목록이 지워집니다. 확인을 누르고 바로 갱신을 해주세요.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences prefLecture = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor1 = prefLecture.edit();
                        editor1.remove("lecture");
                        editor1.commit();
                        SharedPreferences prefSeq = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor2 = prefSeq.edit();
                        editor2.remove("seq");
                        editor2.commit();

                        Intent intent = new Intent(getActivity(),MyInfoCheckActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("취소", null).create();
                dialog.show();


            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefLecture = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor1 = prefLecture.edit();
                editor1.remove("lecture");
                editor1.commit();
                SharedPreferences prefSeq = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor2 = prefSeq.edit();
                editor2.remove("seq");
                editor2.commit();
                SharedPreferences prefLogout = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor3 = prefLogout.edit();
                editor3.clear();
                editor3.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        tvPWChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePWActivity.class);
                startActivity(intent);

            }
        });



        return view;
    }
}
