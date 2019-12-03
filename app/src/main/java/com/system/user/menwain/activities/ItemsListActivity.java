package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.AvailableItemsFragment;
import com.system.user.menwain.fragments.DialogFragmentDeliveryTime;
import com.system.user.menwain.fragments.NotAvailableItemsFragment;

public class ItemsListActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mAvailable, mNotAvailable, mTitle, mConfirmBtn, mPrice, mDistance;
    private ImageView mBackBtn, mMartLogoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();

        mAvailable = findViewById(R.id.available_items);
        mNotAvailable = findViewById(R.id.not_available_items);
        mTitle = findViewById(R.id.title_view);
        mBackBtn = findViewById(R.id.close_back_view);
        mConfirmBtn = findViewById(R.id.confirm_btn_items_list);
        mPrice = findViewById(R.id.sort_by_price_view_item_details);
        mDistance = findViewById(R.id.distance_view_item_details);
        mMartLogoView = findViewById(R.id.mart_logo_view_item_details);

        mAvailable.setOnClickListener(this);
        mNotAvailable.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);


        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mTitle.setText("Items List");

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        mPrice.setText(getIntent().getStringExtra("price"));
        mDistance.setText(getIntent().getStringExtra("distance"));
        mMartLogoView.setImageResource(getIntent().getIntExtra("image_url", 0));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.available_items) {
            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
            getSupportFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();
        } else if (id == R.id.not_available_items) {
            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_selected));
            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unselected));
            getSupportFragmentManager().beginTransaction().replace(R.id.container_items_list, new NotAvailableItemsFragment()).commit();
        } else if (id == R.id.confirm_btn_items_list) {
            DialogFragmentDeliveryTime deliveryTime = new DialogFragmentDeliveryTime();
            deliveryTime.show(getSupportFragmentManager(), "Select Method");
        } else if (id == R.id.close_back_view) {
            finish();
        }
    }
}
