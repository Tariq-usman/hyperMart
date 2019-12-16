package com.system.user.menwain.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.OrderHistoryAdapter;

public class OrderHistoryFragment extends Fragment implements View.OnClickListener {

    String [] orderNumbers = {"25462","55214","14532","455132","32556","54268"};
    RecyclerView recyclerViewOrderHistory;
    OrderHistoryAdapter orderHistoryAdapter;
    TextView mTitle;
    ImageView mBackBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history,container,false);

        mTitle = view.findViewById(R.id.title_view);
        mBackBtn = view.findViewById(R.id.close_back_view);
        mBackBtn.setOnClickListener(this);

        mTitle.setText("Order History");
        mBackBtn.setImageResource(R.drawable.ic_backwhite);

        recyclerViewOrderHistory = view.findViewById(R.id.recycler_view_order_history);
        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        orderHistoryAdapter = new OrderHistoryAdapter(getContext(),orderNumbers);
        recyclerViewOrderHistory.setAdapter(orderHistoryAdapter);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (view.getId()){
            case R.id.close_back_view:
               // finish();
                break;
        }
    }
}
