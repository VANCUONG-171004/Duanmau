package com.example.duan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static String dbName = "PNLIB";
    static int dbVersion = 2;

    public DbHelper(Context context){
        super(context,dbName,null,dbVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo bảng thủ thư
        String createTableThuThu =
                "CREATE TABLE ThuThu (" +
                        "maTT TEXT PRIMARY KEY ," +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL) ";
        db.execSQL(createTableThuThu);

        String sql_danhsa = "INSERT INTO ThuThu (maTT,hoTen,matKhau) VALUES ('ph35327','cuong','cuong321')";
        db.execSQL(sql_danhsa);

        //tạo bảng thành viên
        String createTableThanhVien =
                "CREATE TABLE ThanhVien (" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "hoTen TEXT NOT NULL, " +
                        "namSinh TEXT NOT NULL) ";
        db.execSQL(createTableThanhVien);
        //tạo bảng Loại Sách
        String createTableLoaiSach =
                "CREATE TABLE LoaiSach (" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tenLoai TEXT NOT NULL) ";
        db.execSQL(createTableLoaiSach);

        //tạo bảng Sách
        String createTableSach =
                "CREATE TABLE Sach (" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tenSach TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL,"+
                        "maLoai INTEGER REFERENCES LoaiSach(maLoai)) ";
        db.execSQL(createTableSach);

        //tạo bảng Phieu muon
        String createTablePhieuMuon =
                "CREATE TABLE PhieuMuon (" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "maTT TEXT REFERENCES ThuThu(maTT), " +
                        "maTV INTEGER REFERENCES ThanhVien(maTV),"+
                        "maSach INTEGER REFERENCES Sach(maSach)," +
                        "tienThue INTEGER REFERENCES Sach(giaThue)," +
                        "ngay DATE NOT NULL ," +
                        "traSach INTEGER NOT NULL ) ";
        db.execSQL(createTablePhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
