package com.example.timtroappdemo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timtroappdemo.R;
import com.example.timtroappdemo.model.Photo;
import com.squareup.picasso.Picasso;

public class PhotoFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_photo, container, false);

        Bundle bundle = getArguments();
        Photo photo = (Photo) bundle.get("object_photo");

        ImageView imgPhoto = mView.findViewById(R.id.img_photo);

        Picasso.with(getContext()).load(photo.getImg_url()).into(imgPhoto);

        return mView;
    }
}
