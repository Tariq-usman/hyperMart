package com.system.user.menwain.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.ItemsAvailabilityStoresAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresFragment extends Fragment implements View.OnClickListener {
    List<Double> distance;
    List<Integer> price;
    List<Integer> availItems;
    private int[] marts = {R.drawable.mart_logo, R.drawable.martlogo, R.drawable.mart_logo, R.drawable.martlogo, R.drawable.mart_logo, R.drawable.martlogo};
    private RecyclerView recyclerViewAvailableItemsStore;
    ItemsAvailabilityStoresAdapter itemsAvailabilityStoresAdapter;

    private TextView mTitleView, mPayOnline, mPayInStore,mSortByDistance,mSortByPrice,mSortByAvailability;
    private ImageView mBackBtn,mMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_availability_stores,container,false);
        distance = new ArrayList<>();
        distance.add( 11.3);
        distance.add(9.);
        distance.add(17.0);
        distance.add(11.3);
        distance.add(9.0);
        distance.add(17.3);

        price = new ArrayList<>();
        price.add(23);
        price.add(54);
        price.add(67);
        price.add(32);
        price.add(43);
        price.add(22);

        availItems = new ArrayList<>();
        availItems.add(6);
        availItems.add(5);
        availItems.add(3);
        availItems.add(7);
        availItems.add(8);
        availItems.add(4);

        Log.e("sort", distance.toString());

        mSortByPrice = view.findViewById(R.id.sort_by_price_view);
        mSortByDistance = view.findViewById(R.id.sort_by_distance);
        mSortByAvailability = view.findViewById(R.id.sort_by_availability);
        mTitleView = getActivity().findViewById(R.id.toolbar_title);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mPayInStore = view.findViewById(R.id.pay_in_store);
        mPayOnline = view.findViewById(R.id.pay_online);
       /* mMenu = getActivity().findViewById(R.id.iv_open_drawer);
        mMenu.setVisibility(View.GONE);*/
        mBackBtn.setOnClickListener(this);
        mBackBtn.setVisibility(View.VISIBLE);
        mPayOnline.setOnClickListener(this);
        mPayInStore.setOnClickListener(this);
        mSortByDistance.setOnClickListener(this);
        mSortByPrice.setOnClickListener(this);
        mSortByAvailability.setOnClickListener(this);

//        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mTitleView.setText("Stores");

        recyclerViewAvailableItemsStore = view.findViewById(R.id.recycler_view_available_item_store);
        recyclerViewAvailableItemsStore.setHasFixedSize(true);
        recyclerViewAvailableItemsStore.setLayoutManager(new LinearLayoutManager(getContext()));

        itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(marts, distance,availItems, price,getContext());
        recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_by_distance:
                mSortByDistance.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByDistance.setTextColor(Color.parseColor("#FFFFFF"));
                mSortByPrice.setBackgroundResource(0);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));

                mSortByAvailability.setBackgroundResource(0);
                mSortByAvailability.setTextColor(Color.parseColor("#004040"));
                //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                distance.clear();
                distance.add( 11.3);
                distance.add(9.);
                distance.add(17.0);
                distance.add(11.3);
                distance.add(9.0);
                distance.add(17.3);
                Collections.sort(distance);
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                break;

            case R.id.sort_by_availability:
                mSortByAvailability.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByAvailability.setTextColor(Color.parseColor("#FFFFFF"));

                mSortByDistance.setBackgroundResource(0);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));

                mSortByPrice.setBackgroundResource(0);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));
                availItems.clear();
                availItems.add(6);
                availItems.add(5);
                availItems.add(3);
                availItems.add(7);
                availItems.add(8);
                availItems.add(4);
                Collections.sort(availItems);
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                break;
            case R.id.sort_by_price_view:
                mSortByPrice.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByPrice.setTextColor(Color.parseColor("#FFFFFF"));

                mSortByDistance.setBackgroundResource(0);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));

                mSortByAvailability.setBackgroundResource(0);
                mSortByAvailability.setTextColor(Color.parseColor("#004040"));
               // Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                price.clear();
                price.add(23);
                price.add(54);
                price.add(67);
                price.add(32);
                price.add(43);
                price.add(22);
                Collections.sort(price);
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_back:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new DeliveryAddressFragment()).addToBackStack(null).commit();
                mTitleView.setText("Choose Location");
                mBackBtn.setVisibility(View.GONE);
                break;
            case R.id.pay_online:
                mPayOnline.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mPayInStore.setBackgroundResource(0);
                mPayOnline.setTextColor(Color.parseColor("#FFFFFF"));
                mPayInStore.setTextColor(Color.parseColor("#004040"));
                DialogFragmentPayNow method = new DialogFragmentPayNow();
                method.show(getFragmentManager(), "pay online");
                break;
            case R.id.pay_in_store:
                mPayInStore.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mPayOnline.setBackgroundResource(0);
                mPayOnline.setTextColor(Color.parseColor("#004040"));
                mPayInStore.setTextColor(Color.parseColor("#FFFFFF"));
                DialogFragmentPayInstore payInstore = new DialogFragmentPayInstore();
                payInstore.show(getFragmentManager(), "pay in store");


        }
    }
}
