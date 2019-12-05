package com.system.user.menwain.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.fragments.DialogFragmentPayInstore;
import com.system.user.menwain.fragments.DialogFragmentPayOnline;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresActivity extends AppCompatActivity implements View.OnClickListener {
    List<Double> distance;
    List<Integer> price;
    private int[] marts = {R.drawable.mart_logo, R.drawable.martlogo, R.drawable.mart_logo, R.drawable.martlogo, R.drawable.mart_logo, R.drawable.martlogo};
    RecyclerView recyclerViewAvailableItemsStore;
    ItemsAvailabilityStoresAdapter itemsAvailabilityStoresAdapter;

    TextView mTitleView, mPayOnline, mPayInStore,mSortByDistance,mSortByPrice;
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

        Log.e("sort", distance.toString());

        mSortByPrice = findViewById(R.id.sort_by_price_view);
        mSortByDistance = findViewById(R.id.sort_by_distance);
        mTitleView = findViewById(R.id.title_view);
        mBackBtn = findViewById(R.id.close_back_view);
        mPayInStore = findViewById(R.id.pay_in_store);
        mPayOnline = findViewById(R.id.pay_online);
        mBackBtn.setOnClickListener(this);
        mPayOnline.setOnClickListener(this);
        mPayInStore.setOnClickListener(this);
        mSortByDistance.setOnClickListener(this);
        mSortByPrice.setOnClickListener(this);

        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mTitleView.setText("Stores");

        recyclerViewAvailableItemsStore = findViewById(R.id.recycler_view_available_item_store);
        recyclerViewAvailableItemsStore.setHasFixedSize(true);
        recyclerViewAvailableItemsStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(marts, distance, price,getApplicationContext());
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

            case R.id.sort_by_price_view:
                mSortByPrice.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByPrice.setTextColor(Color.parseColor("#FFFFFF"));

                mSortByDistance.setBackgroundResource(R.drawable.bg_stores_btn_white);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));
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
                DialogFragmentPayOnline method = new DialogFragmentPayOnline();
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
