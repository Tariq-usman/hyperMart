package com.system.user.menwain.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.fragments.DialogFragmentPayInstore;
import com.system.user.menwain.fragments.DialogFragmentPayOnline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] marts = {R.drawable.mart_logo,R.drawable.martlogo,R.drawable.mart_logo,R.drawable.martlogo,R.drawable.mart_logo,R.drawable.martlogo};
    RecyclerView recyclerViewAvailableItemsStore;
    ItemsAvailabilityStoresAdapter itemsAvailabilityStoresAdapter;

    TextView mTitleView,mPayOnline,mPayInStore;
    ImageView mBackBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_availability_stores);

        mTitleView = findViewById(R.id.title_view);
        mBackBtn = findViewById(R.id.close_back_view);
        mPayInStore = findViewById(R.id.pay_in_store);
        mPayOnline = findViewById(R.id.pay_online);
        mBackBtn.setOnClickListener(this);
        mPayOnline.setOnClickListener(this);
        mPayInStore.setOnClickListener(this);

        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mTitleView.setText("Stores");

        recyclerViewAvailableItemsStore = findViewById(R.id.recycler_view_available_item_store);
        recyclerViewAvailableItemsStore.setHasFixedSize(true);
        recyclerViewAvailableItemsStore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(marts,getApplicationContext());
        recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close_back_view:
                finish();
                break;
            case  R.id.pay_online:
                DialogFragmentPayOnline method = new DialogFragmentPayOnline();
                method.show(getSupportFragmentManager(),"pay online");
                break;
            case  R.id.pay_in_store:
                DialogFragmentPayInstore payInstore = new DialogFragmentPayInstore();
                payInstore.show(getSupportFragmentManager(),"pay in store");


        }
    }
}
