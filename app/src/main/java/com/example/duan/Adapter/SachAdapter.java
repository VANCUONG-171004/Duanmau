package com.example.duan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan.DTO.LoaiSachDTO;
import com.example.duan.DTO.SachDTO;
import com.example.duan.Dao.LoaiSachDao;
import com.example.duan.Dao.SachDao;
import com.example.duan.Fragment.Sach_Fragment;
import com.example.duan.R;

import java.util.List;

public class SachAdapter extends BaseAdapter {
    Context context;
    Sach_Fragment sach_fragment;
    List<SachDTO> list;

    TextView tv_masach,tv_tensach,tv_giathue,tv_loai;
    ImageButton btnsoas,suas;

    SachDao sachDao;

    public SachAdapter(Context context,Sach_Fragment sach_fragment , List<SachDTO> list){
        this.context = context;
        this.sach_fragment = sach_fragment;
        this.list = list;
        sachDao = new SachDao(context);
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
            v = inflater.inflate(R.layout.item_sach,null);
        }

        final SachDTO item= list.get(position);
        if (item!=null) {
            LoaiSachDao loaiSachDao = new LoaiSachDao(context);
            LoaiSachDTO sachDTO = loaiSachDao.getID(String.valueOf(item.maLoai));

            tv_masach = v.findViewById(R.id.tv_mas);
            tv_tensach = v.findViewById(R.id.tv_tens);
            tv_giathue = v.findViewById(R.id.tv_giathues);
            tv_loai = v.findViewById(R.id.tv_loais);

            btnsoas = v.findViewById(R.id.btn_xoaS);
            suas = v.findViewById(R.id.btn_suaS);

            tv_masach.setText("Mã Sách: "+item.maSach +" ");
            tv_tensach.setText("Tên Sách: "+item.tenSach);
            tv_giathue.setText("Giá Thuê: "+item.giaThue + " ");
            tv_loai.setText("Tên Loại: "+sachDTO.getTenLoai());

            btnsoas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sach_fragment.Xoa(String.valueOf(item.maSach));
                }
            });

        }

        return v;
    }
}
