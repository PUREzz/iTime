package com.example.itime.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.itime.CheckActivity;
import com.example.itime.CountTime;
import com.example.itime.DataClass;
import com.example.itime.DataSaver;
import com.example.itime.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.example.itime.MainActivity.theAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ListView listView;
    private TextView textView1,textView2,textView3;
    private View root;
    private DataSaver dataSaver;
    private ArrayList<DataClass> dataClasses = new ArrayList<DataClass>();
    private DataClass dataClass;
    private CountDownTimer timer;
    private ConstraintLayout constraintLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        textView1 = root.findViewById(R.id.text_view_home_1);
        textView2 = root.findViewById(R.id.text_view_home_2);
        textView3 = root.findViewById(R.id.text_view_home_3);
        constraintLayout = root.findViewById(R.id.home_constraintLayout);

        listView = root.findViewById(R.id.home_list_view);
        listView.setAdapter(theAdapter);

        dataSaver = new DataSaver(getContext());
        dataClasses = dataSaver.load();
        if(dataClasses.size()!= 0){
            constraintLayout.setBackgroundResource(R.drawable.img_test);
            dataClass = dataClasses.get(0);

            textView1.setText(dataClass.getTitle());
            //textView1.setTextColor(Color.parseColor("#000000"));
            textView1.setTextSize((float) 25);

            textView2.setText(dataClass.getTimeClass().getStr());
            //textView2.setTextColor(Color.parseColor("#000000"));
            textView2.setTextSize((float) 25);
            Calendar calendar = Calendar.getInstance();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date d1 = df.parse(dataClass.getTimeClass().getStr()+":00");
                Date d2 = df.parse(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
                long date = d1.getTime() - d2.getTime();
                timer = new CountDownTimer(date, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        DecimalFormat dec = new DecimalFormat("##.##");
                        long day = millisUntilFinished / (1000 * 60 * 60 * 24);
                        long hour = (millisUntilFinished / (1000 * 60 * 60) - day * 24);
                        long min = ((millisUntilFinished / (60 * 1000)) - day * 24 * 60 - hour * 60);
                        long s = (millisUntilFinished/1000 - day*24*60*60 - hour*60*60 - min*60);
                        String str = day+"天 "+hour+"小时 "+min+"分钟 "+s+"秒";
                        textView3.setText(str);
                        textView3.setTextSize((float) 25);
                    }

                    @Override
                    public void onFinish() {
                        String str = "倒计时已结束";
                        textView3.setText(str);
                        textView3.setTextSize((float) 25);
                    }
                };
                timer.start();
            }catch (Exception e) {
            }
        }
        else{
            constraintLayout.setBackgroundResource(android.R.color.white);
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), CheckActivity.class);
                dataClasses = dataSaver.load();
                dataClass = dataClasses.get(i);
                intent.putExtra("dataclasses",dataClasses);
                intent.putExtra("position",i);
                startActivity(intent);

                //getActivity().finish();

            }
        });



//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}