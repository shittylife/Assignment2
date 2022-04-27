package com.example.assignment2;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CalendarFragment extends Fragment {

    private List<Task> data = new ArrayList<>();
    ImageButton iv2;
    TextView tv5;
    Calendar2Fragment calendar2Fragment = new Calendar2Fragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        iv2 = view.findViewById(R.id.iv2);

        try {
            JSONArray array = new JSONArray(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("C", "[]"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Task task = new Task(object.getLong("id"), object.getInt("day"), object.getInt("month"), object.getInt("year"), object.getString("title"), object.getString("text"));
                data.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment,calendar2Fragment );
                transaction.commit();
            }
        });


        tv5.getText()


        return view;
    }
}
