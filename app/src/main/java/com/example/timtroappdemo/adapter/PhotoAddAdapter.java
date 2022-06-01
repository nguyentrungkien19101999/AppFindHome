package com.example.timtroappdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.R;

import java.io.IOException;
import java.util.BitSet;
import java.util.List;

public class PhotoAddAdapter extends RecyclerView.Adapter<PhotoAddAdapter.PhotoAddViewHolder> {

    private Context mContext;
    private List<Uri> mListPhoto;

    public PhotoAddAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Uri> list){
        this.mListPhoto = list;
        notifyDataSetChanged();
    }

    public void refresh(){
        mListPhoto.clear();
    }

    @NonNull
    @Override
    public PhotoAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoAddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAddViewHolder holder, int position) {
        Uri uri = mListPhoto.get(position);
        if (uri == null){
            return;
        }

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            if (bitmap != null) {
                holder.imgPhoto.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mListPhoto != null){
            return mListPhoto.size();
        }
        return 0;
    }

    public class PhotoAddViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgPhoto;

        public PhotoAddViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }
}
