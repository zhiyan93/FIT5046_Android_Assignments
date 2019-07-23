package com.example.navdrawertest3;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StepsFragment extends Fragment {
    private View vHome;
    private EditText eStepInp;
    private Button stepsAddBtn,clearStepBtn;
    private ListView stepList;
    private TextView vDBresult;
    StepsDB stepsDB = null;
    String name = "";
    private SharedPreferences sp;

    //final SharedPreferences SPLoginUser = getActivity().getSharedPreferences("loginUser", Context.MODE_PRIVATE);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        //getActivity().setContentView(R.layout.activity_main);
        vHome = inflater.inflate(R.layout.steps, container, false);
        eStepInp = vHome.findViewById(R.id.eStepInp);
        stepsAddBtn = vHome.findViewById(R.id.stepsAddBtn);
        stepList = vHome.findViewById(R.id.stepList);
        vDBresult = vHome.findViewById(R.id.vDBresult);
        clearStepBtn = vHome.findViewById(R.id.clearStepBtn);
        stepsDB = ((MainActivity)getActivity()).getStepsDB();
        sp = ((MainActivity)getActivity()).getSP();
        name = sp.getString("userName", "");
        stepsAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDatabase insertDatabase  = new InsertDatabase();
                 insertDatabase.execute();
                ReadDatabase readTask = new ReadDatabase();
                readTask.execute();
            }
        });
        clearStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             DeleteDatabase deleteTask = new DeleteDatabase();
             deleteTask.execute();
                ReadDatabase readTask = new ReadDatabase();
                readTask.execute();
            }
        });
        ReadDatabase readTask = new ReadDatabase();
        readTask.execute();
        return vHome;
    }

    private class InsertDatabase extends AsyncTask<String, Void, String> {
        private String text = "";
        @Override
        protected void onPreExecute()
        {
            text = eStepInp.getText().toString();
        }
        @Override
        protected String doInBackground(String... params) {
            // String[] record = new String[3];

            if (!text.isEmpty()) {


                int steps = Integer.valueOf(eStepInp.getText().toString());
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
               String sdate = dateFormat.format(date);
                StepsEntity stepsEntity = new StepsEntity(name, steps, sdate);
                long id = stepsDB.stepsDao().insert(stepsEntity);
                return (id + " " + name + " " + steps + " " + dateFormat.format(date)+" added");
                //  else return ""; } else return "";
            }
            else return "";
        }
        @Override
        protected void onPostExecute(String result)
        {

            vDBresult.setText(result);
            eStepInp.setText("");
        }

    }

    private class ReadDatabase extends AsyncTask<Void, Void,String> {

        ArrayList<String> listItems=new ArrayList<String>();
        //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
        ArrayAdapter<String> adapter;
        @Override protected String doInBackground(Void... params) {
            List<StepsEntity> records = stepsDB.stepsDao().getAll();
            if (!(records.isEmpty() || records == null)) {
                for (StepsEntity temp : records) {
                    String oneRecord = (""+temp.getId() + " " + temp.getUserName() + " " + temp.getSteps() + " " + temp.getTime() + "\n");
                    listItems.add(oneRecord);
                }

            }
            return " ";
        }
        @Override protected void onPostExecute(String s)
        {
            adapter=new ArrayAdapter<String>(getActivity(), R.layout.list_text, listItems);
            stepList.setAdapter(adapter);
        }

        }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            stepsDB.stepsDao().deleteAll();
            return null; }
        protected void onPostExecute(Void param) {
            vDBresult.setText("All data was deleted");
        }
    }

}
