package com.example.navdrawertest3;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.navdrawertest3.RestEntity.Credential;
import com.example.navdrawertest3.RestEntity.Usr;
import com.example.navdrawertest3.RestEntity.userId;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoginFragment extends Fragment {
    private View vLogin;
    private EditText eUserName, ePassword, eFName, eSName, eEmail, eSetHeight, eSetWeight, eAddress, ePostcode, eStepMile, eSetUserName, eSetPassword, eRePassword, eDob;
    private TextView tFeedBack,  regLog;
    private Button bLogSing, regBtn, submitBtn;
    private ProgressBar loadingBar;
    private String loginId;
    private Spinner spLevel;
    private ScrollView regScroll;
    private CalendarView calendar;
    private RadioButton rbMale, rbFemale;
    private RadioGroup rgGender;
    private SharedPreferences sp;
    final Calendar myCalendar = Calendar.getInstance();
    //final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
// Set Variables and listener
        vLogin = inflater.inflate(R.layout.login, container, false);
        eUserName = (EditText) vLogin.findViewById(R.id.username);
        ePassword = (EditText) vLogin.findViewById(R.id.password);
        tFeedBack = (TextView) vLogin.findViewById(R.id.logFeedBack);
        bLogSing = (Button) vLogin.findViewById(R.id.loginBtn);
        loadingBar = (ProgressBar) vLogin.findViewById(R.id.loadingBar);
        spLevel = (Spinner) vLogin.findViewById(R.id.spinnerLevel);
        regScroll = (ScrollView) vLogin.findViewById(R.id.regScrollView);
        regScroll.setVisibility(View.INVISIBLE);
        regBtn = (Button) vLogin.findViewById(R.id.regBtn);
        submitBtn = (Button) vLogin.findViewById(R.id.submitBtn);
        calendar = (CalendarView) vLogin.findViewById(R.id.calendarIn);
        eDob = (EditText) vLogin.findViewById(R.id.eDob);
        eFName = (EditText) vLogin.findViewById(R.id.eFName);
        eSName = (EditText) vLogin.findViewById(R.id.eSName);
        eEmail = (EditText) vLogin.findViewById(R.id.eEmail);
        eSetHeight = (EditText) vLogin.findViewById(R.id.eSetHeight);
        eSetWeight = (EditText) vLogin.findViewById(R.id.eSetWeight);
        eAddress = (EditText) vLogin.findViewById(R.id.eAddress);
        ePostcode = (EditText) vLogin.findViewById(R.id.ePostcode);
        eStepMile = (EditText) vLogin.findViewById(R.id.eStepMile);
        eSetUserName = (EditText) vLogin.findViewById(R.id.eSetUserName);
        eSetPassword = (EditText) vLogin.findViewById(R.id.eSetPassword);
        eRePassword = (EditText) vLogin.findViewById(R.id.eRePassword);
        regLog = (TextView) vLogin.findViewById(R.id.regLog);
        rgGender = (RadioGroup) vLogin.findViewById(R.id.rgGender) ;
        rbMale = vLogin.findViewById(R.id.rbMale);
        rbFemale= vLogin.findViewById(R.id.rbFemale);
        sp = ((MainActivity)getActivity()).getSP();

       final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        eDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(vLogin.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //bLogSing.setOnClickListener(this);
        bLogSing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtnAct();
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regScroll.setVisibility(View.VISIBLE);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtnAct();
            }
        });
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String strMonth ="";
                strMonth = month<10?( "0"+(month+1)):(""+(month+1));
               String strDay = dayOfMonth<10?("0"+dayOfMonth):(""+dayOfMonth);
                String selectDate = "" + year + "-" + strMonth + "-" + strDay;
                eDob.setText(selectDate);
            }
        });

        String[] levels = {"level 1", "level 2", "level 3", "level 4", "level 5"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_text, levels);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spLevel.setAdapter(langAdapter);
        spLevel.setSelection(0);

        return vLogin;

    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eDob.setText(sdf.format(myCalendar.getTime()));
    }
    private void loginBtnAct() {

        // Gather user input
        String userName = eUserName.getText().toString();
        String password = ePassword.getText().toString();
// Validate user input
        if (userName.isEmpty()) {
            eUserName.setError("User Name is required!");
            return;
        }
        if (password.isEmpty()) {
            ePassword.setError("Pass word is required!");
            return;
        }
        LoginAsyncTaskParam login = new LoginAsyncTaskParam();
        login.execute();

    }

    private void recordUser(String userName) {

        ((MainActivity)getActivity()).getSP().edit().putString("userName", userName).apply();

    }

    private void submitBtnAct() {
        EditText[] editArr = {eFName, eSName, eEmail, eSetHeight, eSetWeight, eAddress, ePostcode, eStepMile, eSetUserName, eSetPassword, eRePassword,eDob};
        for (EditText e : editArr) {
            if (chkIsEmpty(e)) {
                regLog.setText("Sign up fail coz empty attribute");
                return;
            }

        }
        CheckEmailDupAsyncTask ec = new CheckEmailDupAsyncTask();
        ec.execute();
        if (eEmail.getText().toString().isEmpty())
        {return;}
        CheckNameDupAsyncTask nc = new CheckNameDupAsyncTask();
        nc.execute();
        if (eSetUserName.getText().toString().isEmpty())
        { return;}
        String fName = eFName.getText().toString();
        String sName = eSName.getText().toString();
        String email = eEmail.getText().toString();
        String height = eSetHeight.getText().toString();
        String weight = eSetWeight.getText().toString();
        String address = eAddress.getText().toString();
        String postcode = ePostcode.getText().toString();
        String stepMile = eStepMile.getText().toString();
        String userName = eSetUserName.getText().toString();
        String passwd = eSetPassword.getText().toString();
        String dob = eDob.getText().toString();
        String gender = rbMale.isChecked()?"M":"F";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format( new Date());
        String activityLevel = spLevel.getSelectedItem().toString().substring(6,7) ;
        System.out.println(activityLevel);
        PostCredentialTask postCredentialTask = new PostCredentialTask();
      //  postCredentialTask.execute("name","pw","2019-01-01","10","fname","sname","email","2019-03-12","170","60","M","address","1234","3","2000");
        postCredentialTask.execute(userName,md5hashing(passwd),dateString,"",fName,sName,email,dob,height,weight,gender,address,postcode,activityLevel,stepMile);
    }

    public String md5hashing(String text)
    {   String hashtext = null;
        try
        {
            String plaintext = text;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while(hashtext.length() < 32 ){
                hashtext = "0"+hashtext;
            }
        } catch (Exception e1)
        {
            // TODO: handle exception
            //JOptionPane.showMessageDialog(null,e1.getClass().getName() + ": " + e1.getMessage());
        }
        return hashtext;
    }

    private boolean chkIsEmpty(EditText editText) {
        boolean result = false;
        String content = editText.getText().toString();
        int id = editText.getId();
        String viewname = getResources().getResourceEntryName(id);
        if (content.isEmpty()) {
            result = true;
            editText.setError(viewname + " cannot be empty");
        }
        return result;
    }

    private class CheckNameDupAsyncTask extends AsyncTask<String, Void, String> {
        private String username = "";

        private String nameResult = "";

        @Override
        protected void onPreExecute() {

            username = eSetUserName.getText().toString();

        }

        @Override
        protected String doInBackground(String... strings) {
            nameResult = A1DBAPI.findloginName(username);

            return nameResult;

        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.isEmpty()) {
                eSetUserName.setError(username + " name is duplicated! ");
                regLog.setText(username + " name is duplicated! ");
                eSetUserName.setText("");
            }
        }
    }

        private class CheckEmailDupAsyncTask extends AsyncTask<String, Void, String> {
            private String email = "";

            private String emailResult = "";

            @Override
            protected void onPreExecute() {

                email = eEmail.getText().toString();

            }

            @Override
            protected String doInBackground(String... strings) {
                emailResult = A1DBAPI.findEmail(email);

                return emailResult;

            }

            @Override
            protected void onPostExecute(String result) {
                if (!result.equals("[]")) {
                    eEmail.setError(email + " email is duplicated! ");
                    regLog.setText(email + " email is duplicated! ");
                    eEmail.setText("");
                }
            }


        }


        private class LoginAsyncTaskParam extends AsyncTask<String, Void, String> {

            private String param = "";
            String userName = "";
            String password = "";
            boolean exist = false;

            public String md5hashing(String text)
            {   String hashtext = null;
                try
                {
                    String plaintext = text;
                    MessageDigest m = MessageDigest.getInstance("MD5");
                    m.reset();
                    m.update(plaintext.getBytes());
                    byte[] digest = m.digest();
                    BigInteger bigInt = new BigInteger(1,digest);
                    hashtext = bigInt.toString(16);
                    // Now we need to zero pad it if you actually want the full 32 chars.
                    while(hashtext.length() < 32 ){
                        hashtext = "0"+hashtext;
                    }
                } catch (Exception e1)
                {
                    // TODO: handle exception
                    //JOptionPane.showMessageDialog(null,e1.getClass().getName() + ": " + e1.getMessage());
                }
                return hashtext;
            }

            @Override
            protected void onPreExecute() {
                userName = eUserName.getText().toString();
                password = md5hashing(ePassword.getText().toString());
                loadingBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... params) {
                return A1DBAPI.findloginName(userName);

            }

            @Override
            protected void onPostExecute(String name) {
                JSONObject userObj = new JSONObject();
                String pw = "";

                int uid = 0;
                String uAdd = "Sydney";
                try {
                    userObj = new JSONObject(name);

                    pw = userObj.getString("pwHash");
                    uid = userObj.getJSONObject("userId").getInt("userId");
                    uAdd = userObj.getJSONObject("userId").getString("address");
                } catch (JSONException e) {
                    //  e.printStackTrace();
                    tFeedBack.setText("account is not exist !");
                    loadingBar.setVisibility(View.INVISIBLE);
                    return;
                }
                System.out.println(pw);
                if (!pw.equals(password)) {
                    tFeedBack.setText("incorrect password !");
                } else {
                    String feedback = "Welcome " + userName + " !";
                    tFeedBack.setText(feedback);
                    recordUser(userName);
                    sp.edit().putInt("userId",uid).apply();
                    sp.edit().putString("userAdd",uAdd).commit();
                }
                loadingBar.setVisibility(View.INVISIBLE);


            }

        }

    private class PostCredentialTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {

            String sid = A1DBAPI.getNewUsrId();
            Integer iid = Integer.valueOf(sid);
            int incId = iid+1;
            Usr user = new Usr(incId,params[4],params[5],params[6],params[7]+"T00:00:00+05:00",Double.valueOf(params[8]),Double.valueOf(params[9]),params[10].charAt(0),params[11],params[12],Integer.valueOf(params[13]),Integer.valueOf(params[14]));
            //user.setUserId(iid+1);
            A1DBAPI.postUsr(user);

            Credential credential=new Credential(params[0],params[1], params[2]+"T00:00:00+11:00",new userId(""+incId));
            A1DBAPI.postCredential(credential);
            return "User and Credential was added";
            //himanshu.pahuja@monash.edu
        }

    }



    }




