package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskFragment extends Fragment implements DatePicker.OnDateChangedListener, TaskAdapter.OnClickListener, TaskActivity.OnDataChanged {

    private RecyclerView recyclerView;
    private DatePicker datePicker;
    private List<Task> data = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private Button addButton, todayButton, showButton;
    private TextView tv1, tv2;

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
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
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
                    tv2.setVisibility(View.VISIBLE);
                } else {
                    datePicker.setVisibility(View.VISIBLE);
                    showButton.setText("Hide");
                    tv2.setVisibility(View.GONE);
                }
                tv2.setText("Date selected: "+datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear());
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
    }

    @Override
    public void onUpdate(long id, String title, String text) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId()==id) {
                data.get(i).setTitle(title);
                data.get(i).setText(text);
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

    private void update() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).getTitle().isEmpty()&&!data.get(i).getText().isEmpty()) {
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
        }
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("todolist", array.toString()).apply();
        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
    }
}
