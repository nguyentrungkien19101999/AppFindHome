package com.example.timtroappdemo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.timtroappdemo.Constant.GlobalFuntion;
import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.MyViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mBottomNavigationView;
    ViewPager2 mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }
    private void initView(){
        mViewPager = findViewById(R.id.view_pager);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Find Home - Admin");
        }

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this);
        mViewPager.setAdapter(adapter);
    }

    private void initListener(){
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_nav_room_available){
                    mViewPager.setCurrentItem(0);
                } else if (id == R.id.menu_nav_room_renting){
                    mViewPager.setCurrentItem(1);
                } else if (id == R.id.menu_nav_room_add){
                    mViewPager.setCurrentItem(2);
                }
                return true;
            }
        });

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.menu_nav_room_available).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.menu_nav_room_renting).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.menu_nav_room_add).setChecked(true);
                        break;
                    default:
                        mBottomNavigationView.getMenu().findItem(R.id.menu_nav_room_available).setChecked(true);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout){
            showDialogLogoutApp();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showDialogExitApp();
    }

    private void showDialogExitApp() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn muốn thoát ứng dụng?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDialogLogoutApp() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn muốn đăng xuất?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GlobalFuntion.startActivity(getApplication(), LogInActivity.class);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}