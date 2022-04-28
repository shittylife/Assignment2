package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    ListView lv1;
    Button btn_add , btn_addDefault,btn_clock_instruction;
    Integer selected_pos = -1;
    DataPassListener mCallback;
    long a,b,c;

    public  interface  DataPassListener{
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
        TimerAddFragment timerAddFragment = new TimerAddFragment();

        sharedpreferences = getActivity().getSharedPreferences(selected, Context.MODE_PRIVATE);


        s1 = sharedpreferences.getString(data , "");

        lv1 = view.findViewById(R.id.lv1);
        String[] s2;

        ArrayList<String> clockname = new ArrayList<>();
        ArrayList<String> time1 = new ArrayList<>();
        ArrayList<String> time2 = new ArrayList<>();
        ArrayList<String> time3 = new ArrayList<>();


        Log.e("tag","1"+ s1);
        Log.e("tag","2"+ sharedpreferences.getString(data , ""));

        if(s1 != "") {
            s2 = s1.split(";");

            for (int i=0; i<s2.length ; i++){
                String[] clockRecord = s2[i].split(",");
                clockname.add(clockRecord[0]);
                time1.add(clockRecord[1]);
                time2.add(clockRecord[2]);
                time3.add(clockRecord[3]);

            }
        }else{
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(data, "Tomato,25,5,15");
            editor.commit();
            clockname.add("Tomato");

        }

        clock_arraylist adapter = new clock_arraylist(getActivity(),clockname);

        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                selected_pos = position;
                clockname.get(position);
                time1.get(position);
                time2.get(position);
                time3.get(position);
                a = Long.valueOf(String.valueOf(time1));
                b = Long.valueOf(String.valueOf(time2));
                c = Long.valueOf(String.valueOf(time3));
                mCallback.pass1(String.valueOf(clockname),a,b,c);
            }

        });
        adapter.notifyDataSetChanged();

        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setText("ADD");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, timerAddFragment);
                transaction.commit();
            }
        });

        btn_addDefault = view.findViewById(R.id.default_button);
        btn_addDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tag","3 "+ sharedpreferences.getString(data , ""));
                String temp = sharedpreferences.getString(data , "");
                clockname.add("Tomato");
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

                    Log.e("tag","4 "+ sharedpreferences.getString(data , ""));

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
        btn_clock_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timertv1.setText("step1: Pick a task\nstep2: work on your task until 25-minute left\n step3: Take a 5-minute break\n step4: Every 4 pomodoros,take a longer 15-minute break\n(ps: if you delete all the task and quit the app , it will create a default automatically");

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
