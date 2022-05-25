package com.example.timtroappdemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.R;
import com.example.timtroappdemo.model.RoomAvailable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAvailableAdapter extends RecyclerView.Adapter<RoomAvailableAdapter.RoomAvailableViewHolder> {

    private Context mContext;
    private List<RoomAvailable> mListRoom;
    private IClickRoomAvailable iClickRoomAvailable;

    public interface IClickRoomAvailable {
        void clickRoomAvailable(RoomAvailable roomAvailable);
    }

    public RoomAvailableAdapter(Context mContext, List<RoomAvailable> mListRoom, IClickRoomAvailable iClickRoomAvailable) {
        this.mContext = mContext;
        this.mListRoom = mListRoom;
        this.iClickRoomAvailable = iClickRoomAvailable;
    }

    public void release() {
        if (mContext != null) {
            mContext = null;
        }
    }

    @NonNull
    @Override
    public RoomAvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roomavailable, parent, false);
        return new RoomAvailableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAvailableViewHolder holder, int position) {
        RoomAvailable roomAvailable = mListRoom.get(position);
        if (roomAvailable == null) {
            return;
        }

        holder.tvTitle.setText(roomAvailable.getTitle());
        holder.tvPrice.setText("Giá: " + roomAvailable.getPrice()+" VNĐ/Tháng");
        holder.tvAddress.setText("Địa chỉ: " + roomAvailable.getAddress());
        holder.tvPhone.setText("Điện thoại: " + roomAvailable.getPhone());
        holder.tvDescription.setText("Mô tả: " + roomAvailable.getDescription());
        Picasso.with(mContext).load(roomAvailable.getAvatar()).into(holder.imgAvatar);

        holder.layoutRoomAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRoomAvailable.clickRoomAvailable(roomAvailable);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListRoom != null) {
            return mListRoom.size();
        }
        return 0;
    }

    public static class RoomAvailableViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgAvatar;
        private final TextView tvTitle;
        private final TextView tvPrice;
        private final TextView tvAddress;
        private final TextView tvDescription;
        private final TextView tvPhone;
        private final RelativeLayout layoutRoomAvailable;

        public RoomAvailableViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgAvatar = itemView.findViewById(R.id.img_room_avatar);
            layoutRoomAvailable = itemView.findViewById(R.id.layout_room);
            tvPhone = itemView.findViewById(R.id.tv_phone);
        }
    }
}
