package com.example.duan.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan.Adapter.LoaiSachAdapter;
import com.example.duan.DTO.LoaiSachDTO;
import com.example.duan.DTO.ThanhVienDTO;
import com.example.duan.Dao.LoaiSachDao;
import com.example.duan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoaiSach_Fragment extends Fragment {

    ListView lv_ls;
    Dialog dialog;
    ArrayList<LoaiSachDTO> list;
    FloatingActionButton fab;
    EditText ed_maloais,ed_tenls;
    Button btnsave, btndelete;
    static LoaiSachDao loaiSachDao;
    LoaiSachAdapter loaiSachAdapter;
    LoaiSachDTO item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_loaisach,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_ls = view.findViewById(R.id.lv_loaisach);
        fab = view.findViewById(R.id.fab_ls);
        loaiSachDao = new LoaiSachDao(getActivity());
        capnhat();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog(getActivity(),0);
            }
        });

        lv_ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                Toast.makeText(getContext(), "hahhah", Toast.LENGTH_SHORT).show();

//                opendialog(getActivity(),1);
                return true;
            }
        });

    }

    void capnhat(){
        list = (ArrayList<LoaiSachDTO>) loaiSachDao.getAll();
        loaiSachAdapter = new LoaiSachAdapter(getActivity(), list,this);
        lv_ls.setAdapter(loaiSachAdapter);
    }

    protected void opendialog(final Context context, final  int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loaisach);

        ed_maloais = dialog.findViewById(R.id.ed_mals);
        ed_tenls = dialog.findViewById(R.id.ed_tenls);


        btnsave = dialog.findViewById(R.id.btn_save_ls);
        btndelete = dialog.findViewById(R.id.btn_huy_ls);


        ed_maloais.setEnabled(false);
        if (type !=0){
            ed_maloais.setText(String.valueOf(item.getMaLoai()));
            ed_tenls.setText(String.valueOf(item.getTenLoai()));
        }
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tenloai = ed_tenls.getText().toString();

                item = new LoaiSachDTO();
                item.setTenLoai(tenloai);

                if (validate() >0){
                    if (type == 0){
                        //type == 0 insert
                        if (loaiSachDao.insert(item)>0){
                            Toast.makeText(context, "Thêm Thành CÔNG", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //type ==1 (update)
                       String update = String.valueOf(Integer.parseInt(ed_maloais.getText().toString()));
                        if (loaiSachDao.updatepass(item) >0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capnhat();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public  int validate(){
        int check = 1;
        if (ed_tenls.getText().length() == 0){
            Toast.makeText(getActivity(), "Bạn Phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check =-1;
        }
       return check;
    }

    public void Xoa(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loaiSachDao.delete(id);
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
        AlertDialog dialog1 = builder.create();
        dialog1.show();
    }
}
