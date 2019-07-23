package com.example.navdrawertest3.RestEntity;

public class Report {
    //[{"calBurned":200.0,"calConsum":162.0,"calGoal":50.0,"reportDate":"2019-03-19T00:00:00+11:00","reportId":1,"steps":1000,"userId":{"
    private double calBurned;
    private double calConsum;
    private double calGoal;
    private String reportDate;
    private int reportId;
    private int steps;
    private userId userId;

    public Report(double calBurned, double calConsum, double calGoal, String reportDate, int reportId, int steps, com.example.navdrawertest3.RestEntity.userId userId) {
        this.calBurned = calBurned;
        this.calConsum = calConsum;
        this.calGoal = calGoal;
        this.reportDate = reportDate;
        this.reportId = reportId;
        this.steps = steps;
        this.userId = userId;
    }
}
