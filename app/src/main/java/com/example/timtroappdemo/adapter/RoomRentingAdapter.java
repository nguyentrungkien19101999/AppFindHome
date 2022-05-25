package com.example.timtroappdemo.adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomRentingAdapter extends RecyclerView.Adapter<RoomRentingAdapter.RoomRentingViewHolder>{

    private Context mContext;
    private List<RoomRenting> mListRoom;
    private IClickRoomRenting mClickRoomRenting;

    public RoomRentingAdapter(Context mContext, List<RoomRenting> mListRoom, IClickRoomRenting mClickRoomRenting) {
        this.mContext = mContext;
        this.mListRoom = mListRoom;
        this.mClickRoomRenting = mClickRoomRenting;
    }

    public interface IClickRoomRenting{
        void clickRoomRenting(RoomRenting roomRenting);
    }

    public void release() {
        if (mContext != null) {
            mContext = null;
        }
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
        holder.tvTitle.setText(roomRenting.getTitle());
        holder.tvPrice.setText("Giá: " + roomRenting.getPrice()+" VNĐ/Tháng");
        holder.tvAddress.setText("Địa chỉ: " + roomRenting.getAddress());
        holder.tvPhone.setText("Điện thoại: " + roomRenting.getPhone());
        holder.tvDescription.setText("Mô tả: " + roomRenting.getDescription());
        Picasso.with(mContext).load(roomRenting.getAvatar()).into(holder.imgAvatar);

        holder.layoutroomRenting.setOnClickListener(new View.OnClickListener() {
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

        private final ImageView imgAvatar;
        private final TextView tvTitle;
        private final TextView tvPrice;
        private final TextView tvAddress;
        private final TextView tvDescription;
        private final TextView tvPhone;
        private final RelativeLayout layoutroomRenting;

        public RoomRentingViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgAvatar = itemView.findViewById(R.id.img_room_avatar);
            layoutroomRenting = itemView.findViewById(R.id.layout_room);
            tvPhone = itemView.findViewById(R.id.tv_phone);
        }
    }
}
