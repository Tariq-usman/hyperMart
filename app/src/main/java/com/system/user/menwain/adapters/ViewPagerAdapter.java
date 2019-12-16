package com.system.user.menwain.adapters;

import com.system.user.menwain.fragments.AllOrdersFragment;
import com.system.user.menwain.fragments.OrdersCancelledFragment;
import com.system.user.menwain.fragments.OrdersDeliveredFragment;
import com.system.user.menwain.fragments.OrdersProcessingFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]{
                new AllOrdersFragment(), //0
                new OrdersProcessingFragment(), //1
                new OrdersDeliveredFragment(), //2
                new OrdersCancelledFragment() //3
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length; //3 items
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "All";
        } else if (position == 1) {
            title = "Processing";
        } else if (position == 2) {
            title = "Delivered";
        }else if (position == 3) {
            title = "Cancelled";
        }
        return title;
    }
}