package com.example.kangnamuniv;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        overridePendingTransition(R.anim.none, R.anim.none);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isFinishing()){
            overridePendingTransition(R.anim.none, R.anim.none);
        }
    }
}
