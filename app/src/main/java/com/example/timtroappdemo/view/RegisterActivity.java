package com.example.timtroappdemo.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText edtUserName, edtPassWord, edtRePassWord;
    private TextView tvLogin;
    private Button btnRegister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

    }

    private void initView(){
        edtUserName = findViewById(R.id.text_input_username);
        edtPassWord = findViewById(R.id.text_input_password);
        edtRePassWord = findViewById(R.id.text_input_repassword);
        tvLogin = findViewById(R.id.tv_Login);
        btnRegister = findViewById(R.id.btn_Login);

        progressDialog = new ProgressDialog(this);

        getSupportActionBar().setTitle("Đăng ký");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalFuntion.startActivity(getApplication(), LogInActivity.class);
                finish();
            }
        });
    }

    private void onClickRegister(){
        String strUserName = edtUserName.getText().toString().trim();
        String strPassWord = edtPassWord.getText().toString().trim();
        String strRePassWord = edtRePassWord.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (strUserName.isEmpty() || strPassWord.isEmpty() || strRePassWord.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else if (!strUserName.matches(emailPattern)){
            Toast.makeText(this, "Email chưa đúng định dạng!", Toast.LENGTH_SHORT).show();
        } else if (!strPassWord.equalsIgnoreCase(strRePassWord)){
            Toast.makeText(this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
        } else if (strPassWord.length() < 6 || strRePassWord.length() < 6){
            Toast.makeText(this, "Mật khẩu phải từ 6 ký tự!", Toast.LENGTH_SHORT).show();
        } else {
            caseRegister(strUserName, strPassWord);
        }
    }

    private void caseRegister(String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.setMessage("Đang đăng ký. . .");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                           showDialogRegisterSuccessfull();
                           reFresh();
                        } else {
                            showDialogRegisterFailed();
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
                .setMessage(R.string.message_exit_app)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void showDialogRegisterSuccessfull() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_register_successfull))
                .setMessage(R.string.message_register_successfull)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GlobalFuntion.startActivity(getApplication(), LogInActivity.class);
                        finish();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void showDialogRegisterFailed() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_register_failed))
                .setMessage(R.string.message_register_failed)
                .setNegativeButton("Đồng ý", null)
                .show();
    }

    private void reFresh(){
        edtRePassWord.setText(null);
        edtUserName.setText(null);
        edtPassWord.setText(null);
    }
}
