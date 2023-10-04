package com.example.duan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan.DTO.ThuThuDTO;
import com.example.duan.Dao.ThuThuDAO;
import com.example.duan.R;

public class Add_Nguoidung_Fragment extends Fragment {
    EditText ed_tendnhap, ed_hoten, ed_pass, ed_newpass;
    Button btnsave_nd, btn_huy_nd;

    ThuThuDAO dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_themnguoidung,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ed_tendnhap = view.findViewById(R.id.ed_tendnhap);
        ed_hoten = view.findViewById(R.id.ed_hoten);
        ed_pass = view.findViewById(R.id.ed_pass);
        ed_newpass = view.findViewById(R.id.ed_newpass);

        btnsave_nd = view.findViewById(R.id.btn_save_nd);
        btn_huy_nd = view.findViewById(R.id.btn_huy_nd);




        btn_huy_nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_tendnhap.setText("");
                ed_hoten.setText("");
                ed_pass.setText("");
                ed_newpass.setText("");
            }
        });
        btnsave_nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = ed_tendnhap.getText().toString();
                String hoten = ed_hoten.getText().toString();
                String pass = ed_pass.getText().toString();
                ThuThuDTO thuDTO = new ThuThuDTO(user,hoten,pass);


                if (validate()>0){
                    dao = new ThuThuDAO(getActivity());

                    long result = dao.insert(thuDTO);
                    if (result>0){
                        Toast.makeText(getActivity(), "Lưu Thành CÔNG", Toast.LENGTH_SHORT).show();
                        ed_tendnhap.setText("");
                        ed_hoten.setText("");
                        ed_pass.setText("");
                        ed_newpass.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//        btnsave_nd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "hhahha", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    public int validate(){
        int check = 1;
        if (ed_tendnhap.getText().length()==0|| ed_hoten.getText().length()==0|| ed_pass.getText().length()==0 || ed_newpass.getText().length()==0){
            Toast.makeText(getContext(), "Bạn cần nhập thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            String pass = ed_pass.getText().toString();
            String newpass = ed_newpass.getText().toString();
            if (!pass.equals(newpass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check =-1;
            }

        }

        return check;
    }
}
