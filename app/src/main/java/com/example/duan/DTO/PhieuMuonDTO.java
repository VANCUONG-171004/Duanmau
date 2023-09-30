package com.example.duan.DTO;

import java.util.Date;

public class PhieuMuonDTO {
    public int maPM;
    public String maTT;
    public int maTV;
    public int maSach;
    public Date ngay;
    public int tienthue;
    public int traSach;

    public PhieuMuonDTO() {
    }

    public PhieuMuonDTO(int maPM, String maTT, int maTV, int maSach, Date ngay, int tienthue, int traSach) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngay = ngay;
        this.tienthue = tienthue;
        this.traSach = traSach;
    }
}
