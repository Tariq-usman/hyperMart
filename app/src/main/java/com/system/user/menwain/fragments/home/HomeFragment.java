package com.system.user.menwain.fragments.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.home_adapters.ExploreAdapter;
import com.system.user.menwain.adapters.home_adapters.ShopAdapter;
import com.system.user.menwain.fragments.others.SearchFragment;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.home_adapters.ExploreAndShopAdapter;
import com.system.user.menwain.adapters.home_adapters.Banner_SlidingImages_Adapter;
import com.system.user.menwain.responses.home.HomeBannerResponse;
import com.system.user.menwain.responses.home.HomeExploreAndShop;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static ViewPager mPager;
    private ProgressDialog progressDialog;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 3000;
    private static int NUM_PAGES = 0;
    private Handler handler;
    private Runnable runnable;
    AllItemsFragment allItemsFragment;
    private Bundle bundle;

    private RecyclerView recyclerViewExploreAndShop, recyclerViewExplore, recyclerViewShop;
    private LinearLayoutManager linearLayoutManager;
    private ImageView mBarCodeScanner, ivSearch;
    private TextView tvSeeAllExploreShop, tvSeeAllExplore, tvSeeAllShop;
    private EditText etSearch;
    private TabLayout tabLayout;
    Preferences prefrences;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    // List for banner images
    private List<HomeBannerResponse.Datum> bannersList = new ArrayList<>();
    private Banner_SlidingImages_Adapter banner_slidingImages_adapter;

    private List<HomeExploreAndShop.Dymmy1> exploreShopList = new ArrayList<>();
    private List<HomeExploreAndShop.Dummy2> exploreList = new ArrayList<>();
    private List<HomeExploreAndShop.Dummy3> shopList = new ArrayList<>();
    private ExploreAndShopAdapter exploreAndShopAdapter;
    private ExploreAdapter exploreAdapter;
    private ShopAdapter shopAdapter;
    SearchFragment searchFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_home, container, false);
        prefrences = new Preferences(getContext());
        prefrences.setBottomNavStatus(1);
        searchFragment = new SearchFragment();
        customDialog(getContext());

        getExploreAndShop();
        getBannerData();
        //   getExplore();

        mPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        ivSearch = view.findViewById(R.id.iv_search_home);
        ivSearch.setOnClickListener(this);
        mBarCodeScanner = view.findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(this);
        etSearch = view.findViewById(R.id.et_search_home);
        etSearch.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Bundle bundle = new Bundle();
                    if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                        Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                    } else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        bundle.putString("search", etSearch.getText().toString().trim());
                        etSearch.setText("");
                        searchFragment.setArguments(bundle);
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, searchFragment).commit();
                    }
                    return true;
                }
                return false;
            }
        });


        tvSeeAllExploreShop = view.findViewById(R.id.tv_see_all_explore_shop);
        tvSeeAllExploreShop.setOnClickListener(this);
        tvSeeAllExplore = view.findViewById(R.id.tv_see_all_explore);
        tvSeeAllExplore.setOnClickListener(this);
        tvSeeAllShop = view.findViewById(R.id.tv_see_all_shop);
        tvSeeAllShop.setOnClickListener(this);

        /*-------------Adapter of banner----------*/
        banner_slidingImages_adapter = new Banner_SlidingImages_Adapter(getContext(), bannersList);

        /*-------------recyclerview of Explore and shop----------*/
        recyclerViewExploreAndShop = view.findViewById(R.id.recycler_view_explore_shop);
        recyclerViewExploreAndShop.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewExploreAndShop.setLayoutManager(linearLayoutManager);
        exploreAndShopAdapter = new ExploreAndShopAdapter(getContext(), exploreShopList);
        recyclerViewExploreAndShop.setAdapter(exploreAndShopAdapter);

        /*-------------recyclerview of Explore ----------*/
        recyclerViewExplore = view.findViewById(R.id.recycler_view_explore);
        recyclerViewExplore.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewExplore.setLayoutManager(linearLayoutManager);
        exploreAdapter = new ExploreAdapter(getContext(), exploreList);
        recyclerViewExplore.setAdapter(exploreAdapter);

        /*-------------recyclerview of shop----------*/
        recyclerViewShop = view.findViewById(R.id.recycler_view_shop);
        recyclerViewShop.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewShop.setLayoutManager(linearLayoutManager);
        shopAdapter = new ShopAdapter(getContext(), shopList);
        recyclerViewShop.setAdapter(shopAdapter);

        /* ----Banner ---*/
        init();
        return view;
    }

    private void init() {
        mPager.setAdapter(banner_slidingImages_adapter);
        tabLayout.setupWithViewPager(mPager, true);
        NUM_PAGES = bannersList.size();
        /*After setting the adapter use the timer */
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (bannersList.size() == currentPage) {
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
            case R.id.iv_search_home:
                Bundle bundle = new Bundle();
                if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("search", etSearch.getText().toString().trim());
                    etSearch.setText("");
                    searchFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, searchFragment).commit();
                }
                break;
            case R.id.bar_code_code_scanner_home:
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
                break;
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

    private void getBannerData() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_banner_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HomeBannerResponse bannerResponse = gson.fromJson(response, HomeBannerResponse.class);
                bannersList.clear();
                for (int i = 0; i < bannerResponse.getData().size(); i++) {
                    bannersList.add(bannerResponse.getData().get(i));
                }
                banner_slidingImages_adapter.notifyDataSetChanged();
                try {
                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erroe", error.toString());
                try {
                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", prefrences.getLanguage());
                return header;
            }
        };
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


    public void getExploreAndShop() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_explore_and_shop_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HomeExploreAndShop exploreAndShop = gson.fromJson(response, HomeExploreAndShop.class);
                exploreShopList.clear();
                for (int i = 0; i < exploreAndShop.getDymmy1().size(); i++) {
                    exploreShopList.add(exploreAndShop.getDymmy1().get(i));
                }
                exploreAndShopAdapter.notifyDataSetChanged();

                exploreList.clear();
                for (int i = 0; i < exploreAndShop.getDummy2().size(); i++) {
                    exploreList.add(exploreAndShop.getDummy2().get(i));
                }
                exploreAdapter.notifyDataSetChanged();

                shopList.clear();
                for (int i = 0; i < exploreAndShop.getDummy3().size(); i++) {
                    shopList.add(exploreAndShop.getDummy3().get(i));
                }
                shopAdapter.notifyDataSetChanged();
                Log.e("Response", (String.valueOf(exploreShopList.size())));
                try {
                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erroe", error.toString());
                try {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getContext(), getString(R.string.network_timeout), Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getContext(), getString(R.string.authentication_error), Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        Toast.makeText(getContext(), getString(R.string.no_network_found), Toast.LENGTH_LONG).show();
                    } else {
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", prefrences.getLanguage());
                return header;
            }
        };
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
                HomeExploreAndShop exploreAndShop = gson.fromJson(response, HomeExploreAndShop.class);
                exploreShopList.clear();
                for (int i = 0; i < exploreAndShop.getDummy2().size(); i++) {
                    exploreList.add(exploreAndShop.getDummy2().get(i));
                    exploreAdapter.notifyDataSetChanged();
                }
                Log.e("Response", (String.valueOf(exploreShopList.size())));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erroe", error.toString());
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

    public void customDialog(Context context) {
        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.layout_loading_dialog);
        }
        dialog = builder.create();
    }


}
