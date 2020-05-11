package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.orders.AllOrdersFragment;
import com.system.user.menwain.fragments.more.orders.CancelledOrdersFragment;
import com.system.user.menwain.fragments.more.orders.DeliveredOrdersFragment;
import com.system.user.menwain.fragments.more.orders.PendingOrdersFragment;
import com.system.user.menwain.fragments.more.orders.ProcessingOrdersFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;
    Context context;
    // tab titles
  //  private String[] tabTitles = new String[]{context.getString(R.string.all), context.getString(R.string.processing), context.getString(R.string.delivered), context.getString(R.string.cancelled)};
    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
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
            return context.getString(R.string.all);
        } else if (position == 1) {
            return context.getString(R.string.processing);
        } else if (position == 2) {
            return context.getString(R.string.delivered);
        } else if (position == 3) {
            return context.getString(R.string.cancelled);
        }
        return null;
    }
}