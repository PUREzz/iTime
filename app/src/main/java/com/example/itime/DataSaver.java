package com.example.itime;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataSaver {
    private Context context;
    private ArrayList<DataClass> classes = new ArrayList<DataClass>();
    public DataSaver(Context context) {
        this.context = context;
    }

    public ArrayList<DataClass> getClasses(){
        return classes;
    }

    public void save(){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput("iTime.txt",Context.MODE_PRIVATE));

            outputStream.writeObject(classes);
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<DataClass> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("iTime.txt"));
            classes = (ArrayList<DataClass>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
}
