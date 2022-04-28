package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TimerAddFragment extends Fragment {

    SharedPreferences sharedpreferences;
    public static final String selected = "selected";
    public static final String data = "data";

    EditText nameET, workPeriodET, shortbreakET,longbreakET;
    Button add,btn_return , intro;
    String name,workPeriod , shortbreak , longbreak;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_addalarm, container, false);
    TimerFragment timerFragment = new TimerFragment();// add the time fragment

    sharedpreferences = getActivity().getSharedPreferences(selected, Context.MODE_PRIVATE); // create the sharedperferences

    nameET = view.findViewById(R.id.NameET);
    workPeriodET = view.findViewById(R.id.PeriodET);
    shortbreakET = view.findViewById(R.id.shortBreakET);
    longbreakET = view.findViewById(R.id.longBreakET);

    add = view.findViewById(R.id.ADD_btn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getActivity().getCurrentFocus()!= null){
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);}//hide the keyboard


                String temp = sharedpreferences.getString(data , ""); //get the data from sharedpreferences
                String temp2 [] = temp.split(";"); // split the data
                String temp3 = "";
                name = nameET.getText().toString(); //get data
                workPeriod = workPeriodET.getText().toString();
                shortbreak = shortbreakET.getText().toString();
                longbreak = longbreakET.getText().toString();


                if(name.length() != 0 && workPeriod.length() != 0 && shortbreak.length() != 0 && longbreak.length() != 0 && !name.contains(";")  && !name.contains(",") ){
                    //check the input whether valid
                    SharedPreferences.Editor editor = sharedpreferences.edit(); //create the editor

                    for (int i = 0 ; i < temp2.length ;i++){
                        if (i==0) temp3 = temp3 + temp2[i];// add the begin data
                        if (i!=0) temp3 = temp3 +";" + temp2[i];}//add the further data

                    if (temp3 == ""){
                        temp3 = temp3 + name + "," + workPeriod + "," +shortbreak +","+longbreak; //if the sharedpreferences is empty
                    }
                    else{
                        temp3 = temp3 + ";" + name + "," + workPeriod + "," +shortbreak +","+longbreak ; // if the sharedpreferences is not empty
                    }
                    editor.putString(data, temp3);
                    editor.commit(); // commit the data to sharedpreferences
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment, timerFragment);
                    transaction.commit();//change the fragment

                }else{
                    Toast.makeText(getContext(), "Please input valid information and do not left the column empty.", Toast.LENGTH_SHORT).show();//warning the user
                }


            }
        });

    btn_return = view.findViewById(R.id.return_btn);

    btn_return.setOnClickListener(new View.OnClickListener() { //return to menu
        @Override
        public void onClick(View view) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, timerFragment);
            transaction.commit();
        }
    });

        tv = view.findViewById(R.id.timeraddtv1);
        intro = view.findViewById(R.id.btn_add_clock_instruction);//introduction
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Add the clock :\nStep1: Please fill in all data.\nStep2: Please Click the add button.");

                if(tv.getVisibility() == View.GONE){
                    tv.setVisibility(View.VISIBLE);
                }else if(tv.getVisibility() == View.VISIBLE){
                    tv.setVisibility(View.GONE);
                }
            }
        });

        return  view;
}
}





