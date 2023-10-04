package com.example.duan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan.DTO.LoaiSachDTO;
import com.example.duan.DTO.SachDTO;
import com.example.duan.Fragment.Sach_Fragment;
import com.example.duan.R;

import java.util.List;

public class LoaiSachSpinnerAdapter extends BaseAdapter {

    Context context;
    Sach_Fragment sach_fragment;
    List<LoaiSachDTO> list;

    TextView tv_maloaiSP,tv_tenLoaiSP;


    public LoaiSachSpinnerAdapter(@NonNull Context context, List<LoaiSachDTO> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaisach_spinner,null);
        }

        final LoaiSachDTO item = list.get(position);
        if (item!= null){
            tv_maloaiSP = v.findViewById(R.id.tv_maLoaiSP);
            tv_tenLoaiSP = v.findViewById(R.id.tv_tenLoaiSP);

            tv_maloaiSP.setText(item.getMaLoai() + " ");
            tv_tenLoaiSP.setText(item.getTenLoai());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaisach_spinner,null);
        }

        final LoaiSachDTO item = list.get(position);
        if (item!= null){
            tv_maloaiSP = v.findViewById(R.id.tv_maLoaiSP);
            tv_tenLoaiSP = v.findViewById(R.id.tv_tenLoaiSP);

            tv_maloaiSP.setText(item.getMaLoai() + " ");
            tv_tenLoaiSP.setText(item.getTenLoai());
        }
        return v;
    }
}
