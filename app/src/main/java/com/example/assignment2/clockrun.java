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
    long a = 6000;
    long b = 3000;
    long c = 3000;
    long d=0;
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
        tv2=view.findViewById(R.id.clockruntv2);
        tv3=view.findViewById(R.id.clockruntv3);
        mp3 = MediaPlayer.create(getActivity(),R.raw.clock);
        btn1=view.findViewById(R.id.button_start);
        btn2=view.findViewById(R.id.button_pause);
        btn3=view.findViewById(R.id.button_return);

        Bundle x = getArguments();
        Log.d("x", String.valueOf(x));
        if (x != null) {
            if (x.getString("key").equalsIgnoreCase("FromTimerFragment")) {
                    name =x.getString("name");
                    a = x.getLong("time1");
                    b = x.getLong("time2");
                    c = x.getLong("time3");
                    tv3.setText("clock name: "+ name );
                Log.d("tag name a b c", name+" "+ a + " " + b + " " + c );
            }
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cdt = new CountDownTimer(a,1000) {

                    @Override
                    public void onTick(long l) {
                        a= l;
                        checker=true;
                        a3();
                        btn2.setVisibility(view.VISIBLE);
                        btn1.setVisibility(view.GONE);
                        btn3.setVisibility(view.GONE);

                    }

                    @Override
                    public void onFinish() {
                        checker=false;
                        mp3.start();
                        a1();
                    }

                };
                cdt.start();
            }
        });

      btn2.setOnClickListener(new View.OnClickListener() {
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
      btn3.setOnClickListener(new View.OnClickListener() {
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

    public void a1(){
        if(i<3){
            try {
                cdt.wait(c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cdt.start();
            i=i+1;
            tv3.setText("you already done:"+i+"time");
        }else {
            a2();
            tv2.setText("now take a 15-minute break");
        }
    }
    public void a2(){
        cdt2 = new CountDownTimer(b, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                b=millisUntilFinished;
                checker2=true;
                a4();
            }

            @Override
            public void onFinish() {
                checker2=false;
                tv2.setText("you finished a whole process!!");
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

}