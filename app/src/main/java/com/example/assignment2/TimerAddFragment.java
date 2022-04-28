package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TimerAddFragment extends Fragment {

    SharedPreferences sharedpreferences;
    public static final String selected = "selected";
    public static final String data = "data";

    EditText nameET, workPeriodET, shortbreakET,longbreakET;
    Button add;
    String name,workPeriod , shortbreak , longbreak;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_addalarm, container, false);
    TimerFragment timerFragment = new TimerFragment();

    sharedpreferences = getActivity().getSharedPreferences(selected, Context.MODE_PRIVATE);

    nameET = view.findViewById(R.id.NameET);
    workPeriodET = view.findViewById(R.id.PeriodET);
    shortbreakET = view.findViewById(R.id.shortBreakET);
    longbreakET = view.findViewById(R.id.longBreakET);

    add = view.findViewById(R.id.ADD_btn);

    add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            name = nameET.getText().toString();
            workPeriod = workPeriodET.getText().toString();
            shortbreak = shortbreakET.getText().toString();
            longbreak = longbreakET.getText().toString();

            if(workPeriod == ""){

            }
            String temp = ";" + name + "," + workPeriod + "," + shortbreak + "," + longbreak;

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(data, temp);
            editor.commit();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, timerFragment);
            transaction.commit();
        }
    });

    return  view;
}
}





