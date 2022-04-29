package com.example.assignment2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Dailyexpenselist extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> D;
    private final ArrayList<String> T;
    private final ArrayList<String> P;
    private final ArrayList<String> C;
    SharedPreferences sharedpreferences;

    public static final String Name = "nameKey";

    public Dailyexpenselist(Activity context,
                            ArrayList<String> D, ArrayList<String> T, ArrayList<String> P, ArrayList<String> C)

    {
        super(context, R.layout.dailyexpense_listview, D);
        this.context = context;
        this.D = D;
        this.T = T;
        this.P = P;
        this.C = C;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.dailyexpense_listview, null,true);

        sharedpreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);


        TextView tv_label = rowView.findViewById(R.id.textView10);
        TextView tv_label2 = rowView.findViewById(R.id.textView11);
        TextView tv_label3 = rowView.findViewById(R.id.textView12);
        TextView tv_label4 = rowView.findViewById(R.id.textView13);

        tv_label.setText(D.get(position));
        tv_label2.setText(T.get(position));
        tv_label3.setText(P.get(position));
        tv_label4.setText(C.get(position));

        return rowView;
    }
}

