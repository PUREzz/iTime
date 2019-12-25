package com.example.itime;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AddClassSaver {
    private Context context;
    private ArrayList<AddClass> classes = new ArrayList<AddClass>();
    public AddClassSaver(Context context) {
        this.context = context;
    }

    public ArrayList<AddClass> getClasses(){
        return classes;
    }

    public void save(){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput("iTimeAdd.txt",Context.MODE_PRIVATE));

            outputStream.writeObject(classes);
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<AddClass> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("iTimeAdd.txt"));
            classes = (ArrayList<AddClass>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
}
