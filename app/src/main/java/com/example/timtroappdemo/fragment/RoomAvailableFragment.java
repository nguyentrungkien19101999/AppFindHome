package com.example.timtroappdemo.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
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

import com.example.timtroappdemo.Constant.GlobalFuntion;
import com.example.timtroappdemo.MyApplication;
import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.RoomAvailableAdapter;
import com.example.timtroappdemo.model.Photo;
import com.example.timtroappdemo.model.RoomAvailable;
import com.example.timtroappdemo.view.InforRoomAvailable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RoomAvailableFragment extends Fragment {

    private View mView;
    private RecyclerView rcvRoomAvailable;
    private RoomAvailableAdapter roomAvailableAdapter;
    private List<RoomAvailable> mListRoom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_roomavailable, container, false);

        initView();
        getListRoom();

        return mView;
    }

    private void initView() {
        if (mView == null) {
            return;
        }
        rcvRoomAvailable = mView.findViewById(R.id.rcv_roomavailable);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcvRoomAvailable.setLayoutManager(linearLayoutManager);

        mListRoom = new ArrayList<>();
        roomAvailableAdapter = new RoomAvailableAdapter(getActivity(), mListRoom, new RoomAvailableAdapter.IClickRoomAvailable() {
            @Override
            public void clickRoomAvailable(RoomAvailable roomAvailable) {
                goToRoomDetail(roomAvailable);
            }
        });

        rcvRoomAvailable.setAdapter(roomAvailableAdapter);
    }

    private void goToRoomDetail(RoomAvailable roomAvailable) {
        ArrayList<Photo> listImageDisplay = (ArrayList<Photo>) roomAvailable.getImages();
        Bundle bundle = new Bundle();
        bundle.putSerializable("imageRoom", listImageDisplay);
        bundle.putString("idRoom", roomAvailable.getIdroom());
        bundle.putString("titleRoom", roomAvailable.getTitle());
        bundle.putString("priceRoom", roomAvailable.getPrice());
        bundle.putString("phoneRoom", roomAvailable.getPhone());
        bundle.putString("addressRoom", roomAvailable.getAddress());
        bundle.putString("descriptionRoom", roomAvailable.getDescription());
        bundle.putString("statusRoom", roomAvailable.getStatus());
        bundle.putString("avatarRoom", roomAvailable.getAvatar());
        bundle.putString("idFragment", "available");

        GlobalFuntion.startActivity(getActivity(), InforRoomAvailable.class, bundle);
    }

    private void getListRoom() {
        if (getActivity() == null) {
            return;
        }

        MyApplication.get(getActivity()).getRoomAvailable().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RoomAvailable roomAvailable = snapshot.getValue(RoomAvailable.class);
                if (roomAvailable == null || mListRoom == null || roomAvailableAdapter == null) {
                    return;
                }
                mListRoom.add(0, roomAvailable);
                roomAvailableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                RoomAvailable roomAvailable = snapshot.getValue(RoomAvailable.class);
                if (roomAvailable == null || mListRoom == null || roomAvailableAdapter == null) {
                    return;
                }
                for (int i = 0; i < mListRoom.size(); i++) {
                    if (roomAvailable.getIdroom().equalsIgnoreCase(mListRoom.get(i).getIdroom())) {
                        mListRoom.set(i, roomAvailable);
                        break;
                    }
                }
                roomAvailableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                RoomAvailable roomAvailable = snapshot.getValue(RoomAvailable.class);
                if (roomAvailable == null || mListRoom == null || roomAvailableAdapter == null) {
                    return;
                }
                for (RoomAvailable roomDelete : mListRoom) {
                    if (roomAvailable.getIdroom().equalsIgnoreCase(roomDelete.getIdroom())) {
                        mListRoom.remove(roomDelete);
                        break;
                    }
                }
                roomAvailableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), R.string.msg_error_not_connect_to_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (roomAvailableAdapter != null) {
            roomAvailableAdapter.release();
        }
    }


}
