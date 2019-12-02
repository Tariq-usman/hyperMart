package com.system.user.menwain.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.AvailableItemsListAdapter;
import com.system.user.menwain.adapters.NotAvailableItemsListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotAvailableItemsFragment extends Fragment {
    private String [] productsName={"Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese"};

    RecyclerView recyclerViewItemsList;
    NotAvailableItemsListAdapter notAvailableItemsListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_available_items,container,false);
        recyclerViewItemsList = view.findViewById(R.id.recycler_view_not_available_items_list);
        recyclerViewItemsList.setHasFixedSize(true);
        recyclerViewItemsList.setLayoutManager(new LinearLayoutManager(getContext()));

        notAvailableItemsListAdapter= new NotAvailableItemsListAdapter(productsName,getContext());
        recyclerViewItemsList.setAdapter(notAvailableItemsListAdapter);
        return view;
    }
}
