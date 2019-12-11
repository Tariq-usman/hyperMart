package com.system.user.menwain.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.AllItemsActivity;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.ExploreAndShopAdapter;
import com.system.user.menwain.adapters.SlidingImage_Adapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static ViewPager mPager;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 3000;
    private static int NUM_PAGES = 0;
    private Handler handler;
    private Runnable runnable;

    private int[] IMAGES = {R.drawable.dis, R.drawable.disc, R.drawable.disco, R.drawable.discoun, R.drawable.discount};
    private String[] productsName = {"Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallaxy S8", "Samsung", "BlockBuster", "Gallexy M30",
            "Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallexy M30s", "Samsung s4"};
    private int[] items = {R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep, R.drawable.picturepi,
            R.drawable.picturepic, R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep, R.drawable.picturepi,
            R.drawable.picturepic};

    private RecyclerView recyclerViewExploreAndShop, recyclerViewExplore, recyclerViewShop;
    private LinearLayoutManager linearLayoutManager;
    private ImageView mBarCodeScanner;
    private TextView tvSeeAllExploreShop, tvSeeAllExplore, tvSeeAllShop;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_home, container, false);

        mPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        mBarCodeScanner = view.findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        tvSeeAllExploreShop = view.findViewById(R.id.tv_see_all_explore_shop);
        tvSeeAllExploreShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllItemsActivity.class);
                intent.putExtra("explore_shop", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
        tvSeeAllExplore = view.findViewById(R.id.tv_see_all_explore);
        tvSeeAllExplore.setOnClickListener(this);

        tvSeeAllShop = view.findViewById(R.id.tv_see_all_shop);
        tvSeeAllShop.setOnClickListener(this);

        recyclerViewExploreAndShop = view.findViewById(R.id.recycler_view_explore_shop);
        recyclerViewExploreAndShop.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewExploreAndShop.setLayoutManager(linearLayoutManager);
        recyclerViewExploreAndShop.setAdapter(new ExploreAndShopAdapter(getContext(), productsName, items));


        recyclerViewExplore = view.findViewById(R.id.recycler_view_explore);
        recyclerViewExplore.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewExplore.setLayoutManager(linearLayoutManager);
        recyclerViewExplore.setAdapter(new ExploreAndShopAdapter(getContext(), productsName, items));


        recyclerViewShop = view.findViewById(R.id.recycler_view_shop);
        recyclerViewShop.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewShop.setLayoutManager(linearLayoutManager);
        recyclerViewShop.setAdapter(new ExploreAndShopAdapter(getContext(), productsName, items));

        init();
        return view;
    }

    private void init() {

        mPager.setAdapter(new SlidingImage_Adapter(getContext(), IMAGES));
        tabLayout.setupWithViewPager(mPager, true);

        NUM_PAGES = IMAGES.length;

        /*After setting the adapter use the timer */
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (IMAGES.length == currentPage) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                mPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, PERIOD_MS);
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, PERIOD_MS);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_see_all_explore:
                Intent intent_explore = new Intent(getContext(), AllItemsActivity.class);
                intent_explore.putExtra("explore", 2);
                intent_explore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent_explore);
                break;
            case R.id.tv_see_all_shop:
                Intent intent_Shop = new Intent(getContext(), AllItemsActivity.class);
                intent_Shop.putExtra("shop", 3);
                intent_Shop.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent_Shop);
                break;
        }

    }
}
