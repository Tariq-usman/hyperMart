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
import android.widget.Toast;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.ExploreAndShopAdapter;
import com.system.user.menwain.adapters.SlidingImage_Adapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ImageView mChangeLayout;
    public boolean isList = false;

    private int[] IMAGES = {R.drawable.dis, R.drawable.disc, R.drawable.disco, R.drawable.discoun, R.drawable.discount, R.drawable.dis, R.drawable.disc, R.drawable.disco, R.drawable.discoun, R.drawable.discount, R.drawable.discounts};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    private String[] productsName = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables","Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] items = {R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep, R.drawable.picturepi, R.drawable.picturepic,R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep, R.drawable.picturepi, R.drawable.picturepic};

    private RecyclerView recyclerViewExploreAndShop, recyclerViewExplore, recyclerViewShop;
    private ExploreAndShopAdapter exploreAndShopAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ImageView mBarCodeScanner;
    private CircleIndicator circleIndicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_home, container, false);

        mPager = view.findViewById(R.id.pager);
        circleIndicator = view.findViewById(R.id.circle);

        mBarCodeScanner = view.findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewExploreAndShop = view.findViewById(R.id.recycler_view_explore_shop);
        recyclerViewExploreAndShop.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewExploreAndShop.setLayoutManager(linearLayoutManager);
        recyclerViewExploreAndShop.setAdapter(new ExploreAndShopAdapter(getContext(), productsName, items));


/*
        recyclerViewExplore = view.findViewById(R.id.recycler_view_explore);
        recyclerViewExplore.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerViewExplore.setLayoutManager(linearLayoutManager);
        recyclerViewExplore.setAdapter(new ExploreAndShopAdapter(getContext(),productsName,items));


        recyclerViewShop = view.findViewById(R.id.recycler_view_shop);
        recyclerViewShop.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerViewShop.setLayoutManager(linearLayoutManager);
        recyclerViewShop.setAdapter(new ExploreAndShopAdapter(getContext(),productsName,items));*/

        init();
      /*  mChangeLayout = view.findViewById(R.id.iv_linear_grid_layout);
        mChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                if (!isList == true){
                    isList = true;
                    mChangeLayout.setImageResource(R.drawable.ic_view_module);
                    recyclerViewExploreAndShop.setHasFixedSize(true);
                    recyclerViewExploreAndShop.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    recyclerViewExploreAndShop.setAdapter(null);
                    recyclerViewExploreAndShop.setAdapter(new ExploreAndShopAdapter(getContext(), productsName, items));
                }else {
                    isList = false;
                    mChangeLayout.setImageResource(R.drawable.ic_view_list);
                    recyclerViewExploreAndShop.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                    recyclerViewExploreAndShop.setLayoutManager(linearLayoutManager);
                    recyclerViewExploreAndShop.setAdapter(null);
                    recyclerViewExploreAndShop.setAdapter(new ExploreAndShopAdapter(getContext(), productsName, items));
                }


            }
        });*/


        return view;
    }

    private void init() {
      /*  for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);*/

        mPager.setAdapter(new SlidingImage_Adapter(getContext(), IMAGES));
        circleIndicator.setViewPager(mPager);


       /* CirclePageIndicator indicator = (CirclePageIndicator)                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);*/

        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 3000);

        // Pager listener over indicator
        /*indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });*/

    }

}
