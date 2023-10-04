package com.example.duan.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan.Adapter.LoaiSachSpinnerAdapter;
import com.example.duan.Adapter.SachAdapter;
import com.example.duan.DTO.LoaiSachDTO;
import com.example.duan.DTO.SachDTO;
import com.example.duan.Dao.LoaiSachDao;
import com.example.duan.Dao.SachDao;
import com.example.duan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Sach_Fragment extends Fragment {
    ListView lv_sach;
    SachDao sachDao;

    SachAdapter sachAdapter;
    SachDTO item;
    List<SachDTO> listSach;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMasach,edTenSach,edGiaThue;
    Spinner spinner;
    Button btnsave,btnhuy;

    LoaiSachSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSachDTO> list;
    LoaiSachDao loaiSachDao;
    LoaiSachDTO loaisach;
    int maLoaiSach,positon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_sach,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_sach = view.findViewById(R.id.lv_sach);
        sachDao = new SachDao(getActivity());
        fab = view.findViewById(R.id.fab_sach);
        capnhat();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLog(getActivity(),0);
            }
        });
        lv_sach.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                item = listSach.get(positon);
                openDiaLog(getActivity(),1);
                return false;
            }
        });
    }

    void capnhat(){
     listSach = (List<SachDTO>) sachDao.getAll();
     sachAdapter = new SachAdapter(getActivity(),this,listSach);
     lv_sach.setAdapter(sachAdapter);
    }

    public void Xoa(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sachDao.delete(id);
                capnhat();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void openDiaLog(final Context context , final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sach);
        edMasach = dialog.findViewById(R.id.ed_maSach);
        edTenSach = dialog.findViewById(R.id.ed_tenSach);
        edGiaThue = dialog.findViewById(R.id.ed_giaThue);

        spinner = dialog.findViewById(R.id.spLoaiSach);
        btnsave = dialog.findViewById(R.id.btn_saveSach);
        btnhuy = dialog.findViewById(R.id.btn_HuySach);

        list = new ArrayList<LoaiSachDTO>();
        loaiSachDao = new LoaiSachDao(context);
        list = (ArrayList<LoaiSachDTO>) loaiSachDao.getAll();

        spinnerAdapter = new LoaiSachSpinnerAdapter(context,list);
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = list.get(position).getMaLoai();
                Toast.makeText(context, "Chọn"+list.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edMasach.setEnabled(false);
        if (type !=0){
            edMasach.setText(String.valueOf(item.maSach));
            edTenSach.setText(item.tenSach);
            edGiaThue.setText(String.valueOf(item.giaThue));

            for (int i =0 ;i <list.size();i++){
                if (item.maLoai == (listSach.get(i).maLoai)){
                    positon = i;
                }
                Log.i("demo","posSACH"+positon);
                spinner.setSelection(positon);
            }
        }
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new SachDTO();
                item.tenSach = (edTenSach.getText().toString());
                item.giaThue = (Integer.parseInt(edGiaThue.getText().toString()));
                item.maLoai = maLoaiSach;

                if (validate() >0){
                    if (sachDao.insert(item) >0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    item.maSach = Integer.parseInt(edMasach.getText().toString());
                    if (sachDao.updatepass(item) > 0){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capnhat();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public  int validate(){
        int check = 1;
        if (edTenSach.getText().length() == 0 || edGiaThue.getText().length() == 0){
            Toast.makeText(getActivity(), "Bạn Phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check =-1;
        }
        return check;
    }
}
