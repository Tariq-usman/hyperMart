package com.system.user.menwain.fragments.more.stores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.SelectedStoresCategoryProductsAdapter;
import com.system.user.menwain.adapters.more_adapters.SelectedStoreCategoryAdapter;
import com.system.user.menwain.responses.more.stores.SelectedStoreCategoryProductsResponse;
import com.system.user.menwain.responses.more.stores.SelectedStoreResponse;
import com.system.user.menwain.utils.URLs;

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

public class SelectedStoreFragment extends Fragment implements RecyclerClickInterface, View.OnClickListener {

    private ImageView mBarCodeScanner, ivSearch;
    private EditText etSearch;
    private RecyclerView recyclerViewSelectedStoreCategory, recyclerViewFilterStores, recyclerViewSelectedStoreCategoryProducts;
    private LinearLayoutManager linearLayoutManager;
    private ImageView ivBackBtnSelectedStore, ivSelectedStore;
    private TextView tvStoreName,tvStoreContactNo, tvStoreLocation, tvStoreRating;
    private CardView mSearchViewSelecredStore;
    private RatingBar ratingBar;
    private Preferences prefrences;
    private Bundle bundle;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Geocoder geocoder;
    private List<Address> addresses;
    private SelectedStoreCategoryAdapter storeCategoryAdapter;
    private SelectedStoresCategoryProductsAdapter selectedStorecategoryProductsAdapter;
    private SelectedStoreSelectedCategoryProductsAdapter selectedStoreSelectedCategoryProductsAdapter;
    private List<SelectedStoreResponse.Category> category_list = new ArrayList<>();
    private List<SelectedStoreCategoryProductsResponse.Product.Datum> selected_store_category_products_list = new ArrayList<>();
    private List<SelectedStoreResponse.Product.Datum.Product_> category_products_list = new ArrayList<SelectedStoreResponse.Product.Datum.Product_>();

    public static List<Integer> store_id_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_store, container, false);
        prefrences = new Preferences(getContext());
        geocoder = new Geocoder(getContext(), Locale.ENGLISH);
        bundle = this.getArguments();
        customDialog(getContext());
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

        ivSearch =view.findViewById(R.id.iv_search_selected_store);
        ivSearch.setOnClickListener(this);
        mBarCodeScanner =view.findViewById(R.id.bar_code_scanner_selected_store);
        mBarCodeScanner.setOnClickListener(this);
        etSearch = view.findViewById(R.id.et_search_selected_store);

        ivBackBtnSelectedStore = view.findViewById(R.id.iv_back_selected_stores);
        ivBackBtnSelectedStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreStoresFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new StoresFragment()).addToBackStack(null).commit();
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
        recyclerViewSelectedStoreCategoryProducts.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));


        int store_id = prefrences.getMoreStoreId();
        getSelectedStoreCategory(store_id);

        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        int category_id = category_list.get(position).getId();
        getSelectedStoreCategoryProducts(category_id);
    }


    private void getSelectedStoreCategory(final int store_id) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_category_products_data_url + store_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                for (int i = 0; i < storeResponse.getProduct().getData().get(0).getProducts().size(); i++) {
                    category_products_list.add(storeResponse.getProduct().getData().get(0).getProducts().get(i));
                }
                Log.e("list_size", String.valueOf(category_products_list.size()));
                selectedStorecategoryProductsAdapter = new SelectedStoresCategoryProductsAdapter(getContext(), category_products_list,storeResponse.getStore().getName(),storeResponse.getStore().getId());
                recyclerViewSelectedStoreCategoryProducts.setAdapter(selectedStorecategoryProductsAdapter);
                storeCategoryAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ss_error", error.toString());
                dialog.dismiss();
            }
        });
        requestQueue.add(request);
    }

    private void getSelectedStoreCategoryProducts(int category_id) {
        dialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.get_selected_store_selected_cat_url + category_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SelectedStoreCategoryProductsResponse categoryProductsResponse = gson.fromJson(response, SelectedStoreCategoryProductsResponse.class);
                selected_store_category_products_list.clear();
                for (int i = 0; i < categoryProductsResponse.getProduct().getData().size(); i++) {
                    selected_store_category_products_list.add(categoryProductsResponse.getProduct().getData().get(i));
                }
                selected_store_category_products_list.size();
                selectedStoreSelectedCategoryProductsAdapter = new SelectedStoreSelectedCategoryProductsAdapter(getContext(), selected_store_category_products_list);
                recyclerViewSelectedStoreCategoryProducts.setAdapter(selectedStoreSelectedCategoryProductsAdapter);
                if (selected_store_category_products_list.size() == 0) {
                    Toast.makeText(getContext(), getContext().getString(R.string.no_products), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ss_error", error.toString());
                dialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("store_id", String.valueOf(prefrences.getMoreStoreId()));
                return map;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(10000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void customDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.layout_loading_dialog);
        }
        dialog = builder.create();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_selected_store:
                Bundle bundle = new Bundle();
                SearchFragment fragment = new SearchFragment();
                if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("search", etSearch.getText().toString().trim());
                    etSearch.setText("");
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                }
                break;
            case R.id.bar_code_scanner_selected_store:
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
                break;
        }
    }
}
