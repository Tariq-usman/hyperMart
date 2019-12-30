package com.system.user.menwain.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment implements View.OnClickListener {

    private ImageView  ivCustomerServices,ivBack,mSettingsIcon;
    private TextView tvTitle,tvOrderHistory, tvProfile, tvSettings, tvHelp, tvAbout, tvRateApp, tvLogout;
    private CardView mSearchViewMore;
    private LinearLayout ivStores,ivOrder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        mSearchViewMore = getActivity().findViewById(R.id.search_view);
        mSearchViewMore.setVisibility(View.INVISIBLE);
        ivBack = getActivity().findViewById(R.id.iv_back);
        /*mSettingsIcon = getActivity().findViewById(R.id.iv_grid_list_view);
        mSettingsIcon.setVisibility(View.VISIBLE);
        mSettingsIcon.setImageResource(R.drawable.ic_settings);*/
        ivStores = view.findViewById(R.id.iv_stores);
        tvProfile = view.findViewById(R.id.tv_profile);
        ivOrder = view.findViewById(R.id.iv_order);
        ivCustomerServices = view.findViewById(R.id.iv_customer_services);
        tvSettings = view.findViewById(R.id.tv_settings);
        tvAbout = view.findViewById(R.id.tv_about);
        tvRateApp = view.findViewById(R.id.tv_rate_app);
        tvLogout = view.findViewById(R.id.tv_log_out);

        ivBack.setOnClickListener(this);
        ivStores.setOnClickListener(this);
        tvProfile.setOnClickListener(this);
        ivOrder.setOnClickListener(this);
        ivCustomerServices.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvRateApp.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_stores:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new StoresFragment()).addToBackStack(null).commit();
//                mSettingsIcon.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_profile:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).addToBackStack(null).commit();
//                mSettingsIcon.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_order:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).addToBackStack(null).commit();
//                mSettingsIcon.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_rate_app:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new RateUsFragment()).addToBackStack(null).commit();
//                mSettingsIcon.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_log_out:
                SharedPreferences preferences = getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("phone_no", "");
                editor.apply();
                Toast.makeText(getContext(), "Logout Successfully..", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
