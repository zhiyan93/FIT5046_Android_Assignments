package com.example.navdrawertest3;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private View vHome;
    private ImageView imgFit;
    private TextClock digitalClock;
    private TextView vfname;
    private EditText eCalGoal;
    private Button goalBtn;
   private SharedPreferences sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vHome = inflater.inflate(R.layout.home, container, false);
        imgFit = vHome.findViewById(R.id.imgFit);
        eCalGoal = vHome.findViewById(R.id.eCalGoal);
        goalBtn = vHome.findViewById(R.id.goalBtn);
        digitalClock = vHome.findViewById(R.id.digitalClock);
       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault());
        digitalClock.setFormat12Hour("MMMM dd, yyyy HH:mm:ss");
        vfname =( TextView) vHome.findViewById(R.id.vfname);
      //  final SharedPreferences  SPLoginUser =getActivity().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
         sp = ((MainActivity)getActivity()).getSP();
        String name= sp .getString("userName","");
        if (!name.equals(""))
        vfname.setText("welcome "+name+" !");

        String goal = sp.getString("calGoal","");
        eCalGoal.setText(goal);
        goalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goal = eCalGoal.getText().toString();
                double value = Double.valueOf(goal);
                if(value>500)
                {
                    eCalGoal.setError("calorie goal cannot grater than 500");
                    return;
                }
                sp.edit().putString("calGoal",goal).apply();
            }
        });
        return vHome;
    }

}
