package com.example.assignment2;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskFragment extends Fragment implements DatePicker.OnDateChangedListener, TaskAdapter.OnClickListener, TaskActivity.OnDataChanged {

    private FrameLayout layout;
    private RecyclerView recyclerView;
    private DatePicker datePicker;
    private List<Task> data = new ArrayList<>();
    private ImageView iv1;
    private TaskAdapter taskAdapter;
    private MediaPlayer mediaPlayer;
    private Button addButton, todayButton, showButton;
    private TextView tv1, tv2;
    boolean mute = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data.clear();
        try {
            JSONArray array = new JSONArray(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("todolist", "[]"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Task task = new Task(object.getLong("id"), object.getInt("day"), object.getInt("month"), object.getInt("year"), object.getString("title"), object.getString("text"));
                data.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        layout = view.findViewById(R.id.layout);
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        iv1 = view.findViewById(R.id.iv1);
        datePicker = view.findViewById(R.id.datePicker);
        datePicker.setOnDateChangedListener(this);
        taskAdapter = new TaskAdapter(data);
        taskAdapter.updateDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
        taskAdapter.setOnClickListener(this);
        TaskActivity.setOnDataChanged(this);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(taskAdapter);
        addButton = view.findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = System.currentTimeMillis();
                startActivity(new Intent(getContext(), TaskActivity.class).putExtra("data", id+";;;"));
                data.add(new Task(id, datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear()));
            }
        });
        todayButton = view.findViewById(R.id.btnToday);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            }
        });
        showButton = view.findViewById(R.id.btnShow);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datePicker.getVisibility() == View.VISIBLE) {
                    datePicker.setVisibility(View.GONE);
                    showButton.setText("Show");
                } else {
                    datePicker.setVisibility(View.VISIBLE);
                    showButton.setText("Hide");
                }
            }
        });
        view.findViewById(R.id.btnInstruct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Instruction")
                        .setMessage("Step 1: Choose your task date from the DatePicker or \"Today\" Button.\n\nStep 2: Click \"Add Task\" Button to add task.\n\nStep 3: Fill in your task title and task and save.\n\nStep 4: Click the delete icon when you have done your task.\n\nOptional 1: You can hide the DatePicker by clicking right bottom \"Show/Hide\" button.\n\nOptional 2: You can mute the BGM by clicking top right volume button.\n\nThe BGM will be played when no task is found at that time.")
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_popup_reminder)
                        .show();
            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskAdapter.getItemCount()>0||!mute) {
                    iv1.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_volume_off_24));
                    pauseMusic();
                } else {
                    iv1.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_volume_up_24));
                    playMusic();
                }
                mute = !mute;
            }
        });
        return view;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        taskAdapter.updateDate(i2,i1,i);
    }

    @Override
    public void onDeleteClick(long s) {
        delete(s);
    }

    @Override
    public void onItemClick(long s) {
        Task item;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId()==s) {
                item = data.get(i);
                startActivity(new Intent(getContext(), TaskActivity.class).putExtra("data", item.getId()+";"+item.getTitle()+";"+item.getText()));
                break;
            }
        }
    }

    @Override
    public void onItemUpdate(int i) {
        if (tv1 != null)
            if (i > 0)
                tv1.setVisibility(View.GONE);
            else
                tv1.setVisibility(View.VISIBLE);
        String[] bg = {"1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg","9.jpg","10.jpg","11.jpg","12.jpg"};
        try {
            if (layout!=null&&getContext()!=null)
                layout.setBackground(Drawable.createFromStream(getContext().getAssets().open(bg[datePicker.getMonth()]),null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv2.setText("Date selected: "+datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear());
        if (tv1!=null&&tv2!=null)
            switch (datePicker.getMonth()) {
                case 4:
                case 11: {
                    tv1.setTextColor(Color.WHITE);
                    tv2.setTextColor(Color.WHITE);
                    break;
                }
                default:
                    tv1.setTextColor(Color.GRAY);
                    tv2.setTextColor(Color.GRAY);
            }
        if (i==0&&!mute)
            playMusic();
        else
            pauseMusic();
    }

    @Override
    public void onUpdate(long id, String title, String text) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId()==id) {
                if (title.length()>0||text.length()>0) {
                    data.get(i).setTitle(title);
                    data.get(i).setText(text);
                } else {
                    data.remove(i);
                }
                break;
            }
        }
        taskAdapter.updateDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
        update();
    }

    @Override
    public void onDelete(long id) {
        delete(id);
    }

    private void delete(long s) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId()==s) {
                data.remove(i);
            }
        }
        taskAdapter.updateDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (datePicker != null && taskAdapter != null) {
            taskAdapter.updateDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseMusic();
    }

    private void update() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("id", data.get(i).getId());
                object.put("year", data.get(i).getYear());
                object.put("month", data.get(i).getMonth());
                object.put("day", data.get(i).getDay());
                object.put("title", data.get(i).getTitle());
                object.put("text", data.get(i).getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(object);
        }
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("todolist", array.toString()).apply();
        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
    }

    private void playMusic() {
        if (mediaPlayer==null||!mediaPlayer.isPlaying()) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.music);
            mediaPlayer.start();
        }
    }

    private void pauseMusic() {
        try {
            if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
