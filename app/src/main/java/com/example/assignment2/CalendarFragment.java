package com.example.assignment2;


import android.content.Context;
import android.content.SharedPreferences;
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
    int u = 0;
    public static final String MYPreference = "MYPref";
    SharedPreferences sharedpreferences;
    String s0[],s1[] ,s2[],s3[] ,s4[];
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


        sharedpreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);

        String h = sharedpreferences.getString("nameKey" , "");

        SharedPreferences.Editor editor = sharedpreferences.edit();//edit
        String s1 = sharedpreferences.getString(MYPreference , "");


        editor.putString("nameKey", "123,ggg,123,ggg");


        s0 = h.split("/");
        ArrayList<String> arr = new ArrayList<String>();
        ArrayList<String> arr1 = new ArrayList<String>();
        ArrayList<String> arr2 = new ArrayList<String>();
        ArrayList<String> arr3 = new ArrayList<String>();

        for(int i = 0  ; i < s0.length; i++)
        {

            String d[] = s0[i].split(",");
            arr.add(d[0]);
            arr1.add(d[1]);
            arr2.add(d[2]);
            arr3.add(d[3]);
            u += Integer.valueOf(d[2]);

        }



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
