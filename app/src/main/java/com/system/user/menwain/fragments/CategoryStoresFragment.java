package com.system.user.menwain.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CategoryStoresFragment extends Fragment  {
    TextView mProductsCategory,mProductsStores;
    private ImageView mBarCodeScanner;
    private Button btn;
    private int CAMREA_CODE = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_stores,container,false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.products_container,new ItemsCategoryFragment()).addToBackStack(null).commit();

        mBarCodeScanner = view.findViewById(R.id.bar_code_code_scanner);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

       /* mProductsCategory=view.findViewById(R.id.products_category);
        mProductsStores = view.findViewById(R.id.products_stores);

        mProductsCategory.setOnClickListener(this);
        mProductsStores.setOnClickListener(this);*/
//        mProductsCategory.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_selected));

        return view;
    }

    /*@Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.products_category){
            mProductsCategory.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_selected));
            mProductsStores.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_unselected));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.products_container,new ItemsCategoryFragment()).addToBackStack(null).commit();

        }*//*else if (id==R.id.products_stores){
            mProductsCategory.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_unselected));
            mProductsStores.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_selected));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.products_container,new StoresFragment()).commit();
        }*//*
    }*/
}
