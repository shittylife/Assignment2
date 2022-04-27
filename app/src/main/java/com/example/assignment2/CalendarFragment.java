package com.example.assignment2;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CalendarFragment extends Fragment {
    Integer selected_pos = -1;
    private List<Task> data = new ArrayList<>();
    ImageButton iv2;
    TextView tv5;
    ListView lv1;
    String s1[] ,s2[],s3[] ,s4[];
    Calendar2Fragment calendar2Fragment = new Calendar2Fragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        iv2 = view.findViewById(R.id.iv2);
        lv1 = view.findViewById(R.id.lv1);




        ArrayList<String> arr = new ArrayList<String>(Arrays.asList(s1));
        ArrayList<String> arr1 = new ArrayList<String>(Arrays.asList(s2));
        ArrayList<String> arr2 = new ArrayList<String>(Arrays.asList(s3));
        ArrayList<String> arr3 = new ArrayList<String>(Arrays.asList(s4));
        Calendarlist adapter = new Calendarlist(getActivity(),arr,arr1,arr2,arr3);


        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                selected_pos = position;
            }

        });
        adapter.notifyDataSetChanged();

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment,calendar2Fragment );
                transaction.commit();
            }
        });
       // money = data;
        //count = money
       // tv5.setText();


        return view;
    }
}
