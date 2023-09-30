package com.example.duan.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan.DTO.SachDTO;
import com.example.duan.DTO.TopDTO;
import com.example.duan.Database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDao {
    SQLiteDatabase database;
    DbHelper dbHelper;
    Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDao(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public List<TopDTO> getTop(){
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<TopDTO> list = new ArrayList<>();
        SachDao dao = new SachDao(context);
        Cursor c = database.rawQuery(sqlTop,null);
        while (c.moveToNext()){
            TopDTO top = new TopDTO();
            SachDTO sachDTO = dao.getID(c.getString(0));
            top.tenSach = sachDTO.tenSach;
            top.soLuong = Integer.parseInt(c.getString(1));
            list.add(top);

        }
        return list;
    }

    public int getDanhThu(String tuNgay, String denngay){
        String sqlDanhthu = "SELECT SUM(tienThue) as doanhthu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = database.rawQuery(sqlDanhthu,new String[]{tuNgay,denngay});

        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt((c.getString(0))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

}
