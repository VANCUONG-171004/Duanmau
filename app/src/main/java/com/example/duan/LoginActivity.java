package com.example.duan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan.Dao.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    EditText ed_user,ed_pass;
    CheckBox chk_check;
    Button btn_login,btn_cancel;

    ThuThuDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);
        chk_check = findViewById(R.id.chk_check);
        btn_cancel = findViewById(R.id.btn_cancel);

        dao = new ThuThuDAO(this);
        //đọc user, pas trong SharedPrences

        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        ed_user.setText(pref.getString("USERNAME",""));
        ed_pass.setText(pref.getString("PASSWORD",""));
        chk_check.setChecked(pref.getBoolean("REMEMBER",false));

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_user.setText("");
                ed_pass.setText("");
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_user.getText().toString();
                String password = ed_pass.getText().toString();
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                }else {
                    ThuThuDAO dao = new ThuThuDAO(LoginActivity.this);
                    if (dao.checkLogin(username,password)>0){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, TabActivity.class));
                        rememberUSer(username,password,chk_check.isChecked());
                    }else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    public void rememberUSer(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status){
            editor.clear();

        }else {
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }
}