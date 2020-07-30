package com.system.user.menwain.fragments.more.stores;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.more_adapters.SelectedStoreSelectedCategoryProductsAdapter;
import com.system.user.menwain.fragments.others.SearchFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.PaginationListenerGridLayoutManager;
import com.system.user.menwain.others.PaginationListenerLinearLayoutManager;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.SelectedStoresCategoryProductsAdapter;
import com.system.user.menwain.adapters.more_adapters.SelectedStoreCategoryAdapter;
import com.system.user.menwain.responses.more.stores.SelectedStoreCategoryProductsResponse;
import com.system.user.menwain.responses.more.stores.SelectedStoreResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.system.user.menwain.others.PaginationListenerGridLayoutManager.PAGE_START;

public class SelectedStoreFragment extends Fragment implements RecyclerClickInterface, View.OnClickListener {

    private ImageView mBarCodeScanner, ivSearch;
    private EditText etSearch;
    private RecyclerView recyclerViewSelectedStoreCategory, recyclerViewFilterStores, recyclerViewSelectedStoreCategoryProducts;
    private LinearLayoutManager linearLayoutManager;
    private ImageView ivBackBtnSelectedStore, ivSelectedStore, mDirection;
    private TextView tvStoreName, tvStoreContactNo, tvStoreLocation, tvStoreRating;
    private CardView mSearchViewSelecredStore;
    private RatingBar ratingBar;
    private Preferences prefrences;
    private Bundle bundle;
    private Dialog dialog;
    private Geocoder geocoder;
    private List<Address> addresses;
    private GridLayoutManager gridLayoutManager;
    private SelectedStoreCategoryAdapter storeCategoryAdapter;
    private SelectedStoresCategoryProductsAdapter selectedStorecategoryProductsAdapter;
    private SelectedStoreSelectedCategoryProductsAdapter selectedStoreSelectedCategoryProductsAdapter;
    private List<SelectedStoreResponse.Category> category_list = new ArrayList<>();
    private List<SelectedStoreCategoryProductsResponse.Product> selected_store_category_products_list = new ArrayList<SelectedStoreCategoryProductsResponse.Product>();
    private List<SelectedStoreResponse.Product.Datum.Product_> category_products_list = new ArrayList<SelectedStoreResponse.Product.Datum.Product_>();

    public static List<Integer> store_id_list = new ArrayList<>();

    int category_id = -1;

    SearchFragment fragment;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int itemCount = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_store, container, false);
        prefrences = new Preferences(getContext());
        geocoder = new Geocoder(getContext(), Locale.ENGLISH);
        bundle = this.getArguments();
        fragment = new SearchFragment();
        dialog = Utils.dialog(getContext());

        final int store_id = prefrences.getMoreStoreId();
        getSelectedStoreCategory(store_id);

        ivSelectedStore = view.findViewById(R.id.iv_store_iamge);
        tvStoreName = view.findViewById(R.id.tv_selected_store_name);
        tvStoreContactNo = view.findViewById(R.id.tv_store_contact_no);
        tvStoreLocation = view.findViewById(R.id.tv_selected_store_place_name);
        tvStoreRating = view.findViewById(R.id.tv_selected_store_rating);
        ratingBar = view.findViewById(R.id.rating_bar_selected_stores);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        //stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorButton), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getContext().getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(getContext().getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);

        ivSearch = view.findViewById(R.id.iv_search_selected_store);
        ivSearch.setOnClickListener(this);
        mBarCodeScanner = view.findViewById(R.id.bar_code_scanner_selected_store);
        mBarCodeScanner.setOnClickListener(this);
        etSearch = view.findViewById(R.id.et_search_selected_store);
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
                        fragment.setArguments(bundle);
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                    }
                    return true;
                }
                return false;
            }
        });

        ivBackBtnSelectedStore = view.findViewById(R.id.iv_back_selected_stores);
        ivBackBtnSelectedStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreStoresFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new StoresFragment()).addToBackStack(null).commit();
            }
        });

        mDirection = view.findViewById(R.id.iv_nav_selected_store);
        mDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = tvStoreLocation.getText().toString().trim();
