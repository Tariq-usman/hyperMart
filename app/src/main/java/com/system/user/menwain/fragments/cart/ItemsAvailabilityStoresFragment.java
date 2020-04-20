package com.system.user.menwain.fragments.cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.Collections;
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
    List<Double> distance;
    List<Integer> price;
    List<Integer> availItems;
    private int[] marts = {R.drawable.mart_logo, R.drawable.martlogo, R.drawable.mart_logo, R.drawable.martlogo, R.drawable.mart_logo, R.drawable.martlogo};
    private RecyclerView recyclerViewAvailableItemsStore;
    ItemsAvailabilityStoresAdapter itemsAvailabilityStoresAdapter;

    private TextView mTitleView, mPayNow, mPayLater, mSortByDistance, mSortByPrice, mSortByAvailability;
    private ImageView mBackBtn, mMenu;
    private Boolean pay_now, pay_later = false;
    public static Boolean isCheck = false;
    private SharedPreferences.Editor editor;
    Prefrences prefrences;
    private int pay_status;
    private CardView mSearchView;
    private Bundle bundle;
    private ProgressDialog progressDialog;
    List<Integer> cartList = new ArrayList<>();
    CartViewModel cartViewModel;
    private double lat,lang;
    private List<AvailNotAvailResponse.Datum> stores_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_availability_stores, container, false);
        mSearchView = getActivity().findViewById(R.id.search_view);
        mSearchView.setVisibility(View.INVISIBLE);
        prefrences = new Prefrences(getContext());
        pay_status = prefrences.getPaymentStatus();
        bundle = this.getArguments();
        if (bundle!=null){
            lat = bundle.getDouble("lat");
            lang = bundle.getDouble("long");
        }else {
            lat = 33.564545445;
            lang=27.4545454545;
        }

        customProgressDialog(getContext());
        cartViewModel = ViewModelProviders.of(ItemsAvailabilityStoresFragment.this).get(CartViewModel.class);

        cartViewModel.getCartDataList().observe(ItemsAvailabilityStoresFragment.this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                for (int i = 0 ; i<carts.size();i++){
                    cartList.add(carts.get(i).getP_id());
                }
            }
        });


        distance = new ArrayList<>();
        distance.add(11.3);
        distance.add(9.);
        distance.add(17.0);
        distance.add(11.3);
        distance.add(9.0);
        distance.add(17.3);

        price = new ArrayList<>();
        price.add(23);
        price.add(54);
        price.add(67);
        price.add(32);
        price.add(43);
        price.add(22);

        availItems = new ArrayList<>();
        availItems.add(6);
        availItems.add(5);
        availItems.add(3);
        availItems.add(7);
        availItems.add(8);
        availItems.add(4);

        Log.e("sort", distance.toString());

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

        itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(getContext(),stores_list,lat,lang);
        recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_by_distance:
                mSortByDistance.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByDistance.setTextColor(Color.parseColor("#FFFFFF"));
                mSortByPrice.setBackgroundResource(0);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));

                mSortByAvailability.setBackgroundResource(0);
                mSortByAvailability.setTextColor(Color.parseColor("#004040"));
                distance.clear();
                distance.add(11.3);
                distance.add(9.);
                distance.add(17.0);
                distance.add(11.3);
                distance.add(9.0);
                distance.add(17.3);
                Collections.sort(distance);
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                break;

            case R.id.sort_by_availability:
                mSortByAvailability.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByAvailability.setTextColor(Color.parseColor("#FFFFFF"));

                mSortByDistance.setBackgroundResource(0);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));

                mSortByPrice.setBackgroundResource(0);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));
                
                highestAvailabilityData();
                
                availItems.clear();
                availItems.add(6);
                availItems.add(5);
                availItems.add(3);
                availItems.add(7);
                availItems.add(8);
                availItems.add(4);
                Collections.sort(availItems);
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                break;
            case R.id.sort_by_price_view:
                mSortByPrice.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByPrice.setTextColor(Color.parseColor("#FFFFFF"));

                mSortByDistance.setBackgroundResource(0);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));

                mSortByAvailability.setBackgroundResource(0);
                mSortByAvailability.setTextColor(Color.parseColor("#004040"));
                price.clear();
                price.add(23);
                price.add(54);
                price.add(67);
                price.add(32);
                price.add(43);
                price.add(22);
                Collections.sort(price);
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_back:
                prefrences.setCartFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.GONE);
                break;
        }
    }

    private void highestAvailabilityData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude",33.29868671102771);
            jsonObj.put("longitude",71.10277701169252);
            JSONArray jsonArray = new JSONArray();
            for ( int i = 0;i<cartList.size();i++){
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
                for (int i = 0; i<availNotAvailResponse.getData().size();i++){
                    stores_list.add(availNotAvailResponse.getData().get(i));
                }
                itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("avail_error",error.toString());

//                String str = new String(error.networkResponse.data);

//                Log.e("avail_error",error.networkResponse.data.toString());
                progressDialog.dismiss();
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
