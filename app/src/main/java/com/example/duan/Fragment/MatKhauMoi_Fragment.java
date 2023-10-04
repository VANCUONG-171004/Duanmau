package com.example.duan.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

public class MatKhauMoi_Fragment extends Fragment {

    EditText ed_mkcu,ed_moi,ed_agilmkmoi;
    Button btnluu,btnhuy;

    ThuThuDAO dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_doimatkhau,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ed_mkcu = view.findViewById(R.id.ed_mkcu);
        ed_moi = view.findViewById(R.id.ed_mkmoi);
        ed_agilmkmoi = view.findViewById(R.id.aglinmkmoi);
        btnluu = view.findViewById(R.id.btn_save);
        btnhuy = view.findViewById(R.id.btn_huy);

        dao = new ThuThuDAO(getActivity());

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_mkcu.setText("");
                ed_moi.setText("");
                ed_agilmkmoi.setText("");
            }
        });
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //đọc user ,pass trong SharedPrerences
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE",Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME","");

                if (validate()>0){
                    ThuThuDTO thuDTO = dao.getID(user);
                    thuDTO.matKhau = ed_moi.getText().toString();

                    if (dao.updatepass(thuDTO) >0){
                        Toast.makeText(getActivity(), "Thay đổi mk thành công", Toast.LENGTH_SHORT).show();
                        ed_mkcu.setText("");
                        ed_moi.setText("");
                        ed_agilmkmoi.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Thay đổi mk thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    public int validate(){
        int check = 1;
        if (ed_mkcu.getText().length()==0 || ed_moi.getText().length()==0 || ed_agilmkmoi.getText().length()==0){
            Toast.makeText(getContext(), "Bạn Phải nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
            check = -1;

        }else {
            //đọc user /pass trong SharedPrences
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passcu = pref.getString("PASSWORD","");
            String passmoi = ed_moi.getText().toString();
            String repass = ed_agilmkmoi.getText().toString();
            if (!passcu.equals(ed_mkcu.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check =-1;
            }
            if (!passmoi.equals(repass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        return check;
    }
}
