package com.example.duan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan.DTO.ThanhVienDTO;
import com.example.duan.Database.DbHelper;


import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    SQLiteDatabase db;

    public ThanhVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVienDTO obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.hoTen);
        values.put("namSinh",obj.namSinh);
        return db.insert("ThanhVien",null,values);
    }

    public int update(ThanhVienDTO obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.hoTen);
        values.put("namSinh",obj.namSinh);

        return db.update("ThanhVien",values,"maTV=?",new String[]{String.valueOf(obj.maTV)});
    }

    public int delete(String id){
        return db.delete("ThanhVien","maTV=?",new String[]{id});
    }

    public List<ThanhVienDTO> getAll(){
        String sql = "SELECT * FROM ThanhVien ";
        return getData(sql);
    }

    public ThanhVienDTO getID(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=? ";
        List<ThanhVienDTO> list = getData(sql,id);
        return list.get(0);
    }

    private List<ThanhVienDTO> getData(String sql , String...selectionArgs){
        List<ThanhVienDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()){
                ThanhVienDTO obj = new ThanhVienDTO();
                obj.maTV = Integer.parseInt(c.getString(0));
                obj.hoTen = c.getString(1);
                obj.namSinh = c.getString(2);
                list.add(obj);
            }
        }

        return list;
    }
}
