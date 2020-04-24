package com.system.user.menwain.fragments.more.stores;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.more_adapters.CategoryProductsAdapter;
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
import java.util.List;
import java.util.Locale;

public class SelectedStoreFragment extends Fragment implements RecyclerClickInterface {

    private RecyclerView recyclerViewSelectedStore, recyclerViewFilterStores, recyclerViewCategoryProducts;
    private LinearLayoutManager linearLayoutManager;
    private ImageView ivBackBtnSelectedStore, ivSelectedStore;
    private TextView tvStoreName, tvStoreLocation, tvStoreRating;
    private CardView mSearchViewSelecredStore;
    private RatingBar ratingBar;
    private Preferences prefrences;
    private Bundle bundle;
    private ProgressDialog progressDialog;
    private Geocoder geocoder;
    private List<Address> addresses;
    private SelectedStoreCategoryAdapter storeCategoryAdapter;
    private SelectedStoresCategoryProductsAdapter selectedStorecategoryProductsAdapter;
    private CategoryProductsAdapter categoryProductsAdapter;
    private List<SelectedStoreResponse.Category> category_list = new ArrayList<>();
    private List<SelectedStoreResponse.Product.Datum> selected_store_category_products_list = new ArrayList<>();
    private List<SelectedStoreCategoryProductsResponse.Product.Datum> category_products_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_store, container, false);
        prefrences = new Preferences(getContext());
        geocoder = new Geocoder(getContext(), Locale.ENGLISH);
        bundle = this.getArguments();
        customProgressDialog(getContext());
        ivSelectedStore = view.findViewById(R.id.iv_store_iamge);
        tvStoreName = view.findViewById(R.id.tv_selected_store_name);
        tvStoreLocation = view.findViewById(R.id.tv_selected_store_place_name);
        tvStoreRating = view.findViewById(R.id.tv_selected_store_rating);
        ratingBar = view.findViewById(R.id.rating_bar_selected_stores);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        //stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorButton), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getContext().getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(getContext().getResources().getColor(R.color.yellowColor), PorterDuff.Mode.SRC_ATOP);

        mSearchViewSelecredStore = getActivity().findViewById(R.id.search_view);
        mSearchViewSelecredStore.setVisibility(View.VISIBLE);
        ivBackBtnSelectedStore = getActivity().findViewById(R.id.iv_back);
        ivBackBtnSelectedStore.setVisibility(View.VISIBLE);
        ivBackBtnSelectedStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreStoresFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new StoresFragment()).addToBackStack(null).commit();
                ivBackBtnSelectedStore.setVisibility(View.INVISIBLE);
            }
        });
        recyclerViewSelectedStore = view.findViewById(R.id.recycler_view_selected_store);
        recyclerViewSelectedStore.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSelectedStore.setLayoutManager(linearLayoutManager);
        storeCategoryAdapter = new SelectedStoreCategoryAdapter(getContext(), category_list, this);
        recyclerViewSelectedStore.setAdapter(storeCategoryAdapter);

        recyclerViewCategoryProducts = view.findViewById(R.id.recycler_view_category_products);
        recyclerViewCategoryProducts.setHasFixedSize(true);
        recyclerViewCategoryProducts.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        categoryProductsAdapter = new CategoryProductsAdapter(getContext(), category_products_list);
        recyclerViewCategoryProducts.setAdapter(categoryProductsAdapter);

        int store_id = prefrences.getMoreStoreId();
        getSelectedStoreCategory(store_id);

        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        recyclerViewCategoryProducts.setVisibility(View.VISIBLE);
        int category_id = category_list.get(position).getId();
        getCategoryProducts(category_id);
    }


    private void getSelectedStoreCategory(int store_id) {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_category_products_data_url + store_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SelectedStoreResponse storeResponse = gson.fromJson(response, SelectedStoreResponse.class);
                Glide.with(ivSelectedStore.getContext()).load(storeResponse.getStore().getImage()).into(ivSelectedStore);
                tvStoreName.setText(storeResponse.getStore().getName());
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
                tvStoreRating.setText(String.valueOf(storeResponse.getStore().getAverageRating()));
                ratingBar.setRating(Float.valueOf(storeResponse.getStore().getAverageRating()));
                category_list.clear();
                for (int i = 0; i < storeResponse.getCategory().size(); i++) {
                    category_list.add(storeResponse.getCategory().get(i));
                }
                storeCategoryAdapter.notifyDataSetChanged();
                /*selected_store_category_products_list.clear();
                for (int i = 0; i < storeResponse.getProduct().getData().size(); i++) {
                    selected_store_category_products_list.add(storeResponse.getProduct().getData().get(i));
                }
                selectedStorecategoryProductsAdapter.notifyDataSetChanged();*/
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ss_error", error.toString());
                progressDialog.dismiss();
            }
        });
        requestQueue.add(request);
    }

    private void getCategoryProducts(int category_id) {
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_selected_store_data_url + category_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SelectedStoreCategoryProductsResponse categoryProductsResponse = gson.fromJson(response, SelectedStoreCategoryProductsResponse.class);
                category_products_list.clear();
                for (int i = 0; i < categoryProductsResponse.getProduct().getData().size(); i++) {
                    category_products_list.add(categoryProductsResponse.getProduct().getData().get(i));
                }
                category_products_list.size();
                categoryProductsAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ss_error", error.toString());
                progressDialog.dismiss();
            }
        });
        requestQueue.add(request);
    }

    public void customProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }


}
