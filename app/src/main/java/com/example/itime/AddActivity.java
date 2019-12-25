package com.example.itime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private AddAdapter addAdapter;
    private ArrayList<AddClass> theClass;
    private ImageButton button_ok,button_cancel;
    private EditText text_title,text_description;
    private String time,time1,time2,title,description,label=null;
    private int data_year,data_month,data_day,data_hour,data_min;
    private ImageView imageView;
    private Bitmap bitmap;
    private BitmapFactory.Options options;

    private DataClass dataclass = null;
    private int editPosition;

    private TimeClass timeClass;

    private DataClass dataClass;
    //设置返回码：标识本地图库
    private static final int RESULT_IMAGE=100;
    //设置MIME码：表示image所有格式的文件均可
    private static final String IMAGE_TYPE="image/*";
    //实例化Intent,传入ACTION_PICK,表示从Item中选取一个数据返回

    private static final String[] PERMISSION_EXTERNAL_STORAGE = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        verifyStoragePermissions(this);

        init();


        ListView listView = (ListView) findViewById(R.id.list_view_add);

        listView.setAdapter(addAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    showData();

                }
                if(i==1){
                    Intent intent=new Intent(Intent.ACTION_PICK,null);
                    //设置Data和Type属性，前者是URI：表示系统图库的URI,后者是MIME码
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_TYPE);
                    //启动这个intent所指向的Activity
                    startActivityForResult(intent,RESULT_IMAGE);
                }
                if(i==2){
                    ShowChoise();
                }
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                if(data_year==0&&data_month==0){
                    timeClass = dataClass.getTimeClass();
                }
                else{
                    timeClass = new TimeClass(data_year,data_month,data_day,data_hour,data_min);
                }

                title = text_title.getText().toString();
                description = text_description.getText().toString();

                dataClass.setTimeClass(timeClass);
                dataClass.setTitle(title);
                dataClass.setDescription(description);
                dataClass.setImageId(R.drawable.img_test);
                dataClass.setLabel(label);

                intent.putExtra("dataclass",dataClass);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showData(){
        datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                showTime();
                //monthOfYear 得到的月份会减1所以我们要加1
                time1 = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + Integer.toString(dayOfMonth)+" ";
//                time = time1 + time2;
//                calendar.set(year,monthOfYear+1,dayOfMonth);
//                AddClass theDataText = theClass.get(0);
//                theDataText.setTitle("Data\n" +
//                        time);
//                addAdapter.notifyDataSetChanged();
                 data_year = year;
                 data_month = monthOfYear;
                 data_day = dayOfMonth;

            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private void showTime(){
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time2 = "" + Integer.toString(hourOfDay) +":"+ Integer.toString(minute);

                time = time1 + time2;

                AddClass theDataText = theClass.get(0);
                theDataText.setTitle("Data\n" +
                        time);
                addAdapter.notifyDataSetChanged();

                data_hour = hourOfDay;
                data_min = minute;

            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void init(){
        if(getSupportActionBar() != null){      //去除应用标题
            getSupportActionBar().hide();
            getWindow().setFlags
                    (
                            WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN
                    );
        }

        theClass = AddClass.getAllAddClass();

        addAdapter = new AddAdapter(this, R.layout.list_item, theClass);

        text_title = findViewById(R.id.edit_text_title);
        text_description = findViewById(R.id.edit_text_description);
        imageView = findViewById(R.id.image_view_add);

        button_ok = findViewById(R.id.add_button_sure);
        button_cancel = findViewById(R.id.add_button_return);

        calendar = Calendar.getInstance();

        dataclass = (DataClass) getIntent().getSerializableExtra("dataclass");
        if(dataclass!=null){
            dataClass = dataclass;
            timeClass = dataClass.getTimeClass();
            calendar.set(timeClass.getData_year(),timeClass.getData_month(),timeClass.getData_day(),timeClass.getDay_hour(),timeClass.getDay_min());  //设置初始时间

            int i = timeClass.getData_month()+1;
            time = timeClass.getData_year()+"-"+i+"-"+timeClass.getData_day()+" "+timeClass.getDay_hour()+":"+timeClass.getDay_min();
            AddClass theDataText = theClass.get(0);
            theDataText.setTitle("Data\n" +
                    time);
            AddClass thelabelText = theClass.get(2);
            label = dataClass.getLabel();
            thelabelText.setTitle("增加标签\n" +
                    label);
            addAdapter.notifyDataSetChanged();           //修改
        }
        else{
            dataClass = new DataClass();
        }

        String dataTitle= getIntent().getStringExtra("data_title");
        if(dataTitle!=null) {
            text_title.setText(dataTitle);
        }

        String dataDescription= getIntent().getStringExtra("data_description");
        if(dataDescription!=null) {
            text_description.setText(dataDescription);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==RESULT_IMAGE&&data!=null){
                //相册
                //通过获取当前应用的contentResolver对象来查询返回的data数据
                Cursor cursor=this.getContentResolver().query(data.getData(),null,null,null,null);
                //将cursor指针移动到数据首行
                cursor.moveToFirst();
                //获取字段名为_data的数据
                String imagePath=cursor.getString(cursor.getColumnIndex("_data"));


                try{
//                    options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds=true;
//                    bitmap = BitmapFactory.decodeFile(imagePath, options);
//
//                    //options.inSampleSize = calculateInSampleSize(options, imageView.getWidth(), imageView.getHeight());
//                    options.inSampleSize = 4;
//                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeFile(imagePath);

                }catch (OutOfMemoryError e){
                }
                imageView.setImageBitmap(bitmap);
                //销毁cursor对象，释放资源
                cursor.close();


            }
        }
    }

    private void verifyStoragePermissions(Activity activity) {
        int permissionWrite = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionWrite != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_EXTERNAL_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        //先根据宽度进行缩小
        while (width / inSampleSize > reqWidth) {
            inSampleSize++;
        }
        //然后根据高度进行缩小
        while (height / inSampleSize > reqHeight) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private void ShowChoise()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择一个标签");
        //    指定下拉列表的显示数据
        final String[] labels = {"生日", "纪念日", "节日", "学习", "工作"};
        //    设置一个下拉的列表选择项
        builder.setItems(labels, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                label = labels[which];
                AddClass thelabelText = theClass.get(2);
                thelabelText.setTitle("增加标签\n" +
                        label);
                addAdapter.notifyDataSetChanged();
            }
        });
        AlertDialog r_dialog = builder.create();
        r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        r_dialog.show();

    }
}
