package com.system.user.menwain.fragments.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.home_adapters.grid_adapters.ShopItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ShopItemsListAdapter;
import com.system.user.menwain.fragments.others.SearchFragment;
import com.system.user.menwain.others.PaginationListenerLinearLayoutManager;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.home_adapters.grid_adapters.ExploreItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ExploreItemsListAdapter;
import com.system.user.menwain.adapters.home_adapters.grid_adapters.ExploreShopItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ExploreShopItemsListAdapter;
import com.system.user.menwain.responses.home.ExploreSellAllResponse;
import com.system.user.menwain.responses.home.ExploreShopeSeeAllResponse;
import com.system.user.menwain.responses.home.ShopSeeAllResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class AllItemsFragment extends Fragment implements View.OnClickListener {
    private EditText etSearch;
    private TextView tvMessage;
    private ImageView mBarCodeScanner, ivSearch, ivBack, ivListGridView;
    private RecyclerView recyclerViewAllitem;
    private LinearLayoutManager linearLayoutManager;
    private boolean isList = false;
    private String val;
    Bundle bundle;
    private CardView searchViewAllItems;
    Preferences prefrences;
    private ExploreShopItemsGridAdapter exploreShopItemsGridAdapter;
    private ExploreShopItemsListAdapter exploreShopItemsListAdapter;
    private ExploreItemsGridAdapter exploreItemsGridAdapter;
    private ExploreItemsListAdapter exploreItemsListAdapter;
    private ShopItemsGridAdapter shopItemsGridAdapter;
    private ShopItemsListAdapter shopItemsListAdapter;
    private Dialog dialog;
    private List<ExploreShopeSeeAllResponse.Datum> explore_shop_grid_list = new ArrayList<>();
    private List<ExploreSellAllResponse.Datum> explore_list = new ArrayList<>();
    private List<ShopSeeAllResponse.Datum> shop_list = new ArrayList<>();
    SearchFragment searchFragment = new SearchFragment();

    GridLayoutManager gridLayoutManager;
    int PAGE_NO = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int itemCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_items, container, false);
        prefrences = new Preferences(getContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog = Utils.dialog(getContext());

        ivSearch = view.findViewById(R.id.iv_search_all);
        ivSearch.setOnClickListener(this);
        mBarCodeScanner = view.findViewById(R.id.bar_code_scanner_all);
        mBarCodeScanner.setOnClickListener(this);
        tvMessage = view.findViewById(R.id.tv_no_items_found_all);
        tvMessage.setVisibility(View.INVISIBLE);
        etSearch = view.findViewById(R.id.et_search_all);
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
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, searchFragment).commit();
                    }
                    return true;
                }
                return false;
            }
        });
        ivListGridView = view.findViewById(R.id.iv_grid_list_view);
        ivListGridView.setVisibility(View.VISIBLE);
        ivListGridView.setImageResource(R.drawable.ic_list_view);
        ivListGridView.setOnClickListener(this);


        ivBack = view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);


        bundle = this.getArguments();
        val = bundle.getString("explore", "");

        recyclerViewAllitem = view.findViewById(R.id.recycler_view_grid_items);
        linearLayoutManager = new LinearLayoutManager(getContext());

        exploreShopItemsListAdapter = new ExploreShopItemsListAdapter(getContext(), explore_shop_grid_list);
        exploreItemsListAdapter = new ExploreItemsListAdapter(getContext(), explore_list);
        shopItemsListAdapter = new ShopItemsListAdapter(getContext(), shop_list);

        if (val.equals("1")) {
            getExploreAndShopeSeeAllData(PAGE_NO);
            recyclerViewAllitem.setHasFixedSize(true);
            gridLayoutManager = new GridLayoutManager(getContext(), 3);
            recyclerViewAllitem.setLayoutManager(gridLayoutManager);
            exploreShopItemsGridAdapter = new ExploreShopItemsGridAdapter(getContext(), explore_shop_grid_list);
            recyclerViewAllitem.setAdapter(exploreShopItemsGridAdapter);
        } else if (val.equals("2")) {
            getExploreSeeAllData(PAGE_NO);
            gridLayoutManager = new GridLayoutManager(getContext(), 3);
            recyclerViewAllitem.setLayoutManager(gridLayoutManager);
            exploreItemsGridAdapter = new ExploreItemsGridAdapter(getContext(), explore_list);
            recyclerViewAllitem.setAdapter(exploreItemsGridAdapter);
        } else if (val.equals("3")) {
            getShopSeeAllData(PAGE_NO);
            gridLayoutManager = new GridLayoutManager(getContext(), 3);
            recyclerViewAllitem.setLayoutManager(gridLayoutManager);
            shopItemsGridAdapter = new ShopItemsGridAdapter(getContext(), shop_list);
            recyclerViewAllitem.setAdapter(shopItemsGridAdapter);
        }
        recyclerViewAllitem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isLoading = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                if (isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) == totalItemCount
                        /*&& firstVisibleItemPosition >= 0&& totalItemCount >= PAGE_SIZE*/) {
                        isLoading = false;
                        if (val.equals("1")) {
                            getExploreAndShopeSeeAllData(PAGE_NO);
                        } else if (val.equals("2")) {
                            getExploreSeeAllData(PAGE_NO);
                        } else {
                            getShopSeeAllData(PAGE_NO);
                        }
                    }
                }
            }
        });
        recyclerViewAllitem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isLoading = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) == totalItemCount
                        /*&& firstVisibleItemPosition >= 0&& totalItemCount >= PAGE_SIZE*/) {
                        isLoading = false;
                        if (val.equals("1")) {
                            getExploreAndShopeSeeAllData(PAGE_NO);
                        } else if (val.equals("2")) {
                            getExploreSeeAllData(PAGE_NO);
                        } else {
                            getShopSeeAllData(PAGE_NO);
                        }
                    }
                }
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                prefrences.setHomeFragStatus(0);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").addToBackStack(null).commit();
                ivBack.setVisibility(View.INVISIBLE);
                ivListGridView.setVisibility(View.INVISIBLE);

                break;
            case R.id.iv_search_home:
                Bundle bundle = new Bundle();
                if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("search", etSearch.getText().toString().trim());
                    etSearch.setText("");
                    searchFragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, searchFragment).commit();
                }
                break;
            case R.id.bar_code_code_scanner_home:
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_grid_list_view:
                if (val.equals("1")) {
                    if (isList == false) {
                        isList = true;
                        PAGE_NO = 1;
                        explore_shop_grid_list.clear();
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(exploreShopItemsListAdapter);
                        getExploreAndShopeSeeAllData(PAGE_NO);

                    } else {
                        isList = false;
                        PAGE_NO = 1;
                        explore_shop_grid_list.clear();
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(gridLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(exploreShopItemsGridAdapter);
                        getExploreAndShopeSeeAllData(PAGE_NO);
                    }
                } else if (val.equals("2")) {
                    if (isList == false) {
                        isList = true;
                        PAGE_NO = 1;
                        explore_list.clear();
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(exploreItemsListAdapter);
                        getExploreSeeAllData(PAGE_NO);

                    } else {
                        isList = false;
                        PAGE_NO = 1;
                        explore_list.clear();
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(gridLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(exploreItemsGridAdapter);
                        getExploreSeeAllData(PAGE_NO);
                    }
                } else if (val.equals("3")) {
                    if (isList == false) {
                        isList = true;
                        PAGE_NO = 1;
                        shop_list.clear();
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(shopItemsListAdapter);
                        getShopSeeAllData(PAGE_NO);
                    } else {
                        isList = false;
                        PAGE_NO = 1;
                        shop_list.clear();
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(gridLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(shopItemsGridAdapter);
                        getShopSeeAllData(PAGE_NO);
                    }
                }
                break;

        }

    }

    private void getExploreAndShopeSeeAllData(final int currentPage) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.see_all_explore_shop + currentPage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ExploreShopeSeeAllResponse seeAllResponse = gson.fromJson(response, ExploreShopeSeeAllResponse.class);
//                explore_shop_grid_list.clear();
                for (int i = 0; i < seeAllResponse.getData().size(); i++) {
                    explore_shop_grid_list.add(seeAllResponse.getData().get(i));
                }
                PAGE_NO++;
                exploreShopItemsGridAdapter.notifyDataSetChanged();
                exploreShopItemsListAdapter.notifyDataSetChanged();
                if (explore_shop_grid_list.size() == 0) {
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText(getString(R.string.no_items_available));
                } else {
                    tvMessage.setVisibility(View.INVISIBLE);
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError", error.toString());
                try {
                    if (error instanceof TimeoutError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.network_timeout));
                    } else if (error instanceof AuthFailureError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.authentication_error));
                    } else if (error instanceof ServerError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.server_error));
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.no_network_found));
                    } else {
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("X-Language", prefrences.getLanguage());
                headerMap.put("Accept", "application/json");
                return headerMap;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getExploreSeeAllData(final int page_no) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.see_all_explore + page_no, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ExploreSellAllResponse seeAllResponse = gson.fromJson(response, ExploreSellAllResponse.class);
//                explore_list.clear();
                for (int i = 0; i < seeAllResponse.getData().size(); i++) {
                    explore_list.add(seeAllResponse.getData().get(i));
                }
                PAGE_NO++;
                exploreItemsGridAdapter.notifyDataSetChanged();
                exploreItemsListAdapter.notifyDataSetChanged();
                if (explore_list.size() == 0) {
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText(getString(R.string.no_items_available));
                } else {
                    tvMessage.setVisibility(View.INVISIBLE);
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError", error.toString());
                try {
                    if (error instanceof TimeoutError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.network_timeout));
                    } else if (error instanceof AuthFailureError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.authentication_error));
                    } else if (error instanceof ServerError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.server_error));
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.no_network_found));
                    } else {
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("X-Language", prefrences.getLanguage());
                headerMap.put("Accept", "application/json");
                return headerMap;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getShopSeeAllData(int page_no) {

        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.see_all_shop + page_no, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ShopSeeAllResponse shopSeeAllResponse = gson.fromJson(response, ShopSeeAllResponse.class);
//                shop_list.clear();
                for (int i = 0; i < shopSeeAllResponse.getData().size(); i++) {
                    shop_list.add(shopSeeAllResponse.getData().get(i));
                }
                PAGE_NO++;
                shopItemsGridAdapter.notifyDataSetChanged();
                shopItemsListAdapter.notifyDataSetChanged();
                if (shop_list.size() == 0) {
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText(getString(R.string.no_items_available));
                } else {
                    tvMessage.setVisibility(View.INVISIBLE);
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError", error.toString());
                try {
                    if (error instanceof TimeoutError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.network_timeout));
                    } else if (error instanceof AuthFailureError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.authentication_error));
                    } else if (error instanceof ServerError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.server_error));
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.no_network_found));
                    } else {
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("X-Language", prefrences.getLanguage());
                headerMap.put("Accept", "application/json");
                return headerMap;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
