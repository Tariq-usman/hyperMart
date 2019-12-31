package com.system.user.menwain.fragments.more.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrdersProcessingAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProcessingOrdersFragment extends Fragment {

    private String [] amount = {"25","28","23","45","23","55"};
    private String [] order_status = {"Processing","Processing","Processing","Processing","Processing","Processing"};
    RecyclerView recyclerViewOrdersProcessing;
    OrdersProcessingAdapter ordersProcessingAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_processing,container,false);

        recyclerViewOrdersProcessing = view.findViewById(R.id.recycler_view_orders_processing);

        recyclerViewOrdersProcessing.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersProcessingAdapter = new OrdersProcessingAdapter(getContext(),amount,order_status);
        recyclerViewOrdersProcessing.setAdapter(ordersProcessingAdapter);
        return view;
    }
}
