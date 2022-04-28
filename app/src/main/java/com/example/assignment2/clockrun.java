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
    int i=0;
    TextView tv1,tv2,tv3;
    Button btn1,btn2,btn3;
    MediaPlayer mp3;
    CountDownTimer cdt, cdt2;
    String name = "";
    long a = 0;
    long b = 0;
    long c = 0;
    long d = 0;
    boolean checker,checker2;

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

        tv1=view.findViewById(R.id.text_view_countdown);
        tv3=view.findViewById(R.id.clockruntv3);
        mp3 = MediaPlayer.create(getActivity(),R.raw.clock);
        btn1=view.findViewById(R.id.button_start);
        btn2=view.findViewById(R.id.button_pause);
        btn3=view.findViewById(R.id.button_return);

        Bundle x = getArguments();
        if (x != null) {
            if (x.getString("key").equalsIgnoreCase("FromTimerFragment")) {//get the data from bundle
                    name =x.getString("name");
                    a = x.getLong("time1");
                    b = x.getLong("time2");
                    c = x.getLong("time3");
                    tv3.setText("clock name: "+ name );
            }
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv3.setText("you are on 1 work period\nThen you can rest: "+(c/1000/60)+" minute");

                cdt = new CountDownTimer(a,1000) {//start run the clock

                    @Override
                    public void onTick(long l) {
                        a= l;
                        checker=true;
                        a3();//allow the textview show the clock
                        btn2.setVisibility(view.VISIBLE);
                        btn1.setVisibility(view.GONE);
                        btn3.setVisibility(view.GONE);

                    }

                    @Override
                    public void onFinish() {
                        checker=false;
                        mp3.start();//play the alarm to tell the user work period is done
                        a1(view);
                    }

                };
                cdt.start();
            }
        });

      btn2.setOnClickListener(new View.OnClickListener() {//stop the clock
           @Override
           public void onClick(View view) {
               cdt.cancel();
               if(checker2==true){
                   cdt2.cancel();
               }
               btn1.setVisibility(view.VISIBLE);
               btn3.setVisibility(view.VISIBLE);
               btn2.setVisibility(view.GONE);
           }
       });
      btn3.setOnClickListener(new View.OnClickListener() {//return to the menu
          @Override
          public void onClick(View view) {
              a=d;
              a3();
              FragmentTransaction transaction = getFragmentManager().beginTransaction();
              transaction.replace(R.id.fragment,timerFragment);
              transaction.commit();
          }
      });



        return view;
    }

    public void a1(View view){
        if(i<3){
            try {
                cdt.wait(c);//wait for break
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cdt.start();//work period again
            a5();//allow the textview show the time
            i=i+1;
            tv3.setText("you are on "+(i)+" work period\nThen you can rest: "+(c/1000/60)+" minute");

        }else {
            tv3.setText("now take a "+(b/1000/60)+"-minute break");
            a2();//start the long break
        }
    }
    public void a2(){//the long break clock
        cdt2 = new CountDownTimer(b, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                b=millisUntilFinished;
                checker2=true;
                a4();
            }

            @Override
            public void onFinish() {//finish the cycle
                checker2=false;
                tv3.setText("you finished a whole process!!");
            }
        };
        cdt2.start();
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