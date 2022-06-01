package com.example.timtroappdemo.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timtroappdemo.Constant.GlobalFuntion;
import com.example.timtroappdemo.MyApplication;
import com.example.timtroappdemo.R;
import com.example.timtroappdemo.adapter.PhotoAddAdapter;
import com.example.timtroappdemo.model.Photo;
import com.example.timtroappdemo.model.RoomAvailable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class RoomAddFragment extends Fragment {
    private Button btnSelectPhoto, btnSubmit;
    private RecyclerView rcvPhoto;
    private PhotoAddAdapter mPhotoAddAdapter;
    private TextInputEditText edtTitle, edtPrice, edtAddress, edtPhone, edtDescription;
    private View mView;
    private ImageView imgAvatar;

    private String imageFileAvatar;
    private String imageAvatar;

    private List<Uri> uriListImageUpload;
    private List<Photo> uriListImageDownload;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_roomadd, container, false);

        initView();

        return mView;
    }

    private void initView() {
        btnSelectPhoto = mView.findViewById(R.id.btn_selectPhoto);
        rcvPhoto = mView.findViewById(R.id.rcv_photo);
        edtTitle = mView.findViewById(R.id.textInput_title);
        edtPrice = mView.findViewById(R.id.textInput_price);
        edtAddress = mView.findViewById(R.id.textInput_address);
        edtPhone = mView.findViewById(R.id.textInput_phone);
        edtDescription = mView.findViewById(R.id.textInput_desciption);
        btnSubmit = mView.findViewById(R.id.btn_submid);
        imgAvatar = mView.findViewById(R.id.img_add_avatar);

        uriListImageUpload = new ArrayList<>();
        uriListImageDownload = new ArrayList<>();

        mPhotoAddAdapter = new PhotoAddAdapter(getContext());

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(mPhotoAddAdapter);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotoGallery();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long categoryId = GlobalFuntion.getId();
                uploadImage(categoryId);
                uploadListImage(categoryId);
            }
        });
    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectPhotoAvatar();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void selectPhotoGallery() {
        uriListImageUpload.clear();
        TedBottomPicker.with(getActivity())
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Xong")
                .setEmptySelectionText("Không chọn")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        if (uriList != null && !uriList.isEmpty()) {
                            mPhotoAddAdapter.setData(uriList);
                            for (int i = 0; i < uriList.size(); i++) {
                                uriListImageUpload.add(uriList.get(i));
                            }
                            btnSelectPhoto.setText("Thay đổi ảnh mô tả");
                        }
                    }
                });
    }

    private void selectPhotoAvatar() {
        TedBottomPicker.with(getActivity())
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        imgAvatar.setImageURI(uri);
                        imageFileAvatar = uri.toString().trim();
                    }
                });
    }

    private void uploadImage(long categoryId) {
        if (imageFileAvatar != null) {

            // Defining the child of storageReference
            StorageReference ref = storageReference.child(categoryId + "/" + "imageRoom0");

            // adding listeners on upload
            // or failure of image
            ref.putFile(Uri.parse(imageFileAvatar)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageAvatar = String.valueOf(uri);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void uploadListImage(long categoryId) {
        if (uriListImageUpload != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Đang tải ảnh lên...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // Defining the child of storageReference
            for (int i = 0; i < uriListImageUpload.size(); i++) {
                Uri IndividualImage = uriListImageUpload.get(i);
                StorageReference ref = storageReference.child(categoryId + "/" + "imageRoom" + (i+1));

                int ii = i;
                // adding listeners on upload
                // or failure of image
                ref.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Photo photo = new Photo(uri.toString());
                                uriListImageDownload.add(photo);
                                Log.d("+++", "+++" + uriListImageDownload);

                            }
                        });
                        // Image uploaded successfully
                        // Dismiss dialog

                        if (ii == (uriListImageUpload.size() - 1)) {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    addRoom(categoryId);
                                }
                            }, 3000);
                        }
                    }
                });
            }
        }
    }

    private void addRoom(long categoryId) {
        String strTitle = edtTitle.getText().toString().trim();
        String strPrice = edtPrice.getText().toString().trim();
        String strAddress = edtAddress.getText().toString().trim();
        String strPhone = edtPhone.getText().toString().trim();
        String strDescription = edtDescription.getText().toString().trim();

        if (strTitle.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập Tiêu đề!", Toast.LENGTH_SHORT).show();
        } else if (strPrice.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập Giá phòng!", Toast.LENGTH_SHORT).show();
        } else if (strAddress.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập Địa chỉ!", Toast.LENGTH_SHORT).show();
        } else if (strPhone.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập Số điện thoại!", Toast.LENGTH_SHORT).show();
        } else if (strDescription.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập Mô tả!", Toast.LENGTH_SHORT).show();
        } else {

            RoomAvailable roomAvailable = new RoomAvailable();
            roomAvailable.setTitle(strTitle);
            roomAvailable.setPrice(strPrice);
            roomAvailable.setAddress(strAddress);
            roomAvailable.setPhone(strPhone);
            roomAvailable.setDescription(strDescription);
            roomAvailable.setStatus("0");
            roomAvailable.setAvatar(imageAvatar);
            roomAvailable.setIdroom(String.valueOf(categoryId));
            roomAvailable.setImages(uriListImageDownload);

            MyApplication.get(getActivity()).getRoomAvailable().child(String.valueOf(categoryId)).setValue(roomAvailable, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(getActivity(),
                            "Thêm phòng thành công!", Toast.LENGTH_LONG).show();
                    GlobalFuntion.hideSoftKeyboard(getActivity());
                    refresh();
                }
            });
        }
    }

    private void refresh() {
        edtTitle.setText(null);
        edtPrice.setText(null);
        edtAddress.setText(null);
        edtPhone.setText(null);
        edtDescription.setText(null);
        imgAvatar.setImageResource(R.drawable.image_add_avatar);
        rcvPhoto.setAdapter(null);
        btnSelectPhoto.setText("Thêm ảnh mô tả phòng");
        uriListImageUpload.clear();
        uriListImageDownload.clear();

    }
}
