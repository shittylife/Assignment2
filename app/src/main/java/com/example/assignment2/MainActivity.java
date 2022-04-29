package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
        ,TimerFragment.DataPassListener{

    private FrameLayout layout;
    private BottomNavigationView navigationView;
    private final TaskFragment taskFragment = new TaskFragment();
    private final DailyexpenseFragment calendarFragment = new DailyexpenseFragment();
    private final TimerFragment timerFragment = new TimerFragment();
    private final TimerAddFragment timerAddFragment = new TimerAddFragment();
    private final clockrun clockrun = new clockrun();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.fragment);
        navigationView = findViewById(R.id.navigationBar);
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
            case R.id.Dailyexpense: {
                getSupportFragmentManager().beginTransaction().replace(layout.getId(), calendarFragment).commit();
                return true;
            }
            case R.id.timer: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, timerFragment).commit();
                return true;
            }
        }
        return false;
    }

    public void pass1(String name, long time1,long time2,long time3){

        Bundle bundle = new Bundle();
        bundle.putString("key", "FromTimerFragment");
        bundle.putString("name", name);
        bundle.putLong("time1", time1);
        bundle.putLong("time2", time2);
        bundle.putLong("time3", time3);
        clockrun.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, clockrun)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (navigationView.getSelectedItemId() == R.id.task)
            super.onBackPressed();
        else
            navigationView.setSelectedItemId(R.id.task);
    }
}

