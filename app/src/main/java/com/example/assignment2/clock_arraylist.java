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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class clock_arraylist extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> season;
    private Button btn_del_item;
    ArrayList<String> sptext;
    SharedPreferences sharedpreferences;

    public static final String selected = "selected";
    public static final String data = "data";

    public clock_arraylist(Activity context,
                           ArrayList<String> season
    ) {
        super(context, R.layout.clock_listview, season);
        //inherit the information form super class

        //define the attributes of your class
        this.context = context;
        this.season = season;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.clock_listview, null,true);

        sharedpreferences = context.getSharedPreferences(selected, Context.MODE_PRIVATE);

        TextView tv_label = rowView.findViewById(R.id.tv_label);
        ImageView iv_icon = rowView.findViewById(R.id.Iv_Icon);

        tv_label.setText(season.get(position));

        //button for del
        btn_del_item = rowView.findViewById(R.id.btn_del_item);
        btn_del_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp1 = sharedpreferences.getString(data , "");

                String[] temp2 = temp1.split(";");
                Log.e("tag","11 "+ temp1);
                String temp3 = "";

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    int i;
                    for ( i = 0 ; i < temp2.length ;i++){
                        if (i == 1 && i !=0) temp3 = temp2[i];
                        if(i !=1 && i != 0) temp3 = temp3 +";"+ temp2[i];
                        }
                    Log.e("tag","12 "+ i);
                    editor.putString(data, temp3);
                    editor.commit();


                season.remove(position);
                clock_arraylist.this.notifyDataSetChanged();//update immediately
            }
        });


        return rowView;
    }
}
