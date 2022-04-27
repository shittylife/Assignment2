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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class TimerFragment extends Fragment {
    String s1 ;

    SharedPreferences sharedpreferences;
    public static final String selected = "selected";
    public static final String data = "data";


    ListView lv1;
    Button btn_add , btn_addDefault;
    Integer selected_pos = -1;



    /*
    DataPassListener mCallback;
    public interface DataPassListener{

    }






    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that MainActivity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (DataPassListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement DataPassListener");

        }
    }*/

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

        Log.e("tag","1"+ s1);
        Log.e("tag","2"+ sharedpreferences.getString(data , ""));

        if(s1 != "") {
            s2 = s1.split(";");

            for (int i=0; i<s2.length ; i++){
                String[] clockRecord = s2[i].split(",");
                clockname.add(clockRecord[0]);
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
        return view;
    }
}
