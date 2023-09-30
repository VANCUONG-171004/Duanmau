package com.example.duan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan.DTO.ThuThuDTO;
import com.example.duan.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    SQLiteDatabase db;

    DbHelper dbHelper;
    public ThuThuDAO(Context context){
         dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThuThuDTO obj){
        ContentValues values = new ContentValues();
        values.put("maTT",obj.maTT);
        values.put("hoTen",obj.hoTen);
        values.put("matKhau",obj.matKhau);
        return db.insert("ThuThu",null,values);
    }

    public int updatepass(ThuThuDTO obj){
        ContentValues values = new ContentValues();
        values.put("maTT",obj.maTT);
        values.put("hoTen",obj.hoTen);
        values.put("matKhau",obj.matKhau);

        return db.update("ThuThu",values,"maTT=?",new String[]{String.valueOf(obj.maTT)});
    }

    public int delete(String id){
        return db.delete("ThuThu","maTT=?",new String[]{id});
    }

    public List<ThuThuDTO> getAll(){
        String sql = "SELECT * FROM ThuThu ";
        return getData(sql);
    }

    public ThuThuDTO getID(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=? ";
        List<ThuThuDTO> list = getData(sql,id);
        return list.get(0);
    }

    private List<ThuThuDTO> getData(String sql , String...selectionArgs){
        List<ThuThuDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()){
                ThuThuDTO obj = new ThuThuDTO();
                obj.maTT = (c.getString(0));
                obj.hoTen = c.getString(1);
                obj.matKhau = c.getString(2);
                list.add(obj);
            }
        }

        return list;
    }
    public boolean checkLogin(String maTT , String password){
        db = dbHelper.getReadableDatabase();

        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?";
        Cursor cursor = db.rawQuery(sql, new String[]{maTT,password});
        int count = cursor.getCount();
        db.close();

        return (count >0);
    }
}
