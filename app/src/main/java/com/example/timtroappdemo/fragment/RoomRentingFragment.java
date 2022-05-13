package com.example.timtroappdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.RoomAvailableAdapter;
import com.example.timtroappdemo.adapter.RoomRentingAdapter;
import com.example.timtroappdemo.model.RoomAvailable;
import com.example.timtroappdemo.model.RoomRenting;

import java.util.ArrayList;
import java.util.List;

public class RoomRentingFragment extends Fragment {

    private RecyclerView rcvRoomRenting;
    private RoomRentingAdapter roomRentingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roomrenting, container, false);

        rcvRoomRenting = view.findViewById(R.id.rcv_roomrenting);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvRoomRenting.setLayoutManager(linearLayoutManager);

        roomRentingAdapter = new RoomRentingAdapter(getListRoom(), new RoomRentingAdapter.IClickRoomRenting() {
            @Override
            public void clickRoomRenting(RoomRenting roomRenting) {
                Toast.makeText(getContext(),"Is Click to "+roomRenting.getRoomTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        roomRentingAdapter.setData(getListRoom());
        rcvRoomRenting.setAdapter(roomRentingAdapter);

        return view;
    }

    private List<RoomRenting> getListRoom() {
        List<RoomRenting> list = new ArrayList<>();
        list.add(new RoomRenting(R.drawable.room1, "Xóm trọ cô Thủy", "2000000 VNĐ", "Xóm Nước 2, xã Quyết Thắng, TP Thái Nguyên, Thai Nguyen", "Phòng 20m2, điều hòa điện nước đầy đủ"));
        list.add(new RoomRenting(R.drawable.room2, "Xóm trọ cô Thu", "1500000 VNĐ", "Xóm Nước 2, xã Quyết Thắng, TP Thái Nguyên", "Phòng 20m2, điều hòa điện nước đầy đủ"));
        list.add(new RoomRenting(R.drawable.room1, "Xóm trọ cô Thanh", "1000000 VNĐ", "Xóm Nước 2, xã Quyết Thắng, TP Thái Nguyên, Thai Nguyen", "Phòng 20m2, điều hòa điện nước đầy đủ, giuong tu quan ao, may giat"));
        list.add(new RoomRenting(R.drawable.room2, "Xóm trọ chú Hùng", "500000 VNĐ", "Xóm Nước 2, xã Quyết Thắng, TP Thái Nguyên", "Phòng 20m2, điều hòa điện nước đầy đủ"));

        return list;
    }
}
