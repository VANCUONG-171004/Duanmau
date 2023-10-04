package com.example.duan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.duan.DTO.ThanhVienDTO;
import com.example.duan.Dao.ThanhVienDAO;
import com.example.duan.Fragment.Thanhvien_Fragment;
import com.example.duan.R;

import java.util.ArrayList;

public class ThanhVienAdapter extends BaseAdapter {
    private  Context context;
    private ArrayList<ThanhVienDTO> listtv;
    Thanhvien_Fragment thanhvien_fragment;
    ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVienDTO> listtv, Thanhvien_Fragment thanhvien_fragment) {
        this.context = context;
        this.listtv = listtv;
        this.thanhvien_fragment = thanhvien_fragment;
    }

    @Override
    public int getCount() {
        return listtv.size();
    }

    @Override
    public Object getItem(int position) {
        return listtv.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        view = inflater.inflate(R.layout.item_thanhvien,parent,false);

        final ThanhVienDTO thanhVienDTO = listtv.get(position);
        TextView tv_matv = view.findViewById(R.id.tv_matv);
        TextView tv_tentv = view.findViewById(R.id.tv_hoten);
        TextView tv_namsinhtv = view.findViewById(R.id.tv_namsinh);

        ImageButton btnxoa = view.findViewById(R.id.btn_xoa);

        tv_matv.setText("Mã Thành Viên:"+listtv.get(position).maTV);
        tv_tentv.setText("Họ Tên:"+listtv.get(position).hoTen);
        tv_namsinhtv.setText("Năm Sinh :"+listtv.get(position).namSinh);


        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhvien_fragment.Xoa(String.valueOf(thanhVienDTO.maTV));
            }
        });
        return view;
    }

}
