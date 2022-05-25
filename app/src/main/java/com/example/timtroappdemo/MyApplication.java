package com.example.timtroappdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }

    public DatabaseReference getRoomAvailable() {
        return FirebaseDatabase.getInstance().getReference("/available");
    }

    public DatabaseReference getRoomRenting() {
        return FirebaseDatabase.getInstance().getReference("/renting");
    }
}
