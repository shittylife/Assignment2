package com.example.assignment2;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class clockrun extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    int i;
    TextView tv1,tv2,tv3 , name_tv ;
    Button button_pause,button_start,button_return1,button_resume,button_return2;
    MediaPlayer mp3;
    CountDownTimer cdt, cdt2 , cdt3 ;
    String name = "";
    long a = 0;
    long b = 0;
    long c = 0;
    long d = 0;
    long e = 0;
    long g = 0;
    boolean checker,checker2,checker3; // use for check which CountDownTimer is enable

    public clockrun() {
        // Required empty public constructor
    }

    public static clockrun newInstance(String param1, String param2) {
        clockrun fragment = new clockrun();
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
        View view = inflater.inflate(R.layout.fragment_clockrun, container, false);
        TimerFragment timerFragment = new TimerFragment();

        i=1;

        name_tv = view.findViewById(R.id.clockrun_name);
        tv1=view.findViewById(R.id.text_view_countdown);
        tv2 =view.findViewById(R.id.clockruntv_period);
        tv3=view.findViewById(R.id.clockruntv3);
        mp3 = MediaPlayer.create(getActivity(),R.raw.clock);
        button_start= view.findViewById(R.id.button_start);
        button_pause=view.findViewById(R.id.button_pause);
        button_return1=view.findViewById(R.id.button_return);
        button_resume=view.findViewById(R.id.button_resume);
        button_return2 =view.findViewById(R.id.button_retrun2);

        Bundle x = getArguments();
        if (x != null) {
            if (x.getString("key").equalsIgnoreCase("FromTimerFragment")) {//get the data from bundle
                name =x.getString("name");
                a = x.getLong("time1");
                b = x.getLong("time2");
                c = x.getLong("time3");
                d = x.getLong("time1");
                e = x.getLong("time2");
                g = x.getLong("time3");
                name_tv.setText("clock name: "+ name);
            }
        }




        button_start.setOnClickListener(new View.OnClickListener() { //start the  clock
            @Override
            public void onClick(View view) {
                tv3.setText("you are on 1 work period\nThen you can rest "+(c/1000/60)+" minute");
                button_pause.setVisibility(view.VISIBLE);
                button_start.setVisibility(view.GONE);
                button_return1.setVisibility(view.GONE);
                checker = false; // set all checker to be false;
                checker2= false;
                checker3 = false;
                workclock(view);
            }
        });

        button_pause.setOnClickListener(new View.OnClickListener() {//stop the clock
            @Override
            public void onClick(View view) {

                if(checker==true){
                    cdt.cancel();
                }
                if(checker2==true){
                    cdt2.cancel();
                }
                if (checker3==true){
                    cdt3.cancel();
                }
                button_resume.setVisibility(view.VISIBLE);
                button_return2.setVisibility(view.VISIBLE);
                button_pause.setVisibility(view.GONE);
            }
        });

        button_resume.setOnClickListener(new View.OnClickListener() {//stop the clock
            @Override
            public void onClick(View view) {
                if(checker==true){
                    workclock(view);
                }
                if(checker2==true){
                    shortbreakclock(view);
                }
                if (checker3==true){
                    longbreakclock(view);
                }
                button_resume.setVisibility(view.GONE);
                button_return2.setVisibility(view.GONE);
                button_pause.setVisibility(view.VISIBLE);
            }
        });

        button_return1.setOnClickListener(new View.OnClickListener() {//return to the menu
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment,timerFragment);
                transaction.commit();
            }
        });

        button_return2.setOnClickListener(new View.OnClickListener() {//return to the menu
            @Override
            public void onClick(View view) {
                a=d;
                a3();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment,timerFragment);
                transaction.commit();
            }
        });



        return view;}


    public void workclock(View view){ // work time
        cdt = new CountDownTimer(a,1000) {//start run the clock

            @Override
            public void onTick(long l) {
                tv2.setText("Work period");
                tv3.setText("you are on " + i +" work period\nThen you can rest "+(e/1000/60)+" minute");
                a = l;
                checker=true;
                a3();//allow the textview show the clock
            }

            @Override
            public void onFinish() { //when the work clock run less than 3 time
                checker=false;
                if (i < 4){
                    cdt.cancel();
                    mp3.start();
                    b=e;
                    shortbreakclock(view);}
                else{
                    tv3.setText("This is a long break clock. you can rest "+(g/1000/60)+" minute");
                    longbreakclock(view);}
                }


        };
        cdt.start();
    }

    public void shortbreakclock(View view){ // short break
        cdt2 = new CountDownTimer(b,1000) {//start run the clock

            @Override
            public void onTick(long l) {
                tv3.setText("you are on " + i +" work period\nThen you can rest "+(e/1000/60)+" minute");
                tv2.setText("Short break period");
                b = l;
                checker2=true;
                a4();//allow the textview show the clock
            }

            @Override
            public void onFinish() { // when the clock finish , it go back to work period.
                i += 1;
                cdt2.cancel();
                checker2=false;
                a = d;
                mp3.start();
                workclock(view);
            }

        };
        cdt2.start();
    }

    public void longbreakclock(View view){ // long break
        cdt3 = new CountDownTimer(c,1000) {//start run the clock

            @Override
            public void onTick(long l) {
                tv2.setText("long break period");
                c = l;
                checker3=true;
                a5();//allow the textview show the clock
            }

            @Override
            public void onFinish() {
                checker3=false;
                mp3.start();//play the alarm to tell the user work period is done
                tv3.setText("you finished the whole process!!");
                button_resume.setVisibility(view.VISIBLE);
                button_return2.setVisibility(view.VISIBLE);
                button_pause.setVisibility(view.GONE);

            }

        };
        cdt3.start();
    }

    private void a3(){
        int min =(int)(a/1000)/60;
        int sec =(int)(a/1000)%60;
        String timeformatted = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        tv1.setText(timeformatted);
    }
    private void a4(){
        int min =(int)(b/1000)/60;
        int sec =(int)(b/1000)%60;
        String timeformatted = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        tv1.setText(timeformatted);
    }
    private void a5(){
        int min =(int)(c/1000)/60;
        int sec =(int)(c/1000)%60;
        String timeformatted = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        tv1.setText(timeformatted);
    }

}