package com.system.user.menwain.fragments.more.orders;

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

import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrderDetailsAdapter;

public class OrderDetailsFragment extends Fragment {
    // private int[] products = {R.drawable.productpic, R.drawable.productdisplay, R.drawable.productpic, R.drawable.productdisplay, R.drawable.productpic, R.drawable.productdisplay};

    private String[] storesName = {"Madina c carry", "Metro c carry", "Makro c carry", "Pak c carry", "Alrasheed c carry", "ARY c carry",
            "Meezan c carry", "Lahore c carry", "ARY c carry", "Meezan c carry"};
    private String[] productsName = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] products = {R.drawable.coocking_oil, R.drawable.chikken, R.drawable.meat, R.drawable.butter, R.drawable.eggs, R.drawable.choclate, R.drawable.frouts,
            R.drawable.carrot, R.drawable.mango, R.drawable.vagitables};
    RecyclerView recyclerViewFavourite;
    OrderDetailsAdapter orderDetailsAdapter;
    private ImageView mBack;
    private TextView mTitle;
    private Prefrences prefrences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        prefrences = new Prefrences(getContext());

        mBack = getActivity().findViewById(R.id.iv_back);
        mBack.setVisibility(View.VISIBLE);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreOrdersFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).addToBackStack(null).commit();
                mBack.setVisibility(View.INVISIBLE);
            }
        });
        recyclerViewFavourite = view.findViewById(R.id.recycler_view_order_details);
        recyclerViewFavourite.setHasFixedSize(true);
        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getContext()));

        orderDetailsAdapter = new OrderDetailsAdapter(getContext(), products);
        recyclerViewFavourite.setAdapter(orderDetailsAdapter);
        return view;
    }
}
