package com.system.user.menwain.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.home_adapters.ExploreAdapter;
import com.system.user.menwain.adapters.home_adapters.ShopAdapter;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.home_adapters.ExploreAndShopAdapter;
import com.system.user.menwain.adapters.home_adapters.Banner_SlidingImages_Adapter;
import com.system.user.menwain.responses.HomeExploreAndShop;
import com.system.user.menwain.responses.HomeExploreResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
    private Bundle bundle;
    private int[] IMAGES = {R.drawable.dis, R.drawable.disc, R.drawable.disco, R.drawable.discoun, R.drawable.discount};
    private String[] productsName = {"Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallaxy S8", "Samsung", "BlockBuster", "Gallexy M30",
            "Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallexy M30s", "Samsung s4"};
    private int[] items = {R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep, R.drawable.picturepi,
            R.drawable.picturepic, R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep, R.drawable.picturepi,
            R.drawable.picturepic};

    private RecyclerView recyclerViewExploreAndShop, recyclerViewExplore, recyclerViewShop;
    private LinearLayoutManager linearLayoutManager;
    private ImageView mBarCodeScanner, mBackBtn;
    private TextView tvSeeAllExploreShop, tvSeeAllExplore, tvSeeAllShop;
    private TabLayout tabLayout;
    private CardView mSearchView;
    Prefrences prefrences;
    private int frag_status;
    private Context context;
    private List<HomeExploreAndShop.Datum> exploreShopList = new ArrayList<>();
    private List<HomeExploreResponse.Datum> exploreList = new ArrayList<>();
    private ExploreAndShopAdapter exploreAndShopAdapter;
    private ExploreAdapter exploreAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_home, container, false);
        prefrences = new Prefrences(getContext());
        prefrences.setBottomNavStatus(1);

        getExploreAndShop();
        getExplore();

        mPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.INVISIBLE);
        mSearchView = getActivity().findViewById(R.id.search_view);
        mSearchView.setVisibility(View.VISIBLE);

        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
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
        exploreAndShopAdapter = new ExploreAndShopAdapter(getContext(), exploreShopList);
        recyclerViewExploreAndShop.setAdapter(exploreAndShopAdapter);


        recyclerViewExplore = view.findViewById(R.id.recycler_view_explore);
        recyclerViewExplore.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewExplore.setLayoutManager(linearLayoutManager);
        exploreAdapter = new ExploreAdapter(getContext(),exploreList);
        recyclerViewExplore.setAdapter(exploreAdapter);


        recyclerViewShop = view.findViewById(R.id.recycler_view_shop);
        recyclerViewShop.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewShop.setLayoutManager(linearLayoutManager);
        recyclerViewShop.setAdapter(new ShopAdapter(getContext(), productsName, items));

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
                prefrences.setHomeFragStatus(1);
                allItemsFragment = new AllItemsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                bundle = new Bundle();
                bundle.putString("explore", "1");
                allItemsFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, allItemsFragment).addToBackStack(null).commit();
                break;
            case R.id.tv_see_all_explore:
                prefrences.setHomeFragStatus(2);
                allItemsFragment = new AllItemsFragment();
                FragmentTransaction explore_transaction = getActivity().getSupportFragmentManager().beginTransaction();

                bundle = new Bundle();
                bundle.putString("explore", "2");
                allItemsFragment.setArguments(bundle);
                explore_transaction.replace(R.id.nav_host_fragment, allItemsFragment).addToBackStack(null).commit();
                break;
            case R.id.tv_see_all_shop:
                prefrences.setHomeFragStatus(3);
                allItemsFragment = new AllItemsFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                bundle = new Bundle();
                bundle.putString("explore", "3");
                allItemsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment, allItemsFragment).addToBackStack(null).commit();
                break;
        }

    }

    public void getExploreAndShop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_explore_and_shop_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HomeExploreAndShop exploreAndShop = gson.fromJson(response,HomeExploreAndShop.class);
                exploreShopList.clear();
                for (int i=0;i<exploreAndShop.getData().size();i++){
                    exploreShopList.add(exploreAndShop.getData().get(i));
                    exploreAndShopAdapter.notifyDataSetChanged();
                }
                Log.e( "Response",(String.valueOf(exploreShopList.size())));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "erroe", error.toString());
            }
        });
        requestQueue.add(request);
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    public void getExplore() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_explore_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HomeExploreResponse exploreResponse = gson.fromJson(response, HomeExploreResponse.class);
                exploreShopList.clear();
                for (int i=0;i<exploreResponse.getData().size();i++){
                    exploreList.add(exploreResponse.getData().get(i));
                    exploreAdapter.notifyDataSetChanged();
                }
                Log.e( "Response",(String.valueOf(exploreShopList.size())));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "erroe", error.toString());
            }
        });
        requestQueue.add(request);
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

}
