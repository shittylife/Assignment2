package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        button.setText("testButton");

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                button.setText("testButton1");
            }
        });
    }
}

