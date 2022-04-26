package com.example.assignment2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    long id = -1;
    EditText et1;
    EditText et2;
    private static OnDataChanged onDataChanged;

    public interface OnDataChanged {
        void onUpdate(long id, String title, String text);

        void onDelete(long id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);

        if (getIntent() != null) {
            if (getIntent().getExtras().containsKey("data")) {
                String[] data = getIntent().getExtras().getString("data").split(";");
                id = Long.parseLong(data[0]);
                if (data.length > 1 && data[1] != null) {
                    et1.setText(data[1]);
                }
                if (data.length > 2 && data[2] != null) {
                    et2.setText(data[2]);
                }
            }
        }

        findViewById(R.id.ib1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.ib2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDataChanged != null) {
                    onDataChanged.onDelete(id);
                }
                onBackPressed();
            }
        });

        findViewById(R.id.ib3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDataChanged != null) {
                    onDataChanged.onUpdate(id, et1.getText().toString(), et2.getText().toString());
                }
                onBackPressed();
            }
        });
    }

    public static void setOnDataChanged(OnDataChanged onDataChanged) {
        TaskActivity.onDataChanged = onDataChanged;
    }
}