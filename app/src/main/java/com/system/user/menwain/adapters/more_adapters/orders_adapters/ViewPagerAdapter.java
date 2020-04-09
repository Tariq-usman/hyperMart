package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import com.system.user.menwain.fragments.more.orders.AllOrdersFragment;
import com.system.user.menwain.fragments.more.orders.CancelledOrdersFragment;
import com.system.user.menwain.fragments.more.orders.DeliveredOrdersFragment;
import com.system.user.menwain.fragments.more.orders.PendingOrdersFragment;
import com.system.user.menwain.fragments.more.orders.ProcessingOrdersFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]{
                new AllOrdersFragment(), //0
               // new PendingOrdersFragment(),
                new ProcessingOrdersFragment(), //1
                new DeliveredOrdersFragment(), //2
                new CancelledOrdersFragment() //3
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length; //4 items
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "All";
        }/*else if (position==1) {
            title="Pending";
        }*/else if (position == 1) {
            title = "Processing";
        } else if (position == 2) {
            title = "Delivered";
        }else if (position == 3) {
            title = "Cancelled";
        }
        return title;
    }
}