package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private FrameLayout layout;
    private final TaskFragment taskFragment = new TaskFragment();
    private final CalendarFragment calendarFragment = new CalendarFragment();
    private final TimerFragment timerFragment = new TimerFragment();
    private final TimerAddFragment timerAddFragment = new TimerAddFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.fragment);
        BottomNavigationView navigationView = findViewById(R.id.navigationBar);
        navigationView.setOnItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(layout.getId(), taskFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.task: {
                getSupportFragmentManager().beginTransaction().replace(layout.getId(), taskFragment).commit();
                return true;
            }
            case R.id.calendar: {
                getSupportFragmentManager().beginTransaction().replace(layout.getId(), calendarFragment).commit();
                return true;
            }
            case R.id.timer: {
                getSupportFragmentManager().beginTransaction().replace(layout.getId(), timerFragment).commit();
                return true;
            }
        }
        return false;
    }
    /*
    public void passDatafromFirst2Second(String name, int time, int shortbreak, int longbreak) {
        Log.d("Louis", "arrived passDatafromFirst2Second");
        Bundle bundle = new Bundle();
        // store 'data' passed by 1st Fragment to
        // SecondFragment.DATA_RECEIVE class variable
        bundle.putString("key", "FromTimerMenuFragment1");
        bundle.putString("name", name);
        bundle.putInt("sid", time);
        bundle.putInt("gender", shortbreak);
        bundle.putInt("address", longbreak);
        // assign bundle to 2nd fragment
        getSupportFragmentManager().beginTransaction().replace(layout.getId(), TimerRunFragment).commit();
    }*/







}

