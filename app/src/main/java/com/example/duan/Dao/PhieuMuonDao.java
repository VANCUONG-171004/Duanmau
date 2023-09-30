package com.example.duan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan.DTO.PhieuMuonDTO;
import com.example.duan.Database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonDao {
    SQLiteDatabase db;

    DbHelper dbHelper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public PhieuMuonDao(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuonDTO obj){
        ContentValues values = new ContentValues();
        values.put("maTT",obj.maTT);
        values.put("maTV",obj.maSach);
        values.put("maSach",obj.maSach);
        values.put("tienThue",obj.tienthue);
        values.put("ngay",String.valueOf(obj.ngay));
        values.put("traSach",obj.traSach);

        return db.insert("PhieuMuon",null,values);
    }

    public int updatepass(PhieuMuonDTO obj){
        ContentValues values = new ContentValues();
        values.put("maTT",obj.maTT);
        values.put("maTV",obj.maSach);
        values.put("maSach",obj.maSach);
        values.put("tienThue",obj.tienthue);
        values.put("ngay",String.valueOf(obj.ngay));
        values.put("traSach",obj.traSach);

        return db.update("PhieuMuon",values,"maPM=?",new String[]{String.valueOf(obj.maPM)});
    }

    public int delete(String id){
        return db.delete("PhieuMuon","maPM=?",new String[]{id});
    }

    public List<PhieuMuonDTO> getAll(){
        String sql = "SELECT * FROM maPM ";
        return getData(sql);
    }

    public PhieuMuonDTO getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=? ";
        List<PhieuMuonDTO> list = getData(sql,id);
        return list.get(0);
    }

    private List<PhieuMuonDTO> getData(String sql , String...selectionArgs){
        List<PhieuMuonDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            if (c != null && c.getCount() > 0) {
                long datemili =c.getLong(5);
                Date date = new Date(datemili);
                PhieuMuonDTO obj = new PhieuMuonDTO();
                obj.maPM = Integer.parseInt(c.getString(0));
                obj.maTT = c.getString(1);
                obj.maTV = Integer.parseInt(c.getString(2));
                obj.maSach = Integer.parseInt(c.getString(3));
                obj.tienthue = Integer.parseInt(c.getString(4));
                obj.ngay = date;
                obj.traSach = Integer.parseInt(c.getString(6));
                list.add(obj);
            }
        }
        return list;
    }
}
