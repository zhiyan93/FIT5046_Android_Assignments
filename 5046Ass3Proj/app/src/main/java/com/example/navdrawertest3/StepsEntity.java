package com.example.navdrawertest3;


import android.arch.persistence.room.ColumnInfo;
        import android.arch.persistence.room.Entity;
        import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class StepsEntity {
    @PrimaryKey(autoGenerate = true)
    public int rid;
    @ColumnInfo(name = "userName")
    public String userName;
    @ColumnInfo(name = "steps")
    public int steps;
    @ColumnInfo(name = "time")
    public String time;

    public StepsEntity(String userName, int steps, String time) {
        this.userName = userName;
        this.steps = steps;
        this.time = time;
    }

    public int getId() {
        return rid;
    }

    public String getUserName() {
        return userName;
    }

    public int getSteps() {
        return steps;
    }

    public String getTime() {
        return time;
    }

  /*  public void setRid(int rid) {
        this.rid = rid;
    }*/

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
