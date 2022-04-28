package com.example.assignment2;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    int i=0;
    TextView tv1,tv2,tv3;
    Button startTimer_btn,stopTimer_btn,return_btn;
    MediaPlayer mp3;
    CountDownTimer countDownTimer,countDownTimer2;
    long a = 60000;
    long b = 30000;
    long c = 0;

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


        Bundle x = getArguments();
        Log.d("x", String.valueOf(x));
        if (x != null) {
            if (x.getString("key").equalsIgnoreCase("FromTimerFragment")) {
                    a = x.getLong("time1");
                    b = x.getLong("time2");
                    c = x.getLong("time3");
                Log.d("tag name a b c", a + " " + b + " " + c );
            }
        }
       tv1=view.findViewById(R.id.text_view_countdown);
        tv2=view.findViewById(R.id.clockruntv2);
        tv3=view.findViewById(R.id.clockruntv3);
        mp3 = MediaPlayer.create(getActivity(),R.raw.clock);
       startTimer_btn = view.findViewById(R.id.button_start);
       stopTimer_btn=view.findViewById(R.id.button_pause);
       return_btn = view.findViewById(R.id.button_return);
       startTimer_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
          countDownTimer = new CountDownTimer(a,1000) {
              @Override
              public void onTick(long millisUntilFinished) {
                 a= millisUntilFinished;
                  stopTimer_btn.setVisibility(view.VISIBLE);
                  startTimer_btn.setVisibility(view.GONE);
                 a3();
              }

              @Override
              public void onFinish() {
                  mp3.start();
                  a1();
              }
          };
           }
       });
       stopTimer_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               countDownTimer.cancel();
               countDownTimer2.cancel();
               startTimer_btn.setVisibility(view.VISIBLE);
           }
       });



        return view;
    }

    public void a1(){
        if(i<3){
            try {
                countDownTimer.wait(c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownTimer.start();
            i=i+1;
            tv3.setText("you already done:"+i+"time");
        }else {
            a2();
            tv2.setText("now take a 15-minute break");
        }
    }
    public void a2(){
        countDownTimer2 = new CountDownTimer(b, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                b=millisUntilFinished;
                a4();
            }

            @Override
            public void onFinish() {

                tv2.setText("you finished a whole process!!");
            }
        };
        countDownTimer2.start();
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

}