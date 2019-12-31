package com.system.user.menwain.fragments.more.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.ViewPagerAdapter;
import com.system.user.menwain.fragments.more.MoreFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class OrdersFragment extends Fragment {

    private ViewPager viewPager;
    private ImageView mBackBtnOrders;
    private TextView mTitleOrder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders,container,false);

        mBackBtnOrders = getActivity().findViewById(R.id.iv_back);
        mBackBtnOrders.setVisibility(View.VISIBLE);
        viewPager = view.findViewById(R.id.vp_orders_container);

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        mBackBtnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MoreFragment()).addToBackStack(null).commit();
                mBackBtnOrders.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}
