package com.system.user.menwain.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.OrdersAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersFragment extends Fragment {

    private String [] amount = {"25","25","25","25","25","25","25","25","25","25"};
    RecyclerView recyclerViewOrders;
    OrdersAdapter ordersAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders,container,false);

        recyclerViewOrders = view.findViewById(R.id.recycler_view_orders);

        recyclerViewOrders.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));
        ordersAdapter = new OrdersAdapter(getContext(),amount);
        recyclerViewOrders.setAdapter(ordersAdapter);
        return view;
    }
}
