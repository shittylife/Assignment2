package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class TimerFragment extends Fragment {
    String s1 ;

    SharedPreferences sharedpreferences;
    public static final String selected = "selected";
    public static final String data = "data";

    TextView timertv1;
    ListView lv;
    Button btn_add , btn_addDefault,btn_clock_instruction;
    Integer selected_pos = -1;
    DataPassListener mCallback;


    public  interface  DataPassListener{ //create datapasslistener
        public void pass1(String name, long time1,long time2,long time3);
    }

    public  void  onAttach(Context context){
        super.onAttach(context);
        mCallback =(DataPassListener) context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        TimerAddFragment timerAddFragment = new TimerAddFragment();//create timerAddFragment fragment

        sharedpreferences = getContext().getSharedPreferences(selected, Context.MODE_PRIVATE);//create sharedpreferences

        s1 = sharedpreferences.getString(data , "");//get the sharedpreferences
        lv = view.findViewById(R.id.lv1);
        String[] s2;

        ArrayList<String> clockname = new ArrayList<>();//create arraylistw
        ArrayList<String> time1 = new ArrayList<>();
        ArrayList<String> time2 = new ArrayList<>();
        ArrayList<String> time3 = new ArrayList<>();

        if(s1 != "") {//check if the sharedpreferences is null
            if(s1.contains(";")){//check the sharedpreferences data whether more than 1

                s2 = s1.split(";");//split the data

                for (int i=0; i<s2.length ; i++){//use a loop to  add the data to arraylist
                String[] clockRecord = s2[i].split(",");
                clockname.add(clockRecord[0]);
                time1.add(clockRecord[1]);
                time2.add(clockRecord[2]);
                time3.add(clockRecord[3]); }

            }else {

                    String[] clockRecord = s1.split(",");
                    clockname.add(clockRecord[0]);//add the data to arraylist
                    time1.add(clockRecord[1]);
                    time2.add(clockRecord[2]);
                    time3.add(clockRecord[3]);
            }
        } else{//create a default clock
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(data, "Tomato,25,5,15");
            editor.commit();
            clockname.add("Tomato");
            time1.add("25");
            time2.add("5");
            time3.add("15");
        }

        clock_arraylist adapter = new clock_arraylist(getActivity(),clockname);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { // click the listview item and go to the clock run fragment
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_pos = position;
                String name = clockname.get(position);
                long a , b  , c ;
                a = Long.valueOf(time1.get(position));
                b = Long.valueOf(time2.get(position));
                c = Long.valueOf(time3.get(position));
                a = a *1000 *60;
                b = b *1000 *60;
                c = c *1000 *60;
                mCallback.pass1(name,a,b,c);//transfer the data to clockrun fragment


            }

        });
        adapter.notifyDataSetChanged();

        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setText("ADD");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();//transfer ot timeAddFragment
                transaction.replace(R.id.fragment, timerAddFragment);
                transaction.commit();
            }
        });

        btn_addDefault = view.findViewById(R.id.default_button);
        btn_addDefault.setOnClickListener(new View.OnClickListener() {//add the default tomato clock
            @Override
            public void onClick(View view) {
                String temp = sharedpreferences.getString(data , "");
                clockname.add("Tomato");
                time1.add("25");
                time2.add("5");
                time3.add("15");
                if(temp != ""){
                    String[] temp2 = temp.split(";");
                    String temp3 = "";

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    for (int i = 0 ; i < temp2.length ;i++){
                        if (i==0) temp3 = temp3 + temp2[i];
                        if (i!=0) temp3 = temp3 +";" + temp2[i];}

                    temp3 = temp3 + ";Tomato,25,5,15";
                    editor.putString(data, temp3);
                    editor.commit();


                }else{
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(data, "Tomato,25,5,15");
                    editor.commit();
                }
                adapter.notifyDataSetChanged();
            }
        });
        timertv1 = view.findViewById(R.id.timertv1);
        btn_clock_instruction = view.findViewById(R.id.btn_clock_instruction);
        btn_clock_instruction.setOnClickListener(new View.OnClickListener() {//let the instruction show or hide
            @Override
            public void onClick(View view) {
                timertv1.setText("Run the clock: \nstep1: Pick a task.\nstep2: work on your task .\nstep3: Take a break\nstep4: Every 4 period,take a longer break.Then, the clock is done.\n\n(ps:\n1. If you delete all the task and quit the app , it will create a default clock automatically\n2. If you are on break time, the clock do not show you the time.\n3. the default clock is 25 min for work period,5 min for short break and 15 min for long break.");

                if(timertv1.getVisibility() == View.GONE){
                    timertv1.setVisibility(View.VISIBLE);
                }else if(timertv1.getVisibility() == View.VISIBLE){
                    timertv1.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }
}
