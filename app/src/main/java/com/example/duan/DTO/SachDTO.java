package com.example.duan.DTO;

public class SachDTO {

    public int maSach;
    public String tenSach;
    public int giaThue;
    public int maLoai;

    public SachDTO() {
    }

    public SachDTO(int maSach, String tenSach, int giaThue, int maLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
    }
}
