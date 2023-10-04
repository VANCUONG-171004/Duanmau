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

import com.example.duan.Adapter.ThanhVienAdapter;
import com.example.duan.DTO.ThanhVienDTO;
import com.example.duan.Dao.ThanhVienDAO;
import com.example.duan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Thanhvien_Fragment extends Fragment {

    ListView lv_thanhvien;
    FloatingActionButton fab;
    ArrayList<ThanhVienDTO> list = new ArrayList<>();
    Dialog dialog;
    EditText ed_matv,ed_hotentv,ed_namsinhtv;
    Button btnsave_tv,btnhuy_tv;

    static ThanhVienDAO thanhVienDAO;
    ThanhVienAdapter adapter;

    ThanhVienDTO thanhVienDTO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_thanhvien,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_thanhvien = view.findViewById(R.id.lv_thanhvien);
        fab = view.findViewById(R.id.fab);

        thanhVienDAO = new ThanhVienDAO(getActivity());

        capnhatlv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);
            }
        });
        lv_thanhvien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                thanhVienDTO = list.get(position);
                openDialog(getActivity(),1);

                return true;
            }
        });
    }

    public void capnhatlv(){
        list = (ArrayList<ThanhVienDTO>) thanhVienDAO.getAll();
        adapter = new ThanhVienAdapter(getContext(),list,this);

        lv_thanhvien.setAdapter(adapter);

    }
    protected void openDialog(Context context,int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_thanhvien);

        ed_matv = dialog.findViewById(R.id.ed_matv);
        ed_hotentv = dialog.findViewById(R.id.ed_hotentv);
        ed_namsinhtv = dialog.findViewById(R.id.ed_namsinhtv);

        btnsave_tv = dialog.findViewById(R.id.btn_save_tv);
        btnhuy_tv = dialog.findViewById(R.id.btn_huy_tv);


        ed_matv.setEnabled(false);
        if (type !=0){
            ed_matv.setText(String.valueOf(thanhVienDTO.maTV));
            ed_hotentv.setText(String.valueOf(thanhVienDTO.hoTen));
            ed_namsinhtv.setText(String.valueOf(thanhVienDTO.namSinh));
        }
        btnhuy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnsave_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhVienDTO = new ThanhVienDTO();
                thanhVienDTO.hoTen = ed_hotentv.getText().toString();
                thanhVienDTO.namSinh = ed_namsinhtv.getText().toString();

                if (validate() >0){
                    if (type == 0){
                        //type == 0 insert
                        if (thanhVienDAO.insert(thanhVienDTO)>0){
                            Toast.makeText(context, "Thêm Thành CÔNG", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //type ==1 (update)
                        thanhVienDTO.maTV = Integer.parseInt(ed_matv.getText().toString());
                        if (thanhVienDAO.update(thanhVienDTO)>0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capnhatlv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void Xoa(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                thanhVienDAO.delete(id);
                capnhatlv();
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

    public int validate(){
        int check = 1;
        if (ed_hotentv.getText().length() ==0 || ed_namsinhtv.getText().length()==0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
