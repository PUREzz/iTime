package com.example.itime;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CountTime extends CountDownTimer {
    private TextView textView;
    private long millisUntilFinished;

    public CountTime(long millisInFuture, long countDownInterval, final TextView textView) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔,要显示的按钮
        this.textView = textView;

    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        this.millisUntilFinished = millisUntilFinished;
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        //button.setBackgroundResource(R.drawable.send_code_wait);
        textView.setTextSize((float) 25);
        DecimalFormat dec = new DecimalFormat("##.##");
        long day = millisUntilFinished / (1000 * 60 * 60 * 24);
        long hour = (millisUntilFinished / (1000 * 60 * 60) - day * 24);
        long min = ((millisUntilFinished / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (millisUntilFinished/1000 - day*24*60*60 - hour*60*60 - min*60);
        String str = day+"天 "+hour+"小时 "+min+"分钟 "+s+"秒";
        textView.setText(str);
    }

    @Override
    public void onFinish() {//计时完毕时触发
        String str = "倒计时已结束";
        textView.setText(str);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize((float) 25);
        // button.setBackgroundResource(R.drawable.send_code);
    }

}
