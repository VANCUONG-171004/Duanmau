package com.example.duan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.duan.Fragment.Thanhvien_Fragment;
import com.google.android.material.navigation.NavigationView;

public class TabActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    Thanhvien_Fragment thanhvien_fragment;

    NavigationView navigationView;
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

        thanhvien_fragment = new Thanhvien_Fragment();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.thanhvien){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,thanhvien_fragment).commit();
                    toolbar.setTitle("Quản Lý Thành Viên");
                }
                drawerLayout.close();
                return true;
            }
        });

    }
}