package com.example.duan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan.Dao.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    EditText ed_user,ed_pass;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_user.getText().toString();
                String password = ed_pass.getText().toString();
                ThuThuDAO dao = new ThuThuDAO(LoginActivity.this);
                if (dao.checkLogin(username,password)){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, TabActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}