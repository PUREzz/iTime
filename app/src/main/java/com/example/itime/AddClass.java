package com.example.itime;

import java.io.Serializable;
import java.util.ArrayList;

public class AddClass implements Serializable {
    private String title;
    private int imageId;
    private String countdown;

    public AddClass(String title, int imageid,String countdown) {
        this.title = title;
        this.imageId = imageid;
        this.countdown = countdown;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageid(int imageid) {
        this.imageId = imageid;
    }

    public static ArrayList<AddClass> getAllAddClass(){
        ArrayList<AddClass> addClasses = new ArrayList<>();

        addClasses.add(new AddClass("选择日期时间",android.R.drawable.ic_menu_recent_history,""));
        addClasses.add(new AddClass("选择图片",android.R.drawable.ic_menu_gallery,""));
        addClasses.add(new AddClass("增加标签",android.R.drawable.stat_notify_more,""));
        return addClasses;
    }
}
