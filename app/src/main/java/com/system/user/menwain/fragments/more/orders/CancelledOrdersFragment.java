package com.system.user.menwain.fragments.more.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrdersCancelledAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CancelledOrdersFragment extends Fragment {

    private String [] amount = {"25","28","23","45","23","55"};
    private String [] order_status = {"Cancelled","Cancelled","Cancelled","Cancelled","Cancelled","Cancelled"};
    private RecyclerView recyclerViewOrdersDelivered;
    private OrdersCancelledAdapter ordersCancelledAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_cancelled,container,false);

        recyclerViewOrdersDelivered = view.findViewById(R.id.recycler_view_orders_cancelled);

        recyclerViewOrdersDelivered.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersCancelledAdapter = new OrdersCancelledAdapter(getContext(),amount,order_status);
        recyclerViewOrdersDelivered.setAdapter(ordersCancelledAdapter);
        return view;
    }
}
