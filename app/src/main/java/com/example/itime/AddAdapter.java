package com.example.itime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class AddAdapter extends ArrayAdapter<AddClass> {

    private Context context;

    public AddAdapter(Context context, int resource, List<AddClass> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final AddClass addClass = getItem(position);

        View oneTeacherView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        ImageView imageView = (ImageView) oneTeacherView.findViewById(R.id.add_imageView);
        TextView textView1 = (TextView) oneTeacherView.findViewById(R.id.add_textView);
        TextView textView2 = (TextView) oneTeacherView.findViewById(R.id.text_view_img_text);

        imageView.setImageResource(addClass.getImageId());
        if(addClass.getImageId()!=android.R.drawable.ic_menu_recent_history&&addClass.getImageId()!=android.R.drawable.ic_menu_gallery&&addClass.getImageId()!=android.R.drawable.stat_notify_more)
        //Bitmap bitmap = BitmapFactory. decodeResource (getResource(),addClass.getImageId());
        Glide.with(context).load(R.drawable.img_test)
                .apply(bitmapTransform(new BlurTransformation(10, 1)).skipMemoryCache(false)).into(imageView);

        textView1.setText(addClass.getTitle());
        textView2.setText(addClass.getCountdown());
        //textView2.setTextColor(Color.parseColor("#FFFFFF"));
        textView2.setTextSize((float) 16);

//        oneTeacherView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getContext(), TeacherDetailActivity.class);
//
//                intent.putExtra("teacher_image", teacher.getImageId());
//                intent.putExtra("teacher_desc", teacher.getDesc());
//
//                getContext().startActivity(intent);
//            }
//        });

        return oneTeacherView;
    }
}
