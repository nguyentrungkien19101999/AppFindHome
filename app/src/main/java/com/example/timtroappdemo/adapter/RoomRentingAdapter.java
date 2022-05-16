package com.example.timtroappdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.R;
import com.example.timtroappdemo.model.RoomRenting;

import java.util.List;

public class RoomRentingAdapter extends RecyclerView.Adapter<RoomRentingAdapter.RoomRentingViewHolder>{

    private List<RoomRenting> mListRoom;
    private IClickRoomRenting mClickRoomRenting;

    public RoomRentingAdapter(List<RoomRenting> mListRoom, IClickRoomRenting mClickRoomRenting) {
        this.mListRoom = mListRoom;
        this.mClickRoomRenting = mClickRoomRenting;
    }

    public interface IClickRoomRenting{
        void clickRoomRenting(RoomRenting roomRenting);
    }

    public void setData(List<RoomRenting> list){
        this.mListRoom = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomRentingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roomrenting, parent, false);
        return new RoomRentingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomRentingViewHolder holder, int position) {
        RoomRenting roomRenting = mListRoom.get(position);
        if (roomRenting == null){
            return;
        }
        holder.tvTitle.setText(roomRenting.getRoomTitle());
        holder.tvPrice.setText("Giá: "+roomRenting.getRoomPrice());
        holder.tvAddress.setText("Địa chỉ: "+roomRenting.getRoomAddress());
        holder.tvDescription.setText("Mô tả: "+roomRenting.getRoomDescription());
        holder.imgAvatar.setImageResource(roomRenting.getRoomImage());

        holder.layoutRoomRenting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickRoomRenting.clickRoomRenting(roomRenting);
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

    public static class RoomRentingViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgAvatar;
        private TextView tvTitle, tvPrice, tvAddress, tvDescription;
        private RelativeLayout layoutRoomRenting;
        public RoomRentingViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgAvatar = itemView.findViewById(R.id.img_room_avatar);
            layoutRoomRenting = itemView.findViewById(R.id.layout_room);
        }
    }
}
