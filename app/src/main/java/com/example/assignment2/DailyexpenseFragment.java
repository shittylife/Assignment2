package com.example.assignment2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;


public class DailyexpenseFragment extends Fragment {
    Integer selected_pos = -1;
    ImageButton iv2;
    Button ins_btn;
    TextView tv5 , ins_tv;
    ListView lv;
    int u = 0;
    String t;
    SharedPreferences sharedpreferences;
    String s0[];
    public static final String Name = "nameKey";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dailyexpense, container, false);
        Dailyexpense2Fragment dailyexpense2Fragment = new Dailyexpense2Fragment();
        iv2 = view.findViewById(R.id.iv2);
        lv = view.findViewById(R.id.lv1);
        tv5 = view.findViewById(R.id.tv5);
        ArrayList<String> arr = new ArrayList<String>();
        ArrayList<String> arr1 = new ArrayList<String>();
        ArrayList<String> arr2 = new ArrayList<String>();
        ArrayList<String> arr3 = new ArrayList<String>();

        sharedpreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);

        String h = sharedpreferences.getString("nameKey", "");

        if (h.length() != 0) {
            if (h.contains(";")) {

                s0 = h.split(";");
                for (int i = 0; i < s0.length; i++) {
                    String d[] = s0[i].split("~");
                    arr.add(d[0]);
                    arr1.add(d[1]);
                    arr2.add(d[2]);
                    arr3.add(d[3]);
                    u += Integer.valueOf(d[2]);
                }


            } else {

                String d[] = h.split("~");
                arr.add(d[0]);
                arr1.add(d[1]);
                arr2.add(d[2]);
                arr3.add(d[3]);
                u += Integer.valueOf(d[2]);

            }

        }
        t = String.valueOf(u);
        tv5.setText(t);


        Dailyexpenselist adapter = new Dailyexpenselist(getActivity(), arr, arr1, arr2, arr3);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_pos = position;
            }

        });
        adapter.notifyDataSetChanged();

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, dailyexpense2Fragment);
                transaction.commit();
            }
        });

        ins_tv = view.findViewById(R.id.tvtest11);
        ins_btn = view.findViewById(R.id.Instruction_dailyexpenses);
        ins_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ins_tv.getVisibility() == View.GONE){
                    ins_tv.setVisibility(View.VISIBLE);
                }else if(ins_tv.getVisibility() == View.VISIBLE){
                    ins_tv.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

}
