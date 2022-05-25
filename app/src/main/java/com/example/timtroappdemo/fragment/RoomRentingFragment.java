package com.example.timtroappdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.Constant.GlobalFuntion;
import com.example.timtroappdemo.MyApplication;
import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.RoomRentingAdapter;
import com.example.timtroappdemo.model.Photo;
import com.example.timtroappdemo.model.RoomAvailable;
import com.example.timtroappdemo.model.RoomRenting;
import com.example.timtroappdemo.view.InforRoomAvailable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class RoomRentingFragment extends Fragment {

    private View mView;
    private RecyclerView rcvRoomRenting;
    private RoomRentingAdapter roomRentingAdapter;
    private List<RoomRenting> mListRoom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_roomrenting, container, false);

        initView();
        getListRoom();

        return mView;
    }

    private void initView(){
        rcvRoomRenting = mView.findViewById(R.id.rcv_roomrenting);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvRoomRenting.setLayoutManager(linearLayoutManager);

        mListRoom = new ArrayList<>();
        roomRentingAdapter = new RoomRentingAdapter(getContext(), mListRoom, new RoomRentingAdapter.IClickRoomRenting() {
            @Override
            public void clickRoomRenting(RoomRenting roomRenting) {
                goToRoomDetail(roomRenting);
            }
        });

        rcvRoomRenting.setAdapter(roomRentingAdapter);
    }

    private void goToRoomDetail(RoomRenting roomRenting) {
        ArrayList<Photo> listImageDisplay = (ArrayList<Photo>) roomRenting.getImages();
        Bundle bundle = new Bundle();
        bundle.putSerializable("imageRoom", listImageDisplay);
        bundle.putString("idRoom", roomRenting.getIdroom());
        bundle.putString("titleRoom", roomRenting.getTitle());
        bundle.putString("priceRoom", roomRenting.getPrice());
        bundle.putString("phoneRoom", roomRenting.getPhone());
        bundle.putString("addressRoom", roomRenting.getAddress());
        bundle.putString("descriptionRoom", roomRenting.getDescription());
        bundle.putString("statusRoom", roomRenting.getStatus());
        bundle.putString("avatarRoom", roomRenting.getAvatar());
        bundle.putString("idFragment", "renting");

        GlobalFuntion.startActivity(getActivity(), InforRoomAvailable.class, bundle);
    }

    private void getListRoom(){
        if (getActivity() == null) {
            return;
        }
        MyApplication.get(getActivity()).getRoomRenting().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RoomRenting roomRenting = snapshot.getValue(RoomRenting.class);
                if (roomRenting == null || mListRoom == null || roomRentingAdapter == null) {
                    return;
                }
                mListRoom.add(0, roomRenting);
                roomRentingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RoomRenting roomRenting = snapshot.getValue(RoomRenting.class);
                if (roomRenting == null || mListRoom == null || roomRentingAdapter == null) {
                    return;
                }
                for (int i = 0; i < mListRoom.size(); i++) {
                    if (roomRenting.getIdroom().equalsIgnoreCase(mListRoom.get(i).getIdroom())) {
                        mListRoom.set(i, roomRenting);
                        break;
                    }
                }
                roomRentingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                RoomAvailable roomRenting = snapshot.getValue(RoomAvailable.class);
                if (roomRenting == null || mListRoom == null || roomRentingAdapter == null) {
                    return;
                }
                for (RoomRenting roomDelete : mListRoom) {
                    if (roomRenting.getIdroom().equalsIgnoreCase(roomDelete.getIdroom())) {
                        mListRoom.remove(roomDelete);
                        break;
                    }
                }
                roomRentingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (roomRentingAdapter != null) {
            roomRentingAdapter.release();
        }
    }
}
