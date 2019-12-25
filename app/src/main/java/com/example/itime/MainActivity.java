package com.example.itime;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.CountDownTimer;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<DataClass> datas = new ArrayList<DataClass>();
    private DataClass theclass;
    private DataSaver dataSaver;
    private AddClassSaver addClassSaver;
    private ArrayList<AddClass> addClasses = new ArrayList<AddClass>();
    private AddClass addClass;

    public static AddAdapter theAdapter;

    private ListView listView;

    private String countdowntime;
    private int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,101);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if(resultCode==RESULT_OK)
                {
                    theclass = (DataClass)data.getSerializableExtra("dataclass");
                    datas.add(theclass);

                    String str = theclass.getTimeClass().getStr();
                    addClasses.add(new AddClass(theclass.getTitle()+"\n"+str,theclass.getImageId(),""));


                    dataSaver.save();
                    addClassSaver.save();
                    theAdapter.notifyDataSetChanged();

                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    private void init() {
        dataSaver = new DataSaver(this);
        datas = dataSaver.load();

        addClassSaver = new AddClassSaver(this);
        addClasses = addClassSaver.load();

        theAdapter = new AddAdapter(this, R.layout.list_item, addClasses);

        if(datas.size()!=0){
            if(getSupportActionBar() != null){      //去除应用标题
                getSupportActionBar().hide();
                getWindow().setFlags
                        (
                                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        );
            }
        }

        Calendar calendar = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(datas.size()!=0) {
            for (DataClass theclass : datas) {
                final AddClass theclass2 = addClasses.get(count);
                try {
                    Date d1 = df.parse(theclass.getTimeClass().getStr() + ":00");
                    Date d2 = df.parse(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
                    long date = d1.getTime() - d2.getTime();
                    CountDownTimer countDownTimer = new CountDownTimer(date, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            DecimalFormat dec = new DecimalFormat("##.##");
                            long day = millisUntilFinished / (1000 * 60 * 60 * 24);
                            countdowntime = day + " DAYS ";
                            theclass2.setCountdown(countdowntime);
                            theAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFinish() {
                            countdowntime = "  End of\ncountdown";
                            theclass2.setCountdown(countdowntime);
                            theAdapter.notifyDataSetChanged();
                        }
                    };

                    countDownTimer.start();
                } catch (Exception e) {
                }
                count++;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSaver.save();
        addClassSaver.save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
