package com.example.navdrawertest3;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.navdrawertest3.RestEntity.Report;
import com.example.navdrawertest3.RestEntity.userId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalTrackerFragment extends Fragment {

    private View vHome;
    private TextView vSetGoal,vDailyBurn,vStepBurn,vBurned;
    private SharedPreferences sp;
    private static int uid;
    private static int steps = 0;
    static StepsDB stepsDB = null;
    private TextView vTotalSteps,vCalConsum;
    private static double cBurned=0;
    private static double cConsum=0;
    private static double cGoal=0;
    private Button postBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vHome = inflater.inflate(R.layout.fragment_cal_tracker, container, false);
        vSetGoal = vHome.findViewById(R.id.vSetGoal);
        vTotalSteps = vHome.findViewById(R.id.vTotalSteps);
        vCalConsum = vHome.findViewById(R.id.vCalConsum);
        vDailyBurn = vHome.findViewById(R.id.vDailyBurn);
        vStepBurn = vHome.findViewById(R.id.vStepBurn);
        vBurned = vHome.findViewById(R.id.vBurned);
        postBtn = vHome.findViewById(R.id.postBtn);
        sp = ((MainActivity)getActivity()).getSP();
        String goal = sp.getString("calGoal","0");
        cGoal = Double.valueOf(goal);
         uid = sp.getInt("userId",1);
        vSetGoal.setText("entered goal: "+goal);
        stepsDB = ((MainActivity)getActivity()).getStepsDB();
        ReadDBTotalSteps stepTask = new ReadDBTotalSteps();
        stepTask.execute();
        CalConsumpThatDay calConTask = new CalConsumpThatDay();
        calConTask.execute(String.valueOf(uid));
        DailyStepBurnTask burnTask = new DailyStepBurnTask();
        burnTask.execute(String.valueOf(uid));
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              PostReportTask task = new PostReportTask();
              task.execute();
            }
        });
        return vHome;
    }

    private class ReadDBTotalSteps extends AsyncTask<Void, Void,String> {
        //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
        private int totalStep = 0;
        @Override
        protected String doInBackground(Void... params) {

            List<StepsEntity> records = stepsDB.stepsDao().getAll();
            if (!(records.isEmpty() || records == null)) {
                for (StepsEntity temp : records) {
                    totalStep += temp.getSteps();
                }
            }
            return String.valueOf(totalStep);
        }
        @Override
        protected void onPostExecute(String result)
        {

            vTotalSteps.setText("total steps: "+result);
            steps = totalStep;
        }

    }

    private class CalConsumpThatDay extends AsyncTask<String,Void,String>
    {
        String result = "";

        SimpleDateFormat simpleDateFormatformat = new SimpleDateFormat("yyyy-MM-dd");
        String strToday = simpleDateFormatformat.format(new Date());

        @Override
        protected String doInBackground(String... uid) {
             result= A1DBAPI.getCalthatDay(uid[0],strToday);
            return result;
        }
        @Override
        protected void onPostExecute(String cal)
        {
            vCalConsum.setText("calorie consumption today: "+cal);
            cConsum=Double.valueOf(cal);
        }
    }

    private class DailyStepBurnTask extends AsyncTask<String,Void,Void>
    {
        double daily =0;
        double stepBurn = 0;

        @Override
        protected Void doInBackground(String... strings) {
           daily= A1DBAPI.getDailyBurn(strings[0]);
           stepBurn = A1DBAPI.getStepBurn(strings[0]);
           return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            vDailyBurn.setText("daily burn: "+(int)daily);
            vStepBurn.setText("calorie burn per step: "+String.valueOf(stepBurn));
            double sum =daily + steps*stepBurn;
            vBurned.setText("totoal calorie burned today: "+"\n"+(int)sum);
            if(sum-cConsum-cGoal<0)
            {
                vBurned.setTextColor(Color.RED);
            }
            else
            {
                vBurned.setTextColor(Color.GREEN);
            }
            cBurned = sum;
        }
    }
    public static class PostReportTask extends AsyncTask<Void,Void,Void>
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        private String curDate = sdf.format(new Date()) +"T00:00:00+05:00";

        com.example.navdrawertest3.RestEntity.userId userId = new userId(String.valueOf(uid));
//[{"calBurned":200.0,"calConsum":162.0,"calGoal":50.0,"reportDate":"2019-03-19T00:00:00+11:00","reportId":1,"steps":1000,"userId":{"


        @Override
        protected Void doInBackground(Void... voids) {
            int reportId = A1DBAPI.getNewReportId();
            A1DBAPI.postReport(new Report(cBurned,cConsum,cGoal,curDate,reportId,steps,userId));
            return null;
        }
        @Override
        protected void onPostExecute(Void avoid)
        {
            DeleteDatabase deleteTask = new DeleteDatabase();
            deleteTask.execute();
        }
    }

    private static class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            stepsDB.stepsDao().deleteAll();
            return null; }

    }

}
