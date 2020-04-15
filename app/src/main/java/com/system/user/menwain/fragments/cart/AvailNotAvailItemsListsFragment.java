package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentSaveList;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentDeliveryTime;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;

public class AvailNotAvailItemsListsFragment extends Fragment implements View.OnClickListener {
    private CartViewModel cartViewModel;
    private TextView mAvailable, mNotAvailable, mTitle, mTotalAmount, mPrice, mDistance, mAvailItems, mNotAvailItmes;
    private View mShowStatusColor;
    private ImageView mBackBtn, mBarCodeScanner, mMartLogoView;
    private LinearLayout mConfirmBtn;
    private String dist;
    public String available_items, not_available_items;
    SharedPreferences availPreferences, notAvailPrefrences;
    Bundle bundle;
    private Boolean pay_now, pay_later = false;
    public static Boolean isCheck = false;
    Prefrences prefrences;
    private int pay_status;
    private CardView mSearchView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avail_not_avial_items_lists, container, false);
        bundle = this.getArguments();
        prefrences = new Prefrences(getContext());
        getFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();
        float lat1 = (float) 33.29868671102771;
        float long1 = (float) 71.10277701169252;
        float lat2 = (float) 33.288126707444405;
        float long2 = (float) 71.16980742663145;
        getKmFromLatLong(lat1,long1,lat2,long2);
        mSearchView = getActivity().findViewById(R.id.search_view);
        mSearchView.setVisibility(View.VISIBLE);
        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        mTotalAmount = view.findViewById(R.id.tv_total_amount_avial_items);
        cartViewModel = ViewModelProviders.of(AvailNotAvailItemsListsFragment.this).get(CartViewModel.class);
        cartViewModel.getTotalCartPrice().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                if (aFloat != null) {
                    mTotalAmount.setText(aFloat.toString());
                } else {
                    mTotalAmount.setText(00.0 + "");
                }
            }
        });
        mAvailable = view.findViewById(R.id.available_items);
        mNotAvailable = view.findViewById(R.id.not_available_items);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mConfirmBtn = view.findViewById(R.id.confirm_btn_items_list);
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

        if (!available_items.isEmpty()) {
            mAvailItems.setText(String.valueOf(available_items));
            mNotAvailItmes.setText(String.valueOf(not_available_items));
        }
        return view;
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
            pay_status = prefrences.getPayRBtnStatus();
            if (pay_status == 5) {
                DialogFragmentSaveList dialogFragmentSaveList = new DialogFragmentSaveList();
                dialogFragmentSaveList.show(getFragmentManager(), "Purchasing Method");
            } else {
                //if (pay_status == 2){
                DialogFragmentDeliveryTime deliveryTime = new DialogFragmentDeliveryTime();
                deliveryTime.show(getFragmentManager(), "Select Method");
            }
        } else if (id == R.id.iv_back) {
            prefrences.setCartFragStatus(2);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ItemsAvailabilityStoresFragment()).addToBackStack(null).commit();
            mBackBtn.setVisibility(View.GONE);
        }
    }
    public static float getKmFromLatLong(float lat1, float lng1, float lat2, float lng2){
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lng1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);
        float distanceInMeters = loc1.distanceTo(loc2);
        Log.e("distance" , distanceInMeters/1000 +" km");
        return distanceInMeters/1000;
    }
}
