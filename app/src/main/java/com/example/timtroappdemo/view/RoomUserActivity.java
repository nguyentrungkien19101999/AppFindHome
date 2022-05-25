package com.example.timtroappdemo.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.Constant.GlobalFuntion;
import com.example.timtroappdemo.MyApplication;
import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.RoomAvailableAdapter;
import com.example.timtroappdemo.model.Photo;
import com.example.timtroappdemo.model.RoomAvailable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RoomUserActivity extends AppCompatActivity {

    private RecyclerView rcvRoomAvailable;
    private RoomAvailableAdapter roomAvailableAdapter;
    private List<RoomAvailable> mListRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_user);

        initView();

        getListRoom();
    }

    private void initView() {
        if (this == null) {
            return;
        }
        rcvRoomAvailable = findViewById(R.id.rcv_roomavailable);

        getSupportActionBar().setTitle("Find Home");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvRoomAvailable.setLayoutManager(linearLayoutManager);

        mListRoom = new ArrayList<>();
        roomAvailableAdapter = new RoomAvailableAdapter(this, mListRoom, new RoomAvailableAdapter.IClickRoomAvailable() {
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
        bundle.putString("idFragment", "roomuser");

        GlobalFuntion.startActivity(this, InforRoomAvailable.class, bundle);
    }

    private void getListRoom() {
        if (this == null) {
            return;
        }

        MyApplication.get(this).getRoomAvailable().addChildEventListener(new ChildEventListener() {
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
                Toast.makeText(getApplication(), R.string.msg_error_not_connect_to_internet, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout){
            showDialogLogoutApp();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showDialogExitApp();
    }

    private void showDialogExitApp() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn muốn thoát ứng dụng?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDialogLogoutApp() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn muốn đăng xuất?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GlobalFuntion.startActivity(getApplication(), LogInActivity.class);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
