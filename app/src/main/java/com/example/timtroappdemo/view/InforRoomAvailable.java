package com.example.timtroappdemo.view;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.PhotoAdapter;
import com.example.timtroappdemo.model.Photo;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class InforRoomAvailable extends AppCompatActivity {

    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info_roomavailable);

        mViewPager2 = findViewById(R.id.view_pager2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator3);

        PhotoAdapter photoAdapter = new PhotoAdapter(this, getListPhoto());
        mViewPager2.setAdapter(photoAdapter);
        mCircleIndicator3.setViewPager(mViewPager2);
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.room1));
        list.add(new Photo(R.drawable.room1));
        list.add(new Photo(R.drawable.room1));
        list.add(new Photo(R.drawable.room1));

        return list;
    }
}
