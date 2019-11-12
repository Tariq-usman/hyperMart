package com.system.user.menwain.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.FilterItemsAdapter;
import com.system.user.menwain.adapters.SelectedItemAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsFragment extends Fragment{

    RecyclerView recyclerViewProductCategory,recyclerViewFilterItems;
    SelectedItemAdapter selectedItemAdapter;
    FilterItemsAdapter filterItemsAdapter;
    Context context;

    private String [] productsName={"Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_items,container,false);

        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_selected_items);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        selectedItemAdapter=new SelectedItemAdapter(productsName);
        recyclerViewProductCategory.setAdapter(selectedItemAdapter);


        recyclerViewFilterItems = view.findViewById(R.id.recycler_view_filter_items);
        recyclerViewFilterItems.setHasFixedSize(true);
        recyclerViewFilterItems.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        filterItemsAdapter = new FilterItemsAdapter(productsName, getContext());
        recyclerViewFilterItems.setAdapter(filterItemsAdapter);

        return view;
    }

}
