package com.example.timtroappdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.R;
import com.example.timtroappdemo.model.RoomAvailable;
import com.example.timtroappdemo.model.RoomRenting;

import java.util.List;

public class RoomRentingAdapter extends RecyclerView.Adapter<RoomRentingAdapter.RoomRentingViewHolder>{

    private Context mContext;
    private List<RoomRenting> mListRoom;

    public RoomRentingAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<RoomRenting> list){
        this.mListRoom = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomRentingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rcv_roomrenting, parent, false);
        return new RoomRentingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomRentingViewHolder holder, int position) {
        RoomRenting roomRenting = mListRoom.get(position);
        if (roomRenting == null){
            return;
        }
        holder.tvTitle.setText(roomRenting.getRoomTitle());
        holder.tvPrice.setText(roomRenting.getRoomPrice());
        holder.tvAddress.setText(roomRenting.getRoomAddress());
        holder.tvDescription.setText(roomRenting.getRoomDescription());
        holder.imgAvatar.setImageResource(roomRenting.getRoomImage());

    }

    @Override
    public int getItemCount() {
        if (mListRoom != null){
            return mListRoom.size();
        }
        return 0;
    }

    public static class RoomRentingViewHolder extends RecyclerView.ViewHolder{

        ImageView imgAvatar;
        TextView tvTitle, tvPrice, tvAddress, tvDescription;

        public RoomRentingViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgAvatar = itemView.findViewById(R.id.img_room_avatar);

        }
    }
}