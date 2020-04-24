package com.system.user.menwain.fragments.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.menu_fragment.ProfileFragment;
import com.system.user.menwain.fragments.more.menu_fragment.RateUsFragment;
import com.system.user.menwain.fragments.more.orders.OrdersFragment;
import com.system.user.menwain.fragments.more.stores.StoresFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment implements View.OnClickListener {

    private ImageView  ivCustomerServices,ivBack,mSettingsIcon;
    private TextView tvTitle,tvOrderHistory, tvProfile, tvSettings, tvHelp, tvAbout, tvRateApp, tvLogout;
    private CardView mSearchViewMore;
    private LinearLayout ivStores,ivOrder;
    Preferences prefrences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        prefrences = new Preferences(getContext());
        mSearchViewMore = getActivity().findViewById(R.id.search_view);
        mSearchViewMore.setVisibility(View.INVISIBLE);
        ivBack = getActivity().findViewById(R.id.iv_back);
        ivBack.setVisibility(View.INVISIBLE);
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
                prefrences.setMorFragStatus(1);
                prefrences.setMoreStoresFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new StoresFragment()).addToBackStack(null).commit();
//                mSettingsIcon.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_profile:
                prefrences.setMorFragStatus(3);
                prefrences.setProfileFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).addToBackStack(null).commit();
//                mSettingsIcon.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_order:
                prefrences.setMorFragStatus(2);
                prefrences.setMoreOrdersFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).addToBackStack(null).commit();
//                mSettingsIcon.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_rate_app:
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new RateUsFragment()).addToBackStack(null).commit();
//                mSettingsIcon.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_log_out:
                prefrences.setToken("");
                Toast.makeText(getContext(), "Logout Successfully..", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
