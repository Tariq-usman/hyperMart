package com.system.user.menwain.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.ExploreAndShopAdapter;
import com.system.user.menwain.adapters.Banner_SlidingImages_Adapter;

import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static ViewPager mPager;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 3000;
    private static int NUM_PAGES = 0;
    private Handler handler;
    private Runnable runnable;
    AllItemsFragment allItemsFragment;
    private  Bundle bundle;
    private int[] IMAGES = {R.drawable.dis, R.drawable.disc, R.drawable.disco, R.drawable.discoun, R.drawable.discount};
    private String[] productsName = {"Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallaxy S8", "Samsung", "BlockBuster", "Gallexy M30",
            "Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallexy M30s", "Samsung s4"};
    private int[] items = {R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep, R.drawable.picturepi,
            R.drawable.picturepic, R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep, R.drawable.picturepi,
            R.drawable.picturepic};

    private RecyclerView recyclerViewExploreAndShop, recyclerViewExplore, recyclerViewShop;
    private LinearLayoutManager linearLayoutManager;
    private ImageView mBarCodeScanner,mBackBtn;
    private TextView tvSeeAllExploreShop, tvSeeAllExplore, tvSeeAllShop;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_home, container, false);

        mPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.INVISIBLE);

        mBarCodeScanner = view.findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        tvSeeAllExploreShop = view.findViewById(R.id.tv_see_all_explore_shop);
        tvSeeAllExploreShop.setOnClickListener(this);

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
        mPager.setAdapter(new Banner_SlidingImages_Adapter(getContext(), IMAGES));
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
            case R.id.tv_see_all_explore_shop:
                allItemsFragment = new AllItemsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                bundle = new Bundle();
                bundle.putString("explore", "1");
                allItemsFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment,allItemsFragment).addToBackStack(null).commit();
                break;
            case R.id.tv_see_all_explore:
                allItemsFragment = new AllItemsFragment();
                FragmentTransaction explore_transaction = getActivity().getSupportFragmentManager().beginTransaction();

                bundle = new Bundle();
                bundle.putString("explore", "2");
                allItemsFragment.setArguments(bundle);
                explore_transaction.replace(R.id.nav_host_fragment,allItemsFragment).addToBackStack(null).commit();
                break;
            case R.id.tv_see_all_shop:
                allItemsFragment = new AllItemsFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                bundle = new Bundle();
                bundle.putString("explore", "3");
                allItemsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment,allItemsFragment).addToBackStack(null).commit();
                break;
        }

    }
}
