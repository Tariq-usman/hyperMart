package com.system.user.menwain.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment  extends Fragment implements View.OnClickListener {
    TextView mProductsCategory,mProductsStores;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.products_container,new CategoryFragment()).commit();

        mProductsCategory=view.findViewById(R.id.products_category);
        mProductsStores = view.findViewById(R.id.products_stores);

        mProductsCategory.setOnClickListener(this);
        mProductsStores.setOnClickListener(this);
        mProductsCategory.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_selected));

        return view;
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.products_category){
            mProductsCategory.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_selected));
            mProductsStores.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_unselected));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.products_container,new CategoryFragment()).commit();

        }else if (id==R.id.products_stores){
            mProductsCategory.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_unselected));
            mProductsStores.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_selected));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.products_container,new StoresFragment()).commit();
        }
    }
}
