package com.example.assignment2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TimerAddFragment extends Fragment {

    EditText nameET, workPeriodET, shortbreakET,longbreakET;
    Button add;
    private Object TimerFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_addalarm, container, false);

    nameET = view.findViewById(R.id.NameET);
    workPeriodET = view.findViewById(R.id.PeriodET);
    shortbreakET = view.findViewById(R.id.shortBreakET);
    longbreakET = view.findViewById(R.id.longBreakET);

    add = view.findViewById(R.id.btn_add);

    add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_addalarm, TimerFragment);
            transaction.commit();
        }
    });

    return  view;
}
}





