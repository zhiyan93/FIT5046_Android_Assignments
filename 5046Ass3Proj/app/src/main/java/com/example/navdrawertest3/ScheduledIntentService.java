package com.example.navdrawertest3;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
public class ScheduledIntentService extends IntentService {

    public ScheduledIntentService() {
        super("ScheduledIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("start  service");
       CalTrackerFragment.PostReportTask task = new CalTrackerFragment.PostReportTask();
       task.execute();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }
}
