package com.system.user.menwain.fragments.more.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrdersDeliveredAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveredOrdersFragment extends Fragment {

    private String [] amount = {"25","28","23","45","23","55"};
    private String [] order_status = {"Delivered","Delivered","Delivered","Delivered","Delivered","Delivered"};
    private RecyclerView recyclerViewOrdersDelivered;
    private OrdersDeliveredAdapter ordersDeliveredAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_delivered,container,false);

        recyclerViewOrdersDelivered = view.findViewById(R.id.recycler_view_orders_delivered);

        recyclerViewOrdersDelivered.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersDeliveredAdapter = new OrdersDeliveredAdapter(getContext(),amount,order_status);
        recyclerViewOrdersDelivered.setAdapter(ordersDeliveredAdapter);
        return view;
    }
}
