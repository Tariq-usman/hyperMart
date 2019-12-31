package com.system.user.menwain.fragments.more.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.AllOrdersAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllOrdersFragment extends Fragment {

    private String [] amount = {"25","28","23","45","23","5","55","75","35","15"};
    RecyclerView recyclerViewOrders;
    AllOrdersAdapter allOrdersAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_orders,container,false);

        recyclerViewOrders = view.findViewById(R.id.recycler_view_orders);

        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        allOrdersAdapter = new AllOrdersAdapter(getContext(),amount);
        recyclerViewOrders.setAdapter(allOrdersAdapter);
        return view;
    }
}
