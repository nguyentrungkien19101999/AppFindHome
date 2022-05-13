package com.example.timtroappdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.RoomAvailableAdapter;
import com.example.timtroappdemo.model.RoomAvailable;
import com.example.timtroappdemo.view.InforRoomAvailable;

import java.util.ArrayList;
import java.util.List;

public class RoomAvailableFragment extends Fragment {

    private RecyclerView rcvRoomAvailable;
    private RoomAvailableAdapter roomAvailableAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roomavailable, container, false);

        rcvRoomAvailable = view.findViewById(R.id.rcv_roomavailable);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvRoomAvailable.setLayoutManager(linearLayoutManager);

        roomAvailableAdapter = new RoomAvailableAdapter(getListRoom(), new RoomAvailableAdapter.IClickRoomAvailable() {
            @Override
            public void clickRoomAvailable(RoomAvailable roomAvailable) {
                Intent intent = new Intent(getContext(), InforRoomAvailable.class);
                startActivity(intent);
//                Toast.makeText(getContext(),"Is Click Item Room " + roomAvailable.getRoomTitle(),Toast.LENGTH_SHORT).show();
            }
        });

        roomAvailableAdapter.setData(getListRoom());
        rcvRoomAvailable.setAdapter(roomAvailableAdapter);

        return view;
    }

    private List<RoomAvailable> getListRoom() {
        List<RoomAvailable> list = new ArrayList<>();
        list.add(new RoomAvailable(R.drawable.room1, 01,"Xóm trọ cô Thủy", "2000000 VNĐ", "Xóm Nước 2, xã Quyết Thắng, TP Thái Nguyên, Thai Nguyen", "0358387888","Phòng 20m2, điều hòa điện nước đầy đủ"));
        list.add(new RoomAvailable(R.drawable.room2, 02,"Xóm trọ cô Thu", "1500000 VNĐ", "Xóm Nước 2, xã Quyết Thắng, TP Thái Nguyên", "0165878785","Phòng 20m2, điều hòa điện nước đầy đủ"));
        list.add(new RoomAvailable(R.drawable.room1, 03,"Xóm trọ cô Thanh", "1000000 VNĐ", "Xóm Nước 2, xã Quyết Thắng, TP Thái Nguyên, Thai Nguyen", "0358987457","Phòng 20m2, điều hòa điện nước đầy đủ, giuong tu quan ao, may giat"));
        list.add(new RoomAvailable(R.drawable.room2, 04,"Xóm trọ chú Hùng", "500000 VNĐ", "Xóm Nước 2, xã Quyết Thắng, TP Thái Nguyên", "098574745","Phòng 20m2, điều hòa điện nước đầy đủ"));

        return list;
    }
}
