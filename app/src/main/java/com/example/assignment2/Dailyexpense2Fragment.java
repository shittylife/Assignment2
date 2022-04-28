package com.example.assignment2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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

import java.util.Calendar;



public class Dailyexpense2Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    String sppick ;
    private DatePickerDialog datePickerDialog;
    private Button dateButton,save,pick;
    private Spinner sp1;
    private String date,dateDay,dateMonth;
    private TextView tvtest;
    private DatePicker datePicker;
    private EditText detail,et2,et3;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";



    public Dailyexpense2Fragment() {
        // Required empty public constructor
    }
    public static Dailyexpense2Fragment newInstance(String param1, String param2) {

        Dailyexpense2Fragment fragment = new Dailyexpense2Fragment();
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

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dailyexpense2, container, false);
        DailyexpenseFragment calendarFragment = new DailyexpenseFragment();
        initDatePicker();
        dateButton = view.findViewById(R.id.datePickerButton);
        tvtest = view.findViewById(R.id.tvtest);
        save = view.findViewById(R.id.btn4);
        sp1 = view.findViewById(R.id.sp1);
        pick = view.findViewById(R.id.btn5);
        detail = view.findViewById(R.id.et1);
        et2 = view.findViewById(R.id.et2);
        et3 = view.findViewById(R.id.et3);

        datePicker = view.findViewById(R.id.DatePicker);
        DatePicker picker = view.findViewById(R.id.DatePicker);
        int i;

        sharedpreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String choice = adapterView.getItemAtPosition(i).toString();
                sppick =  choice;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                tvtest.setText(date);
            }
        });


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pick.setVisibility(View.VISIBLE);


                if(datePicker.getVisibility()  == view.GONE){
                    datePicker.setVisibility(View.VISIBLE);

                }else if(datePicker.getVisibility() == View.VISIBLE){
                    datePicker.setVisibility(View.GONE);

                }



            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = et3.getText().toString();
                String detail_text = detail.getText().toString();
                String s1;


                if(sppick == null || price.length() == 0 || detail_text.length() == 0 || date == null || detail_text.contains(";") || detail_text.contains("~")){
                    Toast.makeText(getActivity(),"Please fill in the data and do not type ; or ~ in the comment.",Toast.LENGTH_SHORT).show();
                }else{

                     s1 = sharedpreferences.getString(Name , "");
                    Log.e("sp1",s1);


                    if(s1.length() != 0) {

                        s1 = s1 +";" + date;
                        s1 = s1 + "~" + sppick;
                        s1 = s1 + "~" + price;
                        s1 = s1 + "~" + detail_text ;

                    }else{

                        s1 = s1 + date;
                        s1 = s1 + "~" + sppick;
                        s1 = s1 + "~" + price;
                        s1 = s1 + "~" + detail_text ;

                    }

                    SharedPreferences.Editor editor = sharedpreferences.edit();//edit
                    editor.putString(Name, s1);
                    editor.commit();
                    Log.e("sp2",sharedpreferences.getString(Name , ""));
                    Toast.makeText(getActivity(), "Saved",Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment,calendarFragment );
                    transaction.commit();


                }
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

        return view;
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

        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }




}


