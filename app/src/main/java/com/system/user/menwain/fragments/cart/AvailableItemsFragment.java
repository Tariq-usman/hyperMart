package com.system.user.menwain.fragments.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.AvailableItemsListAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvailableItemsFragment extends Fragment {
    private String [] productsName={"Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese"};

    RecyclerView recyclerViewItemsList;
    AvailableItemsListAdapter availableItemsListAdapter;
    public static int avai_items;
    SharedPreferences.Editor editor;
    List<AvailNotAvailResponse.Datum.Available> avail_items_list = ItemsAvailabilityStoresAdapter.available_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_available_items,container,false);
        recyclerViewItemsList = view.findViewById(R.id.recycler_view_available_items_list);
        recyclerViewItemsList.setHasFixedSize(true);
        recyclerViewItemsList.setLayoutManager(new LinearLayoutManager(getContext()));
        availableItemsListAdapter = new AvailableItemsListAdapter(getContext(),avail_items_list);
        recyclerViewItemsList.setAdapter(availableItemsListAdapter);

         avai_items = productsName.length;
        Log.i("total", String.valueOf(avai_items));
        editor = getActivity().getSharedPreferences("avail_length", Context.MODE_PRIVATE).edit();
        editor.putString("available", String.valueOf(avai_items));
        editor.apply();
        return view;
    }
}
