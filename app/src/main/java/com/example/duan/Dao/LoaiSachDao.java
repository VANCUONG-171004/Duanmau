package com.example.duan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan.DTO.LoaiSachDTO;
import com.example.duan.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDao {
    SQLiteDatabase db;

    DbHelper dbHelper;
    public LoaiSachDao(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSachDTO obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.getTenLoai());
        return db.insert("LoaiSach",null,values);
    }

    public int updatepass(LoaiSachDTO obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.getTenLoai());

        return db.update("LoaiSach",values,"maLoai=?",new String[]{String.valueOf(obj.getMaLoai())});
    }

    public int delete(String id){
        return db.delete("LoaiSach","maLoai=?",new String[]{id});
    }

    public List<LoaiSachDTO> getAll(){
        String sql = "SELECT * FROM LoaiSach ";
        return getData(sql);
    }

    public LoaiSachDTO getID(String id){
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=? ";
        List<LoaiSachDTO> list = getData(sql,id);
        return list.get(0);
    }

    private List<LoaiSachDTO> getData(String sql , String...selectionArgs){
        List<LoaiSachDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()){
                LoaiSachDTO  obj = new LoaiSachDTO();
                obj.setMaLoai(Integer.parseInt(c.getString(0)));
                obj.setTenLoai(c.getString(1));

                list.add(obj);
            }
        }

        return list;
    }
}
