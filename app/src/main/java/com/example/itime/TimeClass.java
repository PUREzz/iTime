package com.example.itime;

import java.io.Serializable;

public class TimeClass implements Serializable{
    public TimeClass(int data_year, int data_month, int data_day, int day_hour, int day_min) {
        this.data_year = data_year;
        this.data_month = data_month;
        this.data_day = data_day;
        this.day_hour = day_hour;
        this.day_min = day_min;
    }

    public TimeClass() {
        this.data_year = 0;
        this.data_month = 0;
        this.data_day = 0;
        this.day_hour = 0;
        this.day_min = 0;
    }

    public int getData_year() {
        return data_year;
    }

    public void setData_year(int data_year) {
        this.data_year = data_year;
    }

    public int getData_month() {
        return data_month;
    }

    public void setData_month(int data_month) {
        this.data_month = data_month;
    }

    public int getData_day() {
        return data_day;
    }

    public void setData_day(int data_day) {
        this.data_day = data_day;
    }

    public int getDay_hour() {
        return day_hour;
    }

    public void setDay_hour(int day_hour) {
        this.day_hour = day_hour;
    }

    public int getDay_min() {
        return day_min;
    }

    public void setDay_min(int day_min) {
        this.day_min = day_min;
    }

    public String getStr(){
        int i = this.data_month+1;
        String str = this.data_year+"-"+i+"-"+this.data_day+" "+this.day_hour+":"+this.day_min;
        return str;
    }

    private int data_year,data_month,data_day,day_hour,day_min;

}
