package com.system.user.menwain.fragments.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.home.HomeFragment;
import com.system.user.menwain.others.Preferences;

import java.util.Random;

public class OrderSuccessfulFragment extends Fragment {
    private ImageView mBackBtn;
    private Preferences preferences;
    private ImageView mCart, mFavourite, mHome, mCategory, mMore;
    private TextView tvOrderNo, goHome, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_successfull, container, false);
        preferences = new Preferences(getContext());
        bundle = this.getArguments();
        mHome = getActivity().findViewById(R.id.home_view);
        tvHome = getActivity().findViewById(R.id.tv_home_view);
        mCategory = getActivity().findViewById(R.id.category_view);
        tvCategory = getActivity().findViewById(R.id.tv_category_view);

        mCart = getActivity().findViewById(R.id.cart);
        tvCart = getActivity().findViewById(R.id.tv_cart);

        mFavourite = getActivity().findViewById(R.id.favourite_view);
        tvFavourite = getActivity().findViewById(R.id.tv_favourite_view);

        mMore = getActivity().findViewById(R.id.more);
        tvMore = getActivity().findViewById(R.id.tv_more);


        tvOrderNo = view.findViewById(R.id.tv_order_no_success);
        tvOrderNo.setText("Order # " + bundle.getString("order_no"));
        goHome = view.findViewById(R.id.go_home);
        mBackBtn = view.findViewById(R.id.iv_back_order_success);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                preferences.setCartFragStatus(4);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AddCardFragment())
                        .addToBackStack(null).commit();

            }
        });
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHome.setImageResource(R.drawable.ic_houseblue);
                tvHome.setTextColor(Color.parseColor("#00c1bd"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#004040"));
                mFavourite.setImageResource(R.drawable.ic_likewhite);
                tvFavourite.setTextColor(Color.parseColor("#004040"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#004040"));
                mMore.setImageResource(R.drawable.ic_morewhite);
                tvMore.setTextColor(Color.parseColor("#004040"));
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment())
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }
}
