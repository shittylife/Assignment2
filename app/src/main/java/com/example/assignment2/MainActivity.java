package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private FrameLayout layout;
    private final TaskFragment taskFragment = new TaskFragment();
    private final CalendarFragment calendarFragment = new CalendarFragment();
    private final TimerFragment timerFragment = new TimerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.fragment);
        BottomNavigationView navigationView = findViewById(R.id.navigationBar);
        navigationView.setOnItemSelectedListener(this);
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
}

