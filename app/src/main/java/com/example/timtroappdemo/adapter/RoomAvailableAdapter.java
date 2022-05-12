package com.example.timtroappdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.R;
import com.example.timtroappdemo.model.RoomAvailable;

import java.util.ArrayList;
import java.util.List;

public class RoomAvailableAdapter extends RecyclerView.Adapter<RoomAvailableAdapter.RoomAvailableViewHolder>{

    private List<RoomAvailable> mListRoom;
    private IClickRoomAvailable iClickRoomAvailable;

    public interface IClickRoomAvailable{
        void clickRoomAvailable(RoomAvailable roomAvailable);
    }

    public RoomAvailableAdapter(List<RoomAvailable> list, IClickRoomAvailable iClickRoomAvailable) {
        this.mListRoom = list;
        this.iClickRoomAvailable = iClickRoomAvailable;
    }

    public void setData(List<RoomAvailable> list){
        this.mListRoom = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomAvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rcv_roomavailable, parent, false);
        return new RoomAvailableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAvailableViewHolder holder, int position) {
        RoomAvailable roomAvailable = mListRoom.get(position);
        if (roomAvailable == null){
            return;
        }
        holder.tvTitle.setText(roomAvailable.getRoomTitle());
        holder.tvPrice.setText(roomAvailable.getRoomPrice());
        holder.tvAddress.setText(roomAvailable.getRoomAddress());
        holder.tvDescription.setText(roomAvailable.getRoomDescription());
        holder.imgAvatar.setImageResource(roomAvailable.getRoomImage());

        holder.layoutRoomAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRoomAvailable.clickRoomAvailable(roomAvailable);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListRoom != null){
            return mListRoom.size();
        }
        return 0;
    }

    public static class RoomAvailableViewHolder extends RecyclerView.ViewHolder{

        ImageView imgAvatar;
        TextView tvTitle, tvPrice, tvAddress, tvDescription;
        RelativeLayout layoutRoomAvailable;

        public RoomAvailableViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgAvatar = itemView.findViewById(R.id.img_room_avatar);
            layoutRoomAvailable = itemView.findViewById(R.id.layout_room);
        }
    }
}
