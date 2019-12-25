package com.example.itime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.itime.MainActivity.theAdapter;

public class CheckActivity extends AppCompatActivity {

    public TextView textView1,textView2,textView3;
    private ImageButton button_return,button_del,button_change;
    private DataClass dataClass;
    private ArrayList<DataClass> dataClasses = new ArrayList<DataClass>();
    private CountTime timer;
    private DataSaver dataSaver;
    private int position;
    private AddClassSaver addClassSaver;
    private ArrayList<AddClass> addClasses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Init();

        //dataClasses = (ArrayList<DataClass>) getIntent().getSerializableExtra("dataclasses");

        position = getIntent().getIntExtra("position",0);
        dataClass = dataClasses.get(position);

        textView1.setText(dataClass.getTitle());
        textView1.setTextSize((float) 25);
        textView1.setTextColor(Color.parseColor("#FFFFFF"));

        textView2.setText(dataClass.getTimeClass().getStr());
        textView2.setTextSize((float) 25);
        textView2.setTextColor(Color.parseColor("#FFFFFF"));

        Calendar calendar = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(dataClass.getTimeClass().getStr()+":00");
            Date d2 = df.parse(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
            long date = d1.getTime() - d2.getTime();
            timer = new CountTime(date, 1000, textView3);
            timer.start();
        }catch (Exception e) {
        }

        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(CheckActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("询问")
                        .setMessage("你确定要删除这条吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dataClasses.remove(position);
                                addClasses.remove(position);
                                dataSaver.save();
                                addClassSaver.save();
                                Intent intent = new Intent(CheckActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create().show();

            }
        });

        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckActivity.this,AddActivity.class);
                intent.putExtra("dataclass",dataClass);
                intent.putExtra("data_title",dataClass.getTitle());
                intent.putExtra("data_description",dataClass.getDescription());
                startActivityForResult(intent,202);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 202:
                if(resultCode==RESULT_OK)
                {
                    dataClasses = dataSaver.load();
                    dataClass = (DataClass)data.getSerializableExtra("dataclass");
                    dataClasses.set(position,dataClass);

                    String str = dataClass.getTimeClass().getStr();
                    AddClass addclass = new AddClass(dataClass.getTitle()+"\n"+str,dataClass.getImageId(),"");
                    addClasses.set(position,addclass);

                    dataSaver.save();
                    addClassSaver.save();
                    theAdapter.notifyDataSetChanged();


                    Intent intent = new Intent(CheckActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
    private void Init() {
        if(getSupportActionBar() != null){      //去除应用标题
            getSupportActionBar().hide();
            getWindow().setFlags
                    (
                            WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN
                    );
        }

        textView1 = findViewById(R.id.text_view_show_time_title);
        textView2 = findViewById(R.id.text_view_show_time_data);
        textView3 = findViewById(R.id.text_view_show_time_count);

        button_return = findViewById(R.id.image_button_check_return);
        button_del = findViewById(R.id.image_button_del);
        button_change = findViewById(R.id.image_button_change);

        dataSaver = new DataSaver(this);
        dataClasses = dataSaver.load();

        addClassSaver = new AddClassSaver(this);
        addClasses = addClassSaver.load();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }



}
