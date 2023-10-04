package com.example.duan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.duan.Fragment.Add_Nguoidung_Fragment;
import com.example.duan.Fragment.LoaiSach_Fragment;
import com.example.duan.Fragment.MatKhauMoi_Fragment;
import com.example.duan.Fragment.Sach_Fragment;
import com.example.duan.Fragment.Thanhvien_Fragment;
import com.google.android.material.navigation.NavigationView;

public class TabActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    Thanhvien_Fragment thanhvien_fragment;
    MatKhauMoi_Fragment matKhauMoi_fragment;

    LoaiSach_Fragment loaiSach_fragment;
    Sach_Fragment sach_fragment;
    NavigationView navigationView;
    Add_Nguoidung_Fragment add_nguoidung_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        drawerLayout = findViewById(R.id.DrawerLayout);
        toolbar = findViewById(R.id.Toolbar);
        navigationView = findViewById(R.id.na_view);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Home");
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_open,R.string.app_close);

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        drawerLayout.addDrawerListener(drawerToggle);

        //khai báo fragment
        thanhvien_fragment = new Thanhvien_Fragment();
        matKhauMoi_fragment = new MatKhauMoi_Fragment();
        add_nguoidung_fragment = new Add_Nguoidung_Fragment();
        loaiSach_fragment = new LoaiSach_Fragment();
        sach_fragment = new Sach_Fragment();

        //thực hiện liên kết fragment trong navi
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.thanhvien){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,thanhvien_fragment).commit();
                    toolbar.setTitle("Quản Lý Thành Viên");
                    drawerLayout.close();
                } else if (item.getItemId() == R.id.doimk) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,matKhauMoi_fragment).commit();
                    toolbar.setTitle("Đổi mật khẩu mới");
                    drawerLayout.close();
                } else if (item.getItemId() == R.id.them_nguoi) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,add_nguoidung_fragment).commit();
                    toolbar.setTitle("Thêm người dùng");
                    drawerLayout.close();
                }
                else if (item.getItemId() == R.id.loaisach) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,loaiSach_fragment).commit();
                    toolbar.setTitle("Loại Sách");
                    drawerLayout.close();
                }
                else if (item.getItemId() == R.id.sach) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,sach_fragment).commit();
                    toolbar.setTitle(" Sách");
                    drawerLayout.close();
                }

                return true;
            }
        });

    }
}