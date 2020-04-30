package com.system.user.menwain.fragments.cart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.fragments.my_list.ListDetailsFragment;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemsAvailabilityStoresFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerViewAvailableItemsStore;
    private ItemsAvailabilityStoresAdapter itemsAvailabilityStoresAdapter;
    private TextView mSortByDistance, mSortByPrice, mSortByAvailability;
    private ImageView mBackBtn;
    Preferences prefrences;
    private int pay_status;
    private CardView mSearchView;
    private Bundle bundle;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    List<Integer> cartList = new ArrayList<>();
    CartViewModel cartViewModel;
    private double lat, lang;
    private List<AvailNotAvailResponse.Datum> stores_list = new ArrayList<>();
    List<Integer> reorder_list=  ListDetailsFragment.reorder_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_availability_stores, container, false);
        mSearchView = getActivity().findViewById(R.id.search_view);
        mSearchView.setVisibility(View.INVISIBLE);
        prefrences = new Preferences(getContext());
        pay_status = prefrences.getPaymentStatus();
        bundle = this.getArguments();
        String address = prefrences.getDeliveryAddress();
        String[] split_address = address.split(" ");
        lat = Double.parseDouble(split_address[0]);
        lang = Double.parseDouble(split_address[1]);


        customDialog(getContext());
        cartViewModel = ViewModelProviders.of(ItemsAvailabilityStoresFragment.this).get(CartViewModel.class);

        if (prefrences.getOrderStatus() == 1) {
            cartViewModel.getCartDataList().observe(ItemsAvailabilityStoresFragment.this, new Observer<List<Cart>>() {
                @Override
                public void onChanged(List<Cart> carts) {
                    for (int i = 0; i < carts.size(); i++) {
                        cartList.add(carts.get(i).getP_id());
                    }
                    lowestPrice();
                }
            });
        }else if (prefrences.getOrderStatus()==2){
            for (int i=0;i<reorder_list.size();i++){
                cartList.add(reorder_list.get(i));
            }
            lowestPrice();
        }

        mSortByPrice = view.findViewById(R.id.sort_by_price_view);
        mSortByDistance = view.findViewById(R.id.sort_by_distance);
        mSortByAvailability = view.findViewById(R.id.sort_by_availability);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setOnClickListener(this);
        mBackBtn.setVisibility(View.VISIBLE);
        mSortByDistance.setOnClickListener(this);
        mSortByPrice.setOnClickListener(this);
        mSortByAvailability.setOnClickListener(this);

        recyclerViewAvailableItemsStore = view.findViewById(R.id.recycler_view_available_item_store);
        recyclerViewAvailableItemsStore.setHasFixedSize(true);
        recyclerViewAvailableItemsStore.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(getContext(), stores_list, lat, lang);
        recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_by_price_view:
                mSortByPrice.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByPrice.setTextColor(Color.parseColor("#FFFFFF"));
                mSortByDistance.setBackgroundResource(0);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));
                mSortByAvailability.setBackgroundResource(0);
                mSortByAvailability.setTextColor(Color.parseColor("#004040"));
                lowestPrice();
                break;
            case R.id.sort_by_availability:
                mSortByAvailability.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByAvailability.setTextColor(Color.parseColor("#FFFFFF"));
                mSortByDistance.setBackgroundResource(0);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));
                mSortByPrice.setBackgroundResource(0);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));
                highestAvailabilityData();
                break;
            case R.id.sort_by_distance:
                mSortByDistance.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByDistance.setTextColor(Color.parseColor("#FFFFFF"));
                mSortByPrice.setBackgroundResource(0);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));
                mSortByAvailability.setBackgroundResource(0);
                mSortByAvailability.setTextColor(Color.parseColor("#004040"));
                nearestDistance();
                break;
            case R.id.iv_back:
                prefrences.setCartFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.GONE);
                break;
        }
    }

    private void lowestPrice() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat/*33.29868671102771*/);
            jsonObj.put("longitude", lang/*71.10277701169252*/);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);
            Log.e("products",jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.heighest_availability, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                final AvailNotAvailResponse availNotAvailResponse = gson.fromJson(String.valueOf(response), AvailNotAvailResponse.class);
                stores_list.clear();
                for (int i = 0; i < availNotAvailResponse.getData().size(); i++) {
                    stores_list.add(availNotAvailResponse.getData().get(i));
                }
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("avail_error", error.toString());
                dialog.dismiss();
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

    private void highestAvailabilityData() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lang);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);
            Log.e("hello", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.heighest_availability, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AvailNotAvailResponse availNotAvailResponse = gson.fromJson(String.valueOf(response), AvailNotAvailResponse.class);
                stores_list.clear();
                for (int i = 0; i < availNotAvailResponse.getData().size(); i++) {
                    stores_list.add(availNotAvailResponse.getData().get(i));
                }
                Collections.sort(stores_list, new Comparator<AvailNotAvailResponse.Datum>() {
                    @Override
                    public int compare(AvailNotAvailResponse.Datum o1, AvailNotAvailResponse.Datum o2) {
                        return Integer.valueOf(o2.getAvailable().size()).compareTo(o1.getAvailable().size());
                    }
                });

                //recyclerViewLiveHome.setAdapter(new LiveHomeAdapter(getContext(),productList));

                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("avail_error", error.toString());
                dialog.dismiss();
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

    private void nearestDistance() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lang);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.heighest_availability, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AvailNotAvailResponse availNotAvailResponse = gson.fromJson(String.valueOf(response), AvailNotAvailResponse.class);
                stores_list.clear();
                for (int i = 0; i < availNotAvailResponse.getData().size(); i++) {
                    stores_list.add(availNotAvailResponse.getData().get(i));
                }
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("avail_error", error.toString());
                dialog.dismiss();
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
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.layout_loading_dialog);
        }
        dialog = builder.create();
    }

}
