package com.example.timtroappdemo.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.timtroappdemo.Constant.GlobalFuntion;
import com.example.timtroappdemo.MyApplication;
import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.PhotoAdapter;
import com.example.timtroappdemo.model.Photo;
import com.example.timtroappdemo.model.RoomAvailable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class InforRoomAvailable extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private PhotoAdapter photoAdapter;
    private ImageView imgEdit, imgDelete, imgCall;
    private ArrayList<Photo> imageRoom;
    private String strPrice, strAddress, strPhone, strDescription, strStatus, strTitle, strId, strAvatar, strIdFragment;
    private TextView tvId, tvTitle, tvPrice, tvAddress, tvPhone, tvDescription, tvStatus;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info_roomavailable);

        initData(getIntent().getExtras());
    }

    private void initData(Bundle bundle) {
        mViewPager2 = findViewById(R.id.view_pager2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator3);
        tvTitle = findViewById(R.id.tv_title);
        tvPrice = findViewById(R.id.tv_price);
        tvAddress = findViewById(R.id.tv_address);
        tvPhone = findViewById(R.id.tv_phone);
        tvDescription = findViewById(R.id.tv_description);
        tvStatus = findViewById(R.id.tv_status);
        tvId = findViewById(R.id.tv_id);
        imgDelete = findViewById(R.id.img_delete);
        imgEdit = findViewById(R.id.img_edit);
        imgCall = findViewById(R.id.img_call);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        imageRoom = (ArrayList<Photo>) bundle.getSerializable("imageRoom");

        strIdFragment = bundle.getString("idFragment");
        if (strIdFragment.equalsIgnoreCase("available") || strIdFragment.equalsIgnoreCase("renting")) {
            imgEdit.setVisibility(View.VISIBLE);
            imgDelete.setVisibility(View.VISIBLE);
            imgCall.setVisibility(View.GONE);
        }

        strId = bundle.getString("idRoom");
        tvId.setText("#" + strId);

        strTitle = bundle.getString("titleRoom");
        tvTitle.setText(strTitle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(strTitle);
        }

        strPrice = bundle.getString("priceRoom");
        tvPrice.setText("Giá phòng: " + strPrice + " VNĐ/Tháng");

        strAddress = bundle.getString("addressRoom");
        tvAddress.setText("Địa chỉ: " + strAddress);

        strPhone = bundle.getString("phoneRoom");
        tvPhone.setText("Điện thoại: " + strPhone);

        strDescription = bundle.getString("descriptionRoom");
        tvDescription.setText("Mô tả: " + strDescription);

        strStatus = bundle.getString("statusRoom");
        if (strStatus.equalsIgnoreCase("0")) {
            tvStatus.setText("Tình trạng: Phòng đang trống");
        } else tvStatus.setText("Tình trạng: Đã cho thuê");

        strAvatar = bundle.getString("avatarRoom");

        photoAdapter = new PhotoAdapter(this, imageRoom);
        mViewPager2.setAdapter(photoAdapter);

        mCircleIndicator3.setViewPager(mViewPager2);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeleteRoom();
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEditRoom();
            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCall();
            }
        });
    }

    private void onClickDeleteRoom() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa!")
                .setMessage("Bạn có muốn xóa phòng?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int ii = 0; ii <= imageRoom.size(); ii++) {
                            // Defining the child of storageReference
                            StorageReference ref = storageReference.child(strId + "/imageRoom" + ii);
                            ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            });
                        }

                        if (strIdFragment.equalsIgnoreCase("available")) {
                            MyApplication.get(getApplication()).getRoomAvailable().child(strId).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    GlobalFuntion.startActivity(getApplication(), MainActivity.class);
                                    Toast.makeText(getApplicationContext(),
                                            "Xóa phòng thành công!", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            MyApplication.get(getApplication()).getRoomRenting().child(strId).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    GlobalFuntion.startActivity(getApplication(), MainActivity.class);
                                    Toast.makeText(getApplicationContext(),
                                            "Xóa phòng thành công!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void onClickEditRoom() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_edit_room);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextInputEditText edtTitle = dialog.findViewById(R.id.textInput_title);
        TextInputEditText edtPrice = dialog.findViewById(R.id.textInput_price);
        TextInputEditText edtAddress = dialog.findViewById(R.id.textInput_address);
        TextInputEditText edtPhone = dialog.findViewById(R.id.textInput_phone);
        TextInputEditText edtDescription = dialog.findViewById(R.id.textInput_desciption);
        TextView tvCancel = dialog.findViewById(R.id.tv_dialog_cancel);
        TextView tvSubmit = dialog.findViewById(R.id.tv_dialog_add);
        RadioButton cbRoomAvailable = dialog.findViewById(R.id.rb_Status1);
        RadioButton cbRoomRenting = dialog.findViewById(R.id.rb_Status2);

        if (strStatus.equalsIgnoreCase("0")) {
            cbRoomAvailable.setChecked(true);
        } else cbRoomRenting.setChecked(true);

        edtTitle.setText(strTitle);
        edtPrice.setText(strPrice);
        edtAddress.setText(strAddress);
        edtPhone.setText(strPhone);
        edtDescription.setText(strDescription);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strTitle = edtTitle.getText().toString().trim();
                strPrice = edtPrice.getText().toString().trim();
                strAddress = edtAddress.getText().toString().trim();
                strPhone = edtPhone.getText().toString().trim();
                strDescription = edtDescription.getText().toString().trim();

                if (strTitle.isEmpty()) {
                    Toast.makeText(getApplication(), "Vui lòng nhập Tiêu đề!", Toast.LENGTH_SHORT).show();
                } else if (strPrice.isEmpty()) {
                    Toast.makeText(getApplication(), "Vui lòng nhập Giá phòng!", Toast.LENGTH_SHORT).show();
                } else if (strAddress.isEmpty()) {
                    Toast.makeText(getApplication(), "Vui lòng nhập Địa chỉ!", Toast.LENGTH_SHORT).show();
                } else if (strPhone.isEmpty()) {
                    Toast.makeText(getApplication(), "Vui lòng nhập Số điện thoại!", Toast.LENGTH_SHORT).show();
                } else if (strDescription.isEmpty()) {
                    Toast.makeText(getApplication(), "Vui lòng nhập Mô tả!", Toast.LENGTH_SHORT).show();
                } else {
                    RoomAvailable roomAvailable = new RoomAvailable();
                    roomAvailable.setTitle(strTitle);
                    roomAvailable.setPrice(strPrice);
                    roomAvailable.setAddress(strAddress);
                    roomAvailable.setPhone(strPhone);
                    roomAvailable.setDescription(strDescription);
                    roomAvailable.setIdroom(strId);
                    roomAvailable.setAvatar(strAvatar);
                    roomAvailable.setImages(imageRoom);

                    if (cbRoomAvailable.isChecked() && strIdFragment.equalsIgnoreCase("available")) {
                        roomAvailable.setStatus("0");
                        MyApplication.get(getApplication()).getRoomAvailable().child(strId).setValue(roomAvailable, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getApplication(),
                                        "Sửa thông tin Phòng thành công!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                                tvTitle.setText(strTitle);
                                getSupportActionBar().setTitle(strTitle);
                                tvPrice.setText("Giá phòng: " + strPrice + " VNĐ/Tháng");
                                tvAddress.setText("Địa chỉ: " + strAddress);
                                tvPhone.setText("Điện thoại: " + strPhone);
                                tvDescription.setText("Mô tả: " + strDescription);
                                tvStatus.setText("Tình trạng: Phòng đang trống");
                            }
                        });

                    } else if (strIdFragment.equalsIgnoreCase("available") && cbRoomRenting.isChecked()) {

                        roomAvailable.setIdroom(strId);
                        roomAvailable.setStatus("1");

                        MyApplication.get(getApplication()).getRoomAvailable().child(strId).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            }
                        });

                        MyApplication.get(getApplication()).getRoomRenting().child(String.valueOf(strId)).setValue(roomAvailable, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getApplication(),
                                        "Sửa thông tin Phòng thành công!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                GlobalFuntion.startActivity(getApplication(), MainActivity.class);
                            }
                        });
                    } else if (strIdFragment.equalsIgnoreCase("renting") && cbRoomRenting.isChecked()) {
                        roomAvailable.setStatus("1");
                        MyApplication.get(getApplication()).getRoomRenting().child(strId).setValue(roomAvailable, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getApplication(),
                                        "Sửa thông tin Phòng thành công!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                                tvTitle.setText(strTitle);
                                getSupportActionBar().setTitle(strTitle);
                                tvPrice.setText("Giá phòng: " + strPrice + " VNĐ/Tháng");
                                tvAddress.setText("Địa chỉ: " + strAddress);
                                tvPhone.setText("Điện thoại: " + strPhone);
                                tvDescription.setText("Mô tả: " + strDescription);
                                tvStatus.setText("Tình trạng: Đã cho thuê");
                            }
                        });
                    } else if (strIdFragment.equalsIgnoreCase("renting") && cbRoomAvailable.isChecked()) {

                        roomAvailable.setIdroom(String.valueOf(strId));
                        roomAvailable.setStatus("0");

                        MyApplication.get(getApplication()).getRoomRenting().child(strId).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        });

                        MyApplication.get(getApplication()).getRoomAvailable().child(String.valueOf(strId)).setValue(roomAvailable, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getApplication(),
                                        "Sửa thông tin Phòng thành công!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                GlobalFuntion.startActivity(getApplication(), MainActivity.class);
                            }
                        });
                    }
                }
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void onClickCall() {
        String numberPhone = "tel:" + tvPhone.getText().toString().trim();
        Log.d("+++", "+++" + Uri.parse(numberPhone));

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse(numberPhone));
        startActivity(callIntent);

    }

}
