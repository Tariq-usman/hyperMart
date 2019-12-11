package com.system.user.menwain.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.fragments.DialogFragmentPayInstore;
import com.system.user.menwain.fragments.DialogFragmentPayNow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresActivity extends AppCompatActivity implements View.OnClickListener {
    List<Double> distance;
    List<Integer> price;
    List<Integer> availItems;
    private int[] marts = {R.drawable.mart_logo, R.drawable.martlogo, R.drawable.mart_logo, R.drawable.martlogo, R.drawable.mart_logo, R.drawable.martlogo};
    RecyclerView recyclerViewAvailableItemsStore;
    ItemsAvailabilityStoresAdapter itemsAvailabilityStoresAdapter;

    TextView mTitleView, mPayOnline, mPayInStore,mSortByDistance,mSortByPrice,mSortByAvailability;
    ImageView mBackBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_availability_stores);
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

        mSortByPrice = findViewById(R.id.sort_by_price_view);
        mSortByDistance = findViewById(R.id.sort_by_distance);
        mSortByAvailability = findViewById(R.id.sort_by_availability);
        mTitleView = findViewById(R.id.title_view);
        mBackBtn = findViewById(R.id.close_back_view);
        mPayInStore = findViewById(R.id.pay_in_store);
        mPayOnline = findViewById(R.id.pay_online);
        mBackBtn.setOnClickListener(this);
        mPayOnline.setOnClickListener(this);
        mPayInStore.setOnClickListener(this);
        mSortByDistance.setOnClickListener(this);
        mSortByPrice.setOnClickListener(this);
        mSortByAvailability.setOnClickListener(this);

        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mTitleView.setText("Stores");

        recyclerViewAvailableItemsStore = findViewById(R.id.recycler_view_available_item_store);
        recyclerViewAvailableItemsStore.setHasFixedSize(true);
        recyclerViewAvailableItemsStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(marts, distance,availItems, price,getApplicationContext());
        recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_by_distance:
                mSortByDistance.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByDistance.setTextColor(Color.parseColor("#FFFFFF"));
                mSortByPrice.setBackgroundResource(R.drawable.bg_stores_btn_white);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));

                mSortByAvailability.setBackgroundResource(R.drawable.bg_stores_btn_white);
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

                mSortByDistance.setBackgroundResource(R.drawable.bg_stores_btn_white);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));

                mSortByPrice.setBackgroundResource(R.drawable.bg_stores_btn_white);
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

                mSortByDistance.setBackgroundResource(R.drawable.bg_stores_btn_white);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));

                mSortByAvailability.setBackgroundResource(R.drawable.bg_stores_btn_white);
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
            case R.id.close_back_view:
                finish();
                break;
            case R.id.pay_online:
                mPayOnline.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mPayInStore.setBackgroundResource(R.drawable.bg_stores_btn_white);
                mPayOnline.setTextColor(Color.parseColor("#FFFFFF"));
                mPayInStore.setTextColor(Color.parseColor("#004040"));
                DialogFragmentPayNow method = new DialogFragmentPayNow();
                method.show(getSupportFragmentManager(), "pay online");
                break;
            case R.id.pay_in_store:
                mPayInStore.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mPayOnline.setBackgroundResource(R.drawable.bg_stores_btn_white);
                mPayOnline.setTextColor(Color.parseColor("#004040"));
                mPayInStore.setTextColor(Color.parseColor("#FFFFFF"));
                DialogFragmentPayInstore payInstore = new DialogFragmentPayInstore();
                payInstore.show(getSupportFragmentManager(), "pay in store");


        }
    }
}
