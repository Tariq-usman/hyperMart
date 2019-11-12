package com.system.user.menwain.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.DeliveryAddressActivity;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.CartItemsAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment implements View.OnClickListener {
    private String [] productsName={"Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese"};

    RecyclerView recyclerViewCartItems;
    CartItemsAdapter cartItemsAdapter;
    TextView mProceedBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);

        mProceedBtn = view.findViewById(R.id.proceed_btn);

        mProceedBtn.setOnClickListener(this);

        recyclerViewCartItems=view.findViewById(R.id.recycler_view_cart_items);
        recyclerViewCartItems.setHasFixedSize(true);
        recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemsAdapter = new CartItemsAdapter(productsName);
        recyclerViewCartItems.setAdapter(cartItemsAdapter);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.proceed_btn){
            startActivity(new Intent(getContext(), DeliveryAddressActivity.class));
        }
    }
}
