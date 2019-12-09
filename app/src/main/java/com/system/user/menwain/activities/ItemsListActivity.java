package com.system.user.menwain.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
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
    private TextView mAvailable, mNotAvailable, mTitle, mConfirmBtn, mPrice, mDistance, mAvailItems, mNotAvailItmes;
    private View mShowStatusColor;
    private ImageView mBackBtn, mMartLogoView;
    private String dist;
    public String available_items, not_available_items;
    SharedPreferences availPreferences, notAvailPrefrences;

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
        mShowStatusColor = findViewById(R.id.show_status_color_view_details);
        mAvailItems = findViewById(R.id.count_avail_items);
        mNotAvailItmes = findViewById(R.id.count_not_avail_items);

        mAvailable.setOnClickListener(this);
        mNotAvailable.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);

        availPreferences = getSharedPreferences("avail_length", Activity.MODE_PRIVATE);
        notAvailPrefrences = getSharedPreferences("not_avail_items", Activity.MODE_PRIVATE);
        available_items = availPreferences.getString("available", "");
        not_available_items = notAvailPrefrences.getString("not_available","");
        Log.i("available", String.valueOf(available_items));
        // available_items = AvailableItemsFragment.avai_items;
        if (!Integer.valueOf(available_items).equals(null) ) {
//            Log.i("available", String.valueOf(Integer.valueOf(available_items)));
            mAvailItems.setText(String.valueOf(available_items));
            mNotAvailItmes.setText(String.valueOf(not_available_items));
        }
        mBackBtn.setImageResource(R.drawable.ic_backwhite);
        mTitle.setText("Items List");

        getIncomingIntent();


    }

    private void getIncomingIntent() {
        mPrice.setText(getIntent().getStringExtra("price"));
        mDistance.setText(getIntent().getStringExtra("distance"));
        mMartLogoView.setImageResource(getIntent().getIntExtra("image_url", 0));

        dist = mDistance.getText().toString();
        if (Double.valueOf(dist) <= 10) {
            mShowStatusColor.setBackgroundColor(Color.parseColor("#36F43F"));
        } else if (Double.valueOf(dist) > 10 && Double.valueOf(dist) <= 15) {
            mShowStatusColor.setBackgroundColor(Color.parseColor("#FFFFEB3B"));
        } else if (Double.valueOf(dist) > 15) {
            mShowStatusColor.setBackgroundColor(Color.parseColor("#FFF44336"));
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.available_items) {
            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_avail_items));
            mAvailable.setTextColor(Color.parseColor("#FFFFFF"));
            mAvailItems.setTextColor(Color.parseColor("#FFFFFF"));
            mAvailItems.setBackgroundResource(R.drawable.bg_avail_not_avail_item_selected);

            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_not_avail_items));
            mNotAvailItmes.setBackgroundResource(R.drawable.bg_avail_not_avail_item_unselected);
            mNotAvailItmes.setTextColor(Color.parseColor("#004040"));
            mNotAvailable.setTextColor(Color.parseColor("#004040"));
            getSupportFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();
        } else if (id == R.id.not_available_items) {
            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_avail_items));
            mNotAvailable.setTextColor(Color.parseColor("#FFFFFF"));
            mNotAvailItmes.setTextColor(Color.parseColor("#FFFFFF"));
            mNotAvailItmes.setBackgroundResource(R.drawable.bg_avail_not_avail_item_selected);

            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_not_avail_items));
            mAvailable.setTextColor(Color.parseColor("#004040"));
            mAvailItems.setTextColor(Color.parseColor("#004040"));
            mAvailItems.setBackgroundResource(R.drawable.bg_avail_not_avail_item_unselected);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_items_list, new NotAvailableItemsFragment()).commit();
        } else if (id == R.id.confirm_btn_items_list) {
            DialogFragmentDeliveryTime deliveryTime = new DialogFragmentDeliveryTime();
            deliveryTime.show(getSupportFragmentManager(), "Select Method");
        } else if (id == R.id.close_back_view) {
            finish();
        }
    }
}
