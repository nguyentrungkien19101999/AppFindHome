package com.example.timtroappdemo.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timtroappdemo.Constant.GlobalFuntion;
import com.example.timtroappdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private TextInputEditText edtUsername;
    private TextInputEditText edtPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private TextView tvRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        edtUsername = findViewById(R.id.text_input_username);
        edtPassword = findViewById(R.id.text_input_password);
        btnLogin = findViewById(R.id.btn_Login);
        tvRegister = findViewById(R.id.tv_Register);

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalFuntion.startActivity(getApplication(), RegisterActivity.class);
                finish();
            }
        });
    }

    private void onClickLogin() {
        String strUserName = edtUsername.getText().toString().trim();
        String strPassWord = edtPassword.getText().toString().trim();

        if (strUserName.isEmpty() || strPassWord.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
        } else {
            if (strUserName.equalsIgnoreCase("admin") && strPassWord.equalsIgnoreCase("admin")) {
                GlobalFuntion.startActivity(this, MainActivity.class);
                finish();
            } else {
                caseUserLogin(strUserName, strPassWord);
            }
        }
    }

    private void caseUserLogin(String email, String passWord) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.setMessage("Đang đăng nhập...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            GlobalFuntion.startActivity(getApplication(), RoomUserActivity.class);
                            finish();
                        } else {
                            Toast.makeText(getApplication(), "Sai mật khẩu hoặc tài khoản!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
}
