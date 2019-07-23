package com.example.navdrawertest3;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportFragment extends Fragment {

    private View vHome;
    private SharedPreferences sp;
    private CalendarView calendarView;
    private TextView vRepDate, vBurn, vConsum, vGoal;
    private Button getReportBtn, barchartBtn;
    private DatePicker datePicker;
    private Calendar calendar;
    private EditText eSdate, eEdate;

    private PieChart pieChart;
    private BarChart barChart;
    private ArrayList<PieEntry> yValues = new ArrayList<>();
    private List<BarEntry> yVals1;
    private List<BarEntry> yVals2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        vHome = inflater.inflate(R.layout.fragment_report, container, false);
        calendarView = vHome.findViewById(R.id.calendarPicker);
        vRepDate = vHome.findViewById(R.id.vRepDate);
        vBurn = vHome.findViewById(R.id.vBurn);
        vConsum = vHome.findViewById(R.id.vConsum);
        vGoal = vHome.findViewById(R.id.vGoal);
        getReportBtn = vHome.findViewById(R.id.getReportBtn);
        pieChart = vHome.findViewById(R.id.piechart);
        barChart = vHome.findViewById(R.id.barchart);
        barchartBtn = vHome.findViewById(R.id.barchartBtn);
        eSdate = vHome.findViewById(R.id.eSdate);
        eEdate = vHome.findViewById(R.id.eEdate);
        sp = ((MainActivity) getActivity()).getSP();
        final int uid = sp.getInt("userId", 0);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String strMonth = "";
                strMonth = month < 10 ? ("0" + (month + 1)) : ("" + (month + 1));
                String strDay = "";
                strDay = dayOfMonth < 10 ? ("0" + dayOfMonth) : ("" + dayOfMonth);
                String selectDate = "" + year + "-" + strMonth + "-" + strDay;
                vRepDate.setText(selectDate);
            }
        });
        getReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = vRepDate.getText().toString();
                GetReportTask task = new GetReportTask();
                task.execute(String.valueOf(uid), date);

            }
        });
        barchartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdate = eSdate.getText().toString();
                String edate = eEdate.getText().toString();
                GetPeriodReportTask task = new GetPeriodReportTask();
                task.execute(String.valueOf(uid), sdate, edate);
            }
        });

        return vHome;
    }

    public void setPieChart() {
        //this.pieChart = pieChart;
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "calorie burn report");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.setData(pieData);
    }

    public void setBarChart() {


        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false); ///
        barChart.setDrawGridBackground(false);

        XAxis xl = barChart.getXAxis();
        xl.setGranularity(0.36f);
        xl.setCenterAxisLabels(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(10f);  //
        barChart.getAxisRight().setEnabled(true);

        //data
        float groupSpace = 0.04f;
        float barSpace = 0.00f;
        float barWidth = 0.36f;

        int startDate = 0;


       /* List<BarEntry> yVals1 = new ArrayList<BarEntry>();
        List<BarEntry> yVals2 = new ArrayList<BarEntry>();*/

        /*for (int i = startYear; i < endYear; i++) {
            yVals1.add(new BarEntry(i, 0.4f));
        }

        for (int i = startYear; i < endYear; i++) {
            yVals2.add(new BarEntry(i, 0.7f));
        }*/

        BarDataSet set1, set2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Consume");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "Burned");
            set2.setColor(Color.rgb(164, 228, 251));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            barChart.setData(data);
        }

        barChart.getBarData().setBarWidth(barWidth);
        barChart.groupBars(startDate, groupSpace, barSpace);
        barChart.invalidate();
    }

    private class GetReportTask extends AsyncTask<String, Void, Void> {
        float burn = 0;
        float consum = 0;
        float goal = 0;

        @Override
        protected Void doInBackground(String... strings) {

            double[] out = A1DBAPI.getReport(strings[0], strings[1]);
            burn = (float) out[0];
            consum = (float) out[1];
            goal = (float) out[2];
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            vBurn.setText("B" + burn);
            vConsum.setText("C" + consum);
            vGoal.setText("G" + goal);

            // burn = Float.valueOf(vBurn.getText().toString());
            // consum = Float.valueOf(vConsum.getText().toString());
            //goal = Float.valueOf(vGoal.getText().toString());
            yValues = new ArrayList<>();
            yValues.add(new PieEntry(burn, "burn"));
            yValues.add(new PieEntry(consum, "consume"));
            yValues.add(new PieEntry(Math.abs(goal - (consum - burn)), "remaining"));
            setPieChart();
        }
    }

    private class GetPeriodReportTask extends AsyncTask<String, Void, Void> {
        ArrayList<Float> consum = new ArrayList<>();
        ArrayList<Float> burn = new ArrayList<>();
        ArrayList<String> reportDate = new ArrayList<>();

        @Override
        protected Void doInBackground(String... strings) {
            JSONArray out = A1DBAPI.getPeriodReport(strings[0], strings[1], strings[2]);
            for (int i = 0; i < out.length(); i++) {
                try {
                    burn.add(Float.valueOf(out.getJSONObject(i).getString("calBurned")));
                    consum.add(Float.valueOf(out.getJSONObject(i).getString("calConsum")));
                    reportDate.add(out.getJSONObject(i).getString("reportDate").substring(0, 10));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            yVals1 = new ArrayList<BarEntry>();
            yVals2 = new ArrayList<BarEntry>();

            for (int i = 0; i < consum.size(); i++) {
                yVals1.add(new BarEntry(i, consum.get(i)));
                yVals2.add(new BarEntry(i, burn.get(i)));
                System.out.print(reportDate.get(i));

            }
            XAxis xl = barChart.getXAxis();
             // xl.setGranularity(0.36f);
             // xl.setGranularityEnabled(true);
            // String[] reportArr = reportDate.toArray(new String[reportDate.size()]);
            xl.setValueFormatter(new IndexAxisValueFormatter(reportDate));
            setBarChart();


        }


    }
}
