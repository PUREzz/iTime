package com.example.itime;

import java.io.Serializable;

public class DataClass implements Serializable {
    private String title;          //标题

    private int imageId;           //图片

    private TimeClass timeClass;   //时间

    private String description;    //描述

    private String label;    //标签

    public DataClass(String title, int imageId, TimeClass timeClass, String description) {
        this.title = title;
        this.imageId = imageId;
        this.timeClass = timeClass;
        this.description = description;
    }

    public DataClass() {
        title = "";
        imageId = 0;
        description = "";
        timeClass = new TimeClass();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TimeClass getTimeClass() {
        return timeClass;
    }

    public void setTimeClass(TimeClass timeClass) {
        this.timeClass = timeClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
