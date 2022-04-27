package com.example.assignment2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Calendar2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Calendar2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatePickerDialog datePickerDialog;
    private Button dateButton,save,pick;
    private Spinner sp1;
    private String date,dateDay,dateMonth;
    private TextView tvtest;
    private DatePicker datePicker;
    private EditText detail,et2;
    private List<Task> data = new ArrayList<>();
    SharedPreferences sharedpreferences;
    public static final String MYPreference = "MYPref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    CalendarFragment calendarFragment = new CalendarFragment();

    public Calendar2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calendar2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Calendar2Fragment newInstance(String param1, String param2) {
        Calendar2Fragment fragment = new Calendar2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar2, container, false);
        initDatePicker();
        dateButton = view.findViewById(R.id.datePickerButton);
        tvtest = view.findViewById(R.id.tvtest);
        save = view.findViewById(R.id.btn4);
        sp1 = view.findViewById(R.id.sp1);
        pick = view.findViewById(R.id.btn5);
        detail = view.findViewById(R.id.et1);
        et2 = view.findViewById(R.id.et2);
        datePicker = view.findViewById(R.id.DatePicker);
        dateButton.setText(getTodaysDate());
        DatePicker picker = view.findViewById(R.id.DatePicker);



        sharedpreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String choice = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity(),choice,Toast.LENGTH_SHORT).show();

                //if(choice == "Custom"){
                    et2.setVisibility(View.VISIBLE);

               // }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(tvtest == null && sp1== null){
                    Toast.makeText(getActivity(),"Please fill in the Date and Theme.",Toast.LENGTH_SHORT).show();
                }else {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment,calendarFragment );
                    transaction.commit();
                    Toast.makeText(getActivity(), "Saved",Toast.LENGTH_SHORT).show();

                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setVisibility(View.VISIBLE);
                pick.setVisibility(View.VISIBLE);



            }
        });

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setVisibility(View.GONE);
                pick.setVisibility(View.GONE);
                sp1.setVisibility(View.VISIBLE);
                tvtest.setText("Selected Day: " + date);
                Toast.makeText(getActivity(),date + "Selected",Toast.LENGTH_SHORT).show();
            }
        });

        int Year = picker.getYear();
        int Month =picker.getMonth()+1;
        int Day = picker.getDayOfMonth();
        picker.init(Year,Month,Day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                StringBuilder builder = new StringBuilder();
                builder.append(day+"/");
                builder.append(month+1+"/");
                builder.append(year);
                date=builder.toString();
                dateDay=String.valueOf(picker.getMonth()+1);
                dateMonth=String.valueOf(picker.getDayOfMonth());
            }
        });
        tvtest.setText(date);



        String s1 = sharedpreferences.getString(MYPreference , "");
        String s2[] = s1.split("/");

        if(s1 != "") {
            s2 = s1.split("/");

            for (int i=0; i<s2.length ; i++){
                String[] clockRecord = s2[i].split(",");
                data.add(clockRecord[0]);
            }
        }else {
            SharedPreferences.Editor editor = sharedpreferences.edit();//edit
            editor.putString(Name, "date,25,5,15");
            editor.commit();
        }


        return view;
    }





    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }




}


