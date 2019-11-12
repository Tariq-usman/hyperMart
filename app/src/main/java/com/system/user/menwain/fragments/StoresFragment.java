package com.system.user.menwain.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.CategoryAdapter;
import com.system.user.menwain.adapters.StoresAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StoresFragment extends Fragment {
    private String [] productsName={"Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese"};

    RecyclerView recyclerViewProductCategory;
    StoresAdapter storesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores,container,false);

        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_stores);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(),3,
                GridLayoutManager.VERTICAL,false));
        storesAdapter=new StoresAdapter(productsName);
        recyclerViewProductCategory.setAdapter(storesAdapter);

        return view;
    }
}