//                String geoUri = "http://maps.google.com/maps?q=loc:" + detailsResponse.getData().getStore().getLatitude() + "," + detailsResponse.getData().getStore().getLongitude();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + "&daddr=" + address));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        recyclerViewSelectedStoreCategory = view.findViewById(R.id.recycler_view_selected_store);
        recyclerViewSelectedStoreCategory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSelectedStoreCategory.setLayoutManager(linearLayoutManager);
        storeCategoryAdapter = new SelectedStoreCategoryAdapter(getContext(), category_list, this);
        recyclerViewSelectedStoreCategory.setAdapter(storeCategoryAdapter);

        recyclerViewSelectedStoreCategoryProducts = view.findViewById(R.id.recycler_view_selected_store_category_products);
        recyclerViewSelectedStoreCategoryProducts.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewSelectedStoreCategoryProducts.setLayoutManager(gridLayoutManager);
        recyclerViewSelectedStoreCategoryProducts.addOnScrollListener(new PaginationListenerGridLayoutManager(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                if (category_id==-1){
                    getSelectedStoreCategory(store_id);
                }else {
                    getSelectedStoreCategoryProducts(category_id);
                }
            }

            @Override
            protected boolean isLastPage() {
                return isLastPage;
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }
        });




        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {
         category_id = category_list.get(position).getId();
        getSelectedStoreCategoryProducts(category_id);
    }


    private void getSelectedStoreCategory(final int store_id) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_category_products_data_url + store_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    SelectedStoreResponse storeResponse = gson.fromJson(response, SelectedStoreResponse.class);
                    Glide.with(ivSelectedStore.getContext()).load(storeResponse.getStore().getImage()).into(ivSelectedStore);
                    tvStoreName.setText(storeResponse.getStore().getName());
                    tvStoreContactNo.setText(storeResponse.getStore().getMobile());
                    try {
                        addresses = geocoder.getFromLocation(Double.valueOf(storeResponse.getStore().getLatitude()), Double.valueOf(storeResponse.getStore().getLongitude()), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        tvStoreLocation.setText(city);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tvStoreRating.setText("(" + storeResponse.getStore().getAverageRating() + ")");
                    ratingBar.setRating(Float.parseFloat(String.valueOf(storeResponse.getStore().getAverageRating())));
                    category_list.clear();
                    for (int i = 0; i < storeResponse.getCategory().size(); i++) {
                        category_list.add(storeResponse.getCategory().get(i));
                    }
                    storeCategoryAdapter.notifyDataSetChanged();
                    for (int i = 0; i < storeResponse.getProduct().getData().get(0).getProducts().size(); i++) {
                        category_products_list.add(storeResponse.getProduct().getData().get(0).getProducts().get(i));
                    }
                    Log.e("list_size", String.valueOf(category_products_list.size()));
                    selectedStorecategoryProductsAdapter = new SelectedStoresCategoryProductsAdapter(getContext(), category_products_list, storeResponse.getStore().getName(), storeResponse.getStore().getId());
                    recyclerViewSelectedStoreCategoryProducts.setAdapter(selectedStorecategoryProductsAdapter);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ss_error", error.toString());
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
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", prefrences.getLanguage());
                return header;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getSelectedStoreCategoryProducts(final int category_id) {
        dialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.get_selected_store_selected_cat_url + category_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    SelectedStoreCategoryProductsResponse categoryProductsResponse = gson.fromJson(response, SelectedStoreCategoryProductsResponse.class);
                    selected_store_category_products_list.clear();
                    for (int i = 0; i < categoryProductsResponse.getProduct().size(); i++) {
                        selected_store_category_products_list.add(categoryProductsResponse.getProduct().get(i));
                    }
                    selectedStoreSelectedCategoryProductsAdapter = new SelectedStoreSelectedCategoryProductsAdapter(getContext(), categoryProductsResponse.getStore().getName(), selected_store_category_products_list);
                    recyclerViewSelectedStoreCategoryProducts.setAdapter(selectedStoreSelectedCategoryProductsAdapter);
                    selected_store_category_products_list.size();
                    if (selected_store_category_products_list.size() == 0) {
                        Toast.makeText(getContext(), getContext().getString(R.string.no_products), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("exception",e.getMessage());
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ss_error", error.toString());
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
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("store_id", String.valueOf(prefrences.getMoreStoreId()));
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", prefrences.getLanguage());
                header.put("Accept", "application/json");
                return header;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_selected_store:
                Bundle bundle = new Bundle();
                if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("search", etSearch.getText().toString().trim());
                    etSearch.setText("");
                    fragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                }
                break;
            case R.id.bar_code_scanner_selected_store:
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
                break;
        }
    }
}
