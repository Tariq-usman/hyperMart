package com.system.user.menwain.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.ProfileFragment;
import com.system.user.menwain.activities.RateUsActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment implements View.OnClickListener {

    private ImageView ivOrder, ivCustomerServices,ivStores,ivBack;
    private TextView tvTitle,tvMyAccount, tvProfile, tvSettings, tvHelp, tvAbout, tvRateApp, tvLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        ivBack = getActivity().findViewById(R.id.iv_back);
        tvTitle = getActivity().findViewById(R.id.toolbar_title);
        ivStores = view.findViewById(R.id.iv_stores);
        tvMyAccount = view.findViewById(R.id.tv_my_account);
        tvProfile = view.findViewById(R.id.tv_profile);
        ivOrder = view.findViewById(R.id.iv_order);
        ivCustomerServices = view.findViewById(R.id.iv_customer_services);
        tvSettings = view.findViewById(R.id.tv_settings);
        tvHelp = view.findViewById(R.id.tv_help);
        tvAbout = view.findViewById(R.id.tv_about);
        tvRateApp = view.findViewById(R.id.tv_rate_app);
        tvLogout = view.findViewById(R.id.tv_log_out);

        ivBack.setOnClickListener(this);
        ivStores.setOnClickListener(this);
        tvMyAccount.setOnClickListener(this);
        tvProfile.setOnClickListener(this);
        ivOrder.setOnClickListener(this);
        ivCustomerServices.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvRateApp.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_stores:
                //ivBack.setVisibility(View.VISIBLE);
                tvTitle.setText("Stores");
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new StoresFragment()).addToBackStack(null).commit();
                break;
            case R.id.tv_profile:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).addToBackStack(null).commit();
                break;
            case R.id.iv_order:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).addToBackStack(null).commit();
                break;
            case R.id.tv_rate_app:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new RateUsActivity()).addToBackStack(null).commit();
                break;
            case R.id.tv_log_out:
                SharedPreferences preferences = getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("phone_no", "");
                editor.apply();
                break;
        }

    }
}
