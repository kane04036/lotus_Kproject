package com.example.kangnamuniv;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentMainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentCalendarActivity fragmentCalendarActivity = new FragmentCalendarActivity();
    private FragmentHomeActivity fragmentHomeActivity = new FragmentHomeActivity();
    private FragmentProfileActivity fragmentProfileActivity = new FragmentProfileActivity();
    public String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentHomeActivity).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());


    }

    private class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.homeItem:
                    transaction.replace(R.id.frameLayout, fragmentHomeActivity).commitAllowingStateLoss();
                    break;
                case R.id.scheduleItem:
                    transaction.replace(R.id.frameLayout, fragmentCalendarActivity).commitAllowingStateLoss();
                    break;
                case R.id.profileItem:
                    transaction.replace(R.id.frameLayout, fragmentProfileActivity).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }


}
