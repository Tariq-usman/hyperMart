package com.system.user.menwain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.ItemsAvailabilityStoresAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresActivity extends AppCompatActivity {
    private int[] marts = {R.drawable.mart_logo,R.drawable.martlogo,R.drawable.mart_logo,R.drawable.martlogo,R.drawable.mart_logo,R.drawable.martlogo};
    RecyclerView recyclerViewAvailableItemsStore;
    ItemsAvailabilityStoresAdapter itemsAvailabilityStoresAdapter;

    TextView mTitleView;
    ImageView mBackBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_availability_stores);

        mTitleView = findViewById(R.id.title_view);
        mBackBtn = findViewById(R.id.close_back_view);

        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mTitleView.setText("Stores");

        recyclerViewAvailableItemsStore = findViewById(R.id.recycler_view_available_item_store);
        recyclerViewAvailableItemsStore.setHasFixedSize(true);
        recyclerViewAvailableItemsStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(marts,getApplicationContext());
        recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);
    }
}
