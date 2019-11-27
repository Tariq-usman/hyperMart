package com.system.user.menwain.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.activities.DeliveryAddressActivity;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.CartItemsAdapter;
import com.system.user.menwain.entity.Cart;
import com.system.user.menwain.viewmodel.CartViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment implements View.OnClickListener {
    private String [] productsName={"Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese","Produce","Meat & Poultry","Milk & Cheese"};

    private CartViewModel cartViewModel;
    RecyclerView recyclerViewCartItems;
    CartItemsAdapter cartItemsAdapter;
    TextView mProceedBtn, tvTotalCartAmount;
    private float totalCartAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);

        recyclerViewCartItems=view.findViewById(R.id.recycler_view_cart_items);
        recyclerViewCartItems.setHasFixedSize(true);
        recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        mProceedBtn = view.findViewById(R.id.proceed_btn);
        cartViewModel = ViewModelProviders.of(CartFragment.this).get(CartViewModel.class);

        tvTotalCartAmount = view.findViewById(R.id.tv_total_cart_amount);
        cartViewModel.getTotalCartPrice().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {

                if (aFloat != null)
                {
                    tvTotalCartAmount.setText(aFloat+"");
                }
                else {
                    tvTotalCartAmount.setText(00.0+"");
                }

            }
        });

        getCartData();
        mProceedBtn.setOnClickListener(this);

        return view;
    }

    private void getCartData() {
        cartItemsAdapter = new CartItemsAdapter();

        recyclerViewCartItems.setAdapter(cartItemsAdapter);
        cartViewModel.getCartDataList().observe(CartFragment.this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                cartItemsAdapter.setCartData(carts, cartViewModel);
                cartItemsAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.proceed_btn){
            startActivity(new Intent(getContext(), DeliveryAddressActivity.class));

        }
    }
}
