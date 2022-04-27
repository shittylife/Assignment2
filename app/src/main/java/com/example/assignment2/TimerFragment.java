package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TimerFragment extends Fragment {
    String s1= "tomato,25,5,15;default,25,10,25";

    SharedPreferences sharedpreferences;
    public static final String selected = "selected";
    public static final String data = "data";

    ArrayList<String> season;

    ListView lv1;
    Button btn_add , btn_addDefault;
    Integer selected_pos = -1;

    DataPassListener mCallback;

    public interface DataPassListener{
        public void addAlarm();
    }

    // Interface class for data passing from f1 to f3
    DataPassListener1 mCallback1;

    public interface DataPassListener1{
        public void passDatafromFirst2Third(String data1,String data2,String data3,String data4);
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        sharedpreferences = getActivity().getSharedPreferences(selected, Context.MODE_PRIVATE);


        lv1 = view.findViewById(R.id.lv1);

        s1 = s1 + ";test,20,30,40,60";

        String[] s2 =  s1.split(";");

        ArrayList<String> name = new ArrayList<>();

        for (int i=0; i<s2.length ; i++){
            String[] clockRecord = s2[i].split(",");
            name.add(clockRecord[0]);
        }



        season = new ArrayList<String>(name);

        clock_arraylist adapter = new clock_arraylist(getActivity(),season);

        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                selected_pos = position;
            }
        });

        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setText("ADD");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.addAlarm();
            }
        });

        btn_addDefault = view.findViewById(R.id.btn_add);
        btn_addDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                season.add("Tomato");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(data, ";Tomato,25,5,15");
                editor.commit();
                adapter.notifyDataSetChanged();

            }
        });
        return view;
    }
}
