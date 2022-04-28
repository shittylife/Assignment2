package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TimerAddFragment extends Fragment {

    SharedPreferences sharedpreferences;
    public static final String selected = "selected";
    public static final String data = "data";

    EditText nameET, workPeriodET, shortbreakET,longbreakET;
    Button add,btn_return;
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

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getActivity().getCurrentFocus()!= null){
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);}





                String temp = sharedpreferences.getString(data , "");
                String temp2 [] = temp.split(";");
                String temp3 = "";
                name = nameET.getText().toString();
                workPeriod = workPeriodET.getText().toString();
                shortbreak = shortbreakET.getText().toString();
                longbreak = longbreakET.getText().toString();


                if(name.length() != 0 && workPeriod.length() != 0 && shortbreak.length() != 0 && longbreak.length() != 0 && !name.contains(";")  && !name.contains(",") ){

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    for (int i = 0 ; i < temp2.length ;i++){
                        if (i==0) temp3 = temp3 + temp2[i];
                        if (i!=0) temp3 = temp3 +";" + temp2[i];}

                    if (temp3 == ""){
                        temp3 = temp3 + name + "," + workPeriod + "," +shortbreak +","+longbreak;
                    }
                    else{
                        temp3 = temp3 + ";" + name + "," + workPeriod + "," +shortbreak +","+longbreak ;
                    }
                    editor.putString(data, temp3);
                    editor.commit();
                    Log.e("tag","add: "+ sharedpreferences.getString(data , ""));
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment, timerFragment);
                    transaction.commit();

                }else{
                    Toast.makeText(getContext(), "Please input valid information and do not left the column empty.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    btn_return = view.findViewById(R.id.return_btn);

    btn_return.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, timerFragment);
            transaction.commit();
        }
    });

        return  view;
}
}





