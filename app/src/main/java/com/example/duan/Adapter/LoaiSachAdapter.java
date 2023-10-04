package com.example.duan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.duan.DTO.LoaiSachDTO;
import com.example.duan.Dao.LoaiSachDao;
import com.example.duan.Fragment.LoaiSach_Fragment;
import com.example.duan.R;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachAdapter extends BaseAdapter {

    private Context context;
     ArrayList<LoaiSachDTO> listls;
    LoaiSach_Fragment loaiSach_fragment;

    TextView tv_mals,tv_tenls;

    ImageButton btnxoa,btnsua;

    LoaiSachDao dao;

    public LoaiSachAdapter(Context context , ArrayList<LoaiSachDTO> listls, LoaiSach_Fragment loaiSach_fragment){
        this.context = context;
        this.listls = listls;
        this.loaiSach_fragment = loaiSach_fragment;
        dao = new LoaiSachDao(context);
    }


    @Override
    public int getCount() {
        return listls.size();
    }

    @Override
    public Object getItem(int position) {
        return listls.get(position);
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
            v = inflater.inflate(R.layout.item_loaisach,null);
        }


        final LoaiSachDTO  dto = listls.get(position);

        tv_mals = v.findViewById(R.id.tv_mals);
        tv_tenls = v.findViewById(R.id.tv_tenls);

        btnxoa = v.findViewById(R.id.btn_xoals);
        btnsua = v.findViewById(R.id.btn_suals);

        tv_mals.setText("Mã Loại:"+ listls.get(position).getMaLoai());
        tv_tenls.setText("Tên Loại : " + listls.get(position).getTenLoai());


        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSach_fragment.Xoa(String.valueOf(dto.getMaLoai()));
            }
        });

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //load layout cho dialog
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view = inflater.inflate(R.layout.update_loaisach,null);

                builder.setView(view);
                final AlertDialog dialog = builder.create();

                EditText ed_mals_update = view.findViewById(R.id.ed_mals_up);
                EditText ed_tenls_update = view.findViewById(R.id.ed_tenls_up);

                Button btn_row_update = view.findViewById(R.id.btn_save_ls_up);
                LoaiSachDTO sachDTO = new LoaiSachDTO();


                ed_tenls_update.setText(sachDTO.getTenLoai());

                btn_row_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoaiSachDTO loaiSachDTO = listls.get(position);
                        int maLoai = loaiSachDTO.getMaLoai();
                        String name = ed_tenls_update.getText().toString();

                        LoaiSachDTO pro = new LoaiSachDTO(maLoai,name);


                        if (dao.updatepass(pro) >0){
                            Toast.makeText(context, "cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                            listls.clear();
                            listls.addAll(dao.getAll());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        return v;
    }
    public void Xoa(final String id){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(id);
                listls.clear();
                listls.addAll(dao.getAll());
                notifyDataSetChanged();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        android.app.AlertDialog dialog1 = builder.create();
        dialog1.show();
    }
}
