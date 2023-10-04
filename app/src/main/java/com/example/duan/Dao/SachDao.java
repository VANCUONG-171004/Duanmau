package com.example.duan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan.DTO.SachDTO;
import com.example.duan.Database.DbHelper;


import java.util.ArrayList;
import java.util.List;

public class SachDao {
    SQLiteDatabase db;

    DbHelper dbHelper;
    public SachDao(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(SachDTO obj){
        ContentValues values = new ContentValues();
        values.put("tenSach",obj.tenSach);
        values.put("giaThue",obj.giaThue);
        values.put("maLoai",obj.maLoai);
        return db.insert("Sach",null,values);
    }

    public int updatepass(SachDTO obj){
        ContentValues values = new ContentValues();
        values.put("tenSach",obj.tenSach);
        values.put("giaThue",obj.giaThue);
        values.put("maLoai",obj.maLoai);

        return db.update("Sach",values,"maSach=?",new String[]{String.valueOf(obj.maSach)});
    }

    public int delete(String id){
        return db.delete("Sach","maSach=?",new String[]{id});
    }

    public List<SachDTO> getAll(){
        String sql = "SELECT * FROM Sach ";
        return getData(sql);
    }

    public SachDTO getID(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=? ";
        List<SachDTO> list = getData(sql,id);
        return list.get(0);
    }

    private List<SachDTO> getData(String sql , String...selectionArgs){
        List<SachDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()){
                SachDTO obj = new SachDTO();
                obj.maSach = Integer.parseInt(c.getString(0));
                obj.tenSach = c.getString(1);
                obj.giaThue = c.getInt(2);
                obj.maLoai = Integer.parseInt(c.getString(3));


                list.add(obj);
            }
        }

        return list;
    }
}