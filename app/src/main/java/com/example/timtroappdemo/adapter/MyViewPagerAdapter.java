package com.example.timtroappdemo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.timtroappdemo.fragment.RoomAddFragment;
import com.example.timtroappdemo.fragment.RoomAvailableFragment;
import com.example.timtroappdemo.fragment.RoomRentingFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RoomAvailableFragment();
            case 1:
                return new RoomRentingFragment();
            case 2:
                return new RoomAddFragment();
            default:
                return new RoomAvailableFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
