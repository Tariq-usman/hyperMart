package com.system.user.menwain.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.AvailableItemsFragment;
import com.system.user.menwain.fragments.DialogFragmentDeliveryTime;
import com.system.user.menwain.fragments.NotAvailableItemsFragment;

public class ItemsListFragment extends Fragment implements View.OnClickListener {
    private TextView mAvailable, mNotAvailable, mTitle, mConfirmBtn, mPrice, mDistance, mAvailItems, mNotAvailItmes;
    private View mShowStatusColor;
    private ImageView mBackBtn, mMenu, mMartLogoView;
    private String dist;
    public String available_items, not_available_items;
    SharedPreferences availPreferences, notAvailPrefrences;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_list, container, false);

        getFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();

        mMenu = getActivity().findViewById(R.id.iv_open_drawer);
        mAvailable = view.findViewById(R.id.available_items);
        mNotAvailable = view.findViewById(R.id.not_available_items);
        mTitle = getActivity().findViewById(R.id.toolbar_title);
        mTitle.setText("Items List");
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mConfirmBtn = view.findViewById(R.id.confirm_btn_items_list);
        mPrice = view.findViewById(R.id.sort_by_price_view_item_details);
        mDistance = view.findViewById(R.id.distance_view_item_details);
        mMartLogoView = view.findViewById(R.id.mart_logo_view_item_details);
        mShowStatusColor = view.findViewById(R.id.show_status_color_view_details);
        mAvailItems = view.findViewById(R.id.count_avail_items);
        mNotAvailItmes = view.findViewById(R.id.count_not_avail_items);

        mAvailable.setOnClickListener(this);
        mNotAvailable.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mBackBtn.setVisibility(View.VISIBLE);

        availPreferences = getActivity().getSharedPreferences("avail_length", Activity.MODE_PRIVATE);
        notAvailPrefrences = getActivity().getSharedPreferences("not_avail_items", Activity.MODE_PRIVATE);
        available_items = availPreferences.getString("available", "");
        not_available_items = notAvailPrefrences.getString("not_available", "");
        Log.i("available", String.valueOf(available_items));
        // available_items = AvailableItemsFragment.avai_items;

        if (!available_items.isEmpty()) {
            mAvailItems.setText(String.valueOf(available_items));
            mNotAvailItmes.setText(String.valueOf(not_available_items));
        }

       // getIncommingBundle();

        return view;
    }

    private void getIncommingBundle() {
        bundle = this.getArguments();
        if (!bundle.isEmpty()) {
            String pr = bundle.getString("price", "");
            mPrice.setText(pr);
            mDistance.setText(bundle.getString("distance", ""));
            mMartLogoView.setImageResource(Integer.parseInt(bundle.getString("image_url", "")));
            Log.i("price", pr);
            dist = mDistance.getText().toString();
            if (Double.valueOf(dist) <= 10) {
                mShowStatusColor.setBackgroundColor(Color.parseColor("#36F43F"));
            } else if (Double.valueOf(dist) > 10 && Double.valueOf(dist) <= 15) {
                mShowStatusColor.setBackgroundColor(Color.parseColor("#FFFFEB3B"));
            } else if (Double.valueOf(dist) > 15) {
                mShowStatusColor.setBackgroundColor(Color.parseColor("#FFF44336"));
            }
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
            getFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();
        } else if (id == R.id.not_available_items) {
            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_avail_items));
            mNotAvailable.setTextColor(Color.parseColor("#FFFFFF"));
            mNotAvailItmes.setTextColor(Color.parseColor("#FFFFFF"));
            mNotAvailItmes.setBackgroundResource(R.drawable.bg_avail_not_avail_item_selected);

            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_not_avail_items));
            mAvailable.setTextColor(Color.parseColor("#004040"));
            mAvailItems.setTextColor(Color.parseColor("#004040"));
            mAvailItems.setBackgroundResource(R.drawable.bg_avail_not_avail_item_unselected);
            getFragmentManager().beginTransaction().replace(R.id.container_items_list, new NotAvailableItemsFragment()).commit();
        } else if (id == R.id.confirm_btn_items_list) {
            DialogFragmentDeliveryTime deliveryTime = new DialogFragmentDeliveryTime();
            deliveryTime.show(getFragmentManager(), "Select Method");
        } else if (id == R.id.iv_back) {
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ItemsAvailabilityStoresFragment()).addToBackStack(null).commit();
            mTitle.setText("Stores");
            mBackBtn.setVisibility(View.GONE);
        }
    }
}
