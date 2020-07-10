package com.system.user.menwain.fragments.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilitySelectedStoresAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresRadiusAdapter;
import com.system.user.menwain.fragments.cart.apply_filter.DialogFragmentFilterStores;
import com.system.user.menwain.fragments.more.stores.SelectedStoreFragment;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.responses.cart.AvailNotAvailRadiusResponse;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;
import com.system.user.menwain.responses.cart.SelectedStoreProductsResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ItemsAvailabilityStoresFragment extends Fragment implements View.OnClickListener, DialogFragmentFilterStores.OnDismissDialogInterface {
    private EditText etSearch;
    private RecyclerView recyclerViewAvailableItemsStore;

    private ItemsAvailabilityStoresAdapter itemsAvailabilityStoresAdapter;
    private ItemsAvailabilityStoresRadiusAdapter itemsAvailabilityStoresRadiusAdapter;
    private ItemsAvailabilitySelectedStoresAdapter itemsAvailabilitySelectedStoresAdapter;

    private TextView mSortByDistance, mSortByPrice, mSortByAvailability, tvNoStoreFound;
    private ImageView mBackBtn, mFilterStores;
    Preferences prefrences;
    private int pay_status;
    private CardView mSearchView;
    private Bundle bundle;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private List<Integer> cartList = new ArrayList<>();
    private CartViewModel cartViewModel;
    private double lat, lang;
    private List<AvailNotAvailResponse.Datum> stores_list = new ArrayList<>();
    private List<AvailNotAvailRadiusResponse.Datum> stores_list_radius = new ArrayList<AvailNotAvailRadiusResponse.Datum>();
    private List<SelectedStoreProductsResponse.Datum> selected_stores_list = new ArrayList<SelectedStoreProductsResponse.Datum>();
    List<AvailNotAvailResponse.Datum> filter_list = new ArrayList<>();
    List<AvailNotAvailRadiusResponse.Datum> filter_list_radius = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_availability_stores, container, false);
        cartViewModel = ViewModelProviders.of(ItemsAvailabilityStoresFragment.this).get(CartViewModel.class);

        prefrences = new Preferences(getContext());
        pay_status = prefrences.getPaymentStatus();
        bundle = this.getArguments();
        String address = prefrences.getDeliveryAddress();
        String[] split_address = address.split(" ");
        lat = Double.parseDouble(split_address[0]);
        lang = Double.parseDouble(split_address[1]);

        customDialog(getContext());

        int order_status = prefrences.getOrderStatus();

        cartViewModel.getCartDataList().observe((LifecycleOwner) getContext(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                cartList.clear();
                for (int i = 0; i < carts.size(); i++) {
                    cartList.add(carts.get(i).getP_id());
                    String store_name = carts.get(i).getStore_name();
                }
                if (prefrences.getOrderStatus() == 0) {
                    lowestPrice();
                } else if (prefrences.getOrderStatus() == 1) {
                    lowestPriceAsRadius();
                } else if (prefrences.getOrderStatus() == 3) {
                    selectedStoreLowestPrice();
                }
            }
        });

        tvNoStoreFound = view.findViewById(R.id.tv_no_store_found);
        etSearch = view.findViewById(R.id.et_search_availability_store);
        etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white, 0, 0, 0);
        mSortByPrice = view.findViewById(R.id.sort_by_price_view);
        mSortByDistance = view.findViewById(R.id.sort_by_distance);
        mSortByAvailability = view.findViewById(R.id.sort_by_availability);
        mBackBtn = view.findViewById(R.id.iv_back_items_avail_store);
        mBackBtn.setOnClickListener(this);
        mFilterStores = view.findViewById(R.id.iv_filter_stores_list);
        mFilterStores.setOnClickListener(this);
        mSortByDistance.setOnClickListener(this);
        mSortByPrice.setOnClickListener(this);
        mSortByAvailability.setOnClickListener(this);


        recyclerViewAvailableItemsStore = view.findViewById(R.id.recycler_view_available_item_store);
        recyclerViewAvailableItemsStore.setHasFixedSize(true);
        recyclerViewAvailableItemsStore.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(getContext(), stores_list, lat, lang);
        recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    //Assign your image again to the view, otherwise it will always be gone even if the text is 0 again.
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white, 0, 0, 0);
                }
                String query = s.toString().toLowerCase();
                if (stores_list.size() > 0) {
                    filter_list.clear();
                    for (int i = 0; i < stores_list.size(); i++) {
                        final String text = stores_list.get(i).getName().toLowerCase();
                        if (text.contains(query)) {
                            filter_list.add(stores_list.get(i));
                        }
                    }
                    itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(getContext(), filter_list, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);
                } else {
                    filter_list_radius.clear();
                    for (int i = 0; i < stores_list_radius.size(); i++) {
                        final String text = stores_list_radius.get(i).getName().toLowerCase();
                        if (text.contains(query)) {
                            filter_list_radius.add(stores_list_radius.get(i));
                        }
                    }
                    itemsAvailabilityStoresRadiusAdapter = new ItemsAvailabilityStoresRadiusAdapter(getContext(), filter_list_radius, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresRadiusAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
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
                if (prefrences.getOrderStatus() == 0) {
                    lowestPrice();
                } else if (prefrences.getOrderStatus() == 1) {
                    lowestPriceAsRadius();
                } else if (prefrences.getOrderStatus() == 3) {
                    selectedStoreLowestPrice();
                }
                break;
            case R.id.sort_by_availability:
                mSortByAvailability.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByAvailability.setTextColor(Color.parseColor("#FFFFFF"));
                mSortByDistance.setBackgroundResource(0);
                mSortByDistance.setTextColor(Color.parseColor("#004040"));
                mSortByPrice.setBackgroundResource(0);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));
                if (prefrences.getOrderStatus() == 0) {
                    highestAvailabilityData();
                } else if (prefrences.getOrderStatus() == 1) {
                    highestAvailabilityAsRadius(prefrences.getRadius());
                } else if (prefrences.getOrderStatus() == 3) {
                    selectedStoreHighestAvailability();
                }
                break;
            case R.id.sort_by_distance:
                mSortByDistance.setBackgroundResource(R.drawable.bg_store_btn_colored);
                mSortByDistance.setTextColor(Color.parseColor("#FFFFFF"));
                mSortByPrice.setBackgroundResource(0);
                mSortByPrice.setTextColor(Color.parseColor("#004040"));
                mSortByAvailability.setBackgroundResource(0);
                mSortByAvailability.setTextColor(Color.parseColor("#004040"));
                if (prefrences.getOrderStatus() == 0) {
                    nearestDistance();
                } else if (prefrences.getOrderStatus() == 1) {
                    nearestDistanceAsRadius(prefrences.getRadius());
                } else if (prefrences.getOrderStatus() == 3) {
                    selectedStoreNearestDist();
                }
                break;
            case R.id.iv_back_items_avail_store:
                prefrences.setCartFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeliveryAddressFragment()).addToBackStack(null).commit();
                break;
            case R.id.iv_filter_stores_list:
                DialogFragmentFilterStores dialogFragmentFilterStores = new DialogFragmentFilterStores();
                dialogFragmentFilterStores.setTargetFragment(ItemsAvailabilityStoresFragment.this, 1);
                dialogFragmentFilterStores.show(getParentFragmentManager(), "Select store");
                break;
        }
    }


    /*result without select radius and store*/
    private void lowestPrice() {
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
//            jsonObj.put("Authorization", "Bearer " + prefrences.getToken());
//            jsonObj.put("Content-Type", "application/json");
            Log.e("products", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.heighest_availability, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final AvailNotAvailResponse availNotAvailResponse = gson.fromJson(String.valueOf(response), AvailNotAvailResponse.class);
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
                    itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(getContext(), stores_list, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);
//                    itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                    if (stores_list.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("avail_error", error.toString());
                dialog.dismiss();
            }
        }) /*{
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", prefrences.getLanguage());
                return header;
            }
        }*/;
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
                try {
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
                    itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(getContext(), stores_list, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);
//                    itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                    if (stores_list.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }

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
                try {
                    AvailNotAvailResponse availNotAvailResponse = gson.fromJson(String.valueOf(response), AvailNotAvailResponse.class);
                    stores_list.clear();
                    for (int i = 0; i < availNotAvailResponse.getData().size(); i++) {
                        stores_list.add(availNotAvailResponse.getData().get(i));
                    }
                    itemsAvailabilityStoresAdapter = new ItemsAvailabilityStoresAdapter(getContext(), stores_list, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresAdapter);
//                    itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                    if (stores_list.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }

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

    /*result on select radius*/
    private void lowestPriceAsRadius() {
        int selected_radius = prefrences.getRadius();
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lang);
            jsonObj.put("radius", selected_radius);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);
            Log.e("products", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.heighest_availability_by_radius, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final AvailNotAvailRadiusResponse availNotAvailRadiusResponse = gson.fromJson(String.valueOf(response), AvailNotAvailRadiusResponse.class);
                    stores_list_radius.clear();
                    for (int i = 0; i < availNotAvailRadiusResponse.getData().size(); i++) {
                        stores_list_radius.add(availNotAvailRadiusResponse.getData().get(i));
                    }
                    Collections.sort(stores_list_radius, new Comparator<AvailNotAvailRadiusResponse.Datum>() {
                        @Override
                        public int compare(AvailNotAvailRadiusResponse.Datum o1, AvailNotAvailRadiusResponse.Datum o2) {
                            return Integer.valueOf(o2.getAvailable().size()).compareTo(o1.getAvailable().size());
                        }
                    });
                    itemsAvailabilityStoresRadiusAdapter = new ItemsAvailabilityStoresRadiusAdapter(getContext(), stores_list_radius, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresRadiusAdapter);
//                    itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                    if (stores_list_radius.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("radius_error", error.toString());
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

    private void highestAvailabilityAsRadius(int selected_radius) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lang);
            jsonObj.put("radius", selected_radius);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);
            Log.e("products", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.heighest_availability_by_radius, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final AvailNotAvailRadiusResponse availNotAvailRadiusResponse = gson.fromJson(String.valueOf(response), AvailNotAvailRadiusResponse.class);
                    stores_list_radius.clear();
                    for (int i = 0; i < availNotAvailRadiusResponse.getData().size(); i++) {
                        stores_list_radius.add(availNotAvailRadiusResponse.getData().get(i));
                    }
                    Collections.sort(stores_list_radius, new Comparator<AvailNotAvailRadiusResponse.Datum>() {
                        @Override
                        public int compare(AvailNotAvailRadiusResponse.Datum o1, AvailNotAvailRadiusResponse.Datum o2) {
                            return Integer.valueOf(o2.getAvailable().size()).compareTo(o1.getAvailable().size());
                        }
                    });
                    itemsAvailabilityStoresRadiusAdapter = new ItemsAvailabilityStoresRadiusAdapter(getContext(), stores_list_radius, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresRadiusAdapter);
//                    itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                    if (stores_list_radius.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
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

    private void nearestDistanceAsRadius(int selected_radius) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lang);
            jsonObj.put("radius", selected_radius);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);
            Log.e("products", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.heighest_availability_by_radius, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final AvailNotAvailRadiusResponse availNotAvailRadiusResponse = gson.fromJson(String.valueOf(response), AvailNotAvailRadiusResponse.class);
                    stores_list_radius.clear();
                    for (int i = 0; i < availNotAvailRadiusResponse.getData().size(); i++) {
                        stores_list_radius.add(availNotAvailRadiusResponse.getData().get(i));
                    }
                    itemsAvailabilityStoresRadiusAdapter = new ItemsAvailabilityStoresRadiusAdapter(getContext(), stores_list_radius, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilityStoresRadiusAdapter);
//                    itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                    if (stores_list_radius.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
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

    /*result on select radius and store*/
    private void selectedStoreLowestPrice() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lang);
            jsonObj.put("radius", prefrences.getRadius());
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);

            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < SelectedStoreFragment.store_id_list.size(); i++) {
                jsonArray1.put(SelectedStoreFragment.store_id_list.get(i));
            }
            jsonObj.put("storeList", jsonArray1);

            Log.e("storeList", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.radious_stores_list, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final SelectedStoreProductsResponse selectedStoreProductsResponse = gson.fromJson(String.valueOf(response), SelectedStoreProductsResponse.class);
                    selected_stores_list.clear();
                    for (int i = 0; i < selectedStoreProductsResponse.getData().size(); i++) {
                        selected_stores_list.add(selectedStoreProductsResponse.getData().get(i));
                    }
                    Collections.sort(selected_stores_list, new Comparator<SelectedStoreProductsResponse.Datum>() {
                        @Override
                        public int compare(SelectedStoreProductsResponse.Datum o1, SelectedStoreProductsResponse.Datum o2) {
                            return Integer.valueOf(o2.getAvailable().size()).compareTo(o1.getAvailable().size());
                        }
                    });
                    itemsAvailabilitySelectedStoresAdapter = new ItemsAvailabilitySelectedStoresAdapter(getContext(), selected_stores_list, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilitySelectedStoresAdapter);
                    if (selected_stores_list.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
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

    private void selectedStoreHighestAvailability() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lang);
            jsonObj.put("radius", prefrences.getRadius());
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);

            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < SelectedStoreFragment.store_id_list.size(); i++) {
                jsonArray1.put(SelectedStoreFragment.store_id_list.get(i));
            }
            jsonObj.put("storeList", jsonArray1);

            Log.e("storeList", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.radious_stores_list, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final SelectedStoreProductsResponse selectedStoreProductsResponse = gson.fromJson(String.valueOf(response), SelectedStoreProductsResponse.class);
                    selected_stores_list.clear();
                    for (int i = 0; i < selectedStoreProductsResponse.getData().size(); i++) {
                        selected_stores_list.add(selectedStoreProductsResponse.getData().get(i));
                    }
                    Collections.sort(selected_stores_list, new Comparator<SelectedStoreProductsResponse.Datum>() {
                        @Override
                        public int compare(SelectedStoreProductsResponse.Datum o1, SelectedStoreProductsResponse.Datum o2) {
                            return Integer.valueOf(o2.getAvailable().size()).compareTo(o1.getAvailable().size());
                        }
                    });
                    itemsAvailabilitySelectedStoresAdapter = new ItemsAvailabilitySelectedStoresAdapter(getContext(), selected_stores_list, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilitySelectedStoresAdapter);
//                    itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                    if (selected_stores_list.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
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

    private void selectedStoreNearestDist() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("latitude", lat);
            jsonObj.put("longitude", lang);
            jsonObj.put("radius", prefrences.getRadius());
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartList.size(); i++) {
                jsonArray.put(cartList.get(i));
            }
            jsonObj.put("products", jsonArray);

            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < SelectedStoreFragment.store_id_list.size(); i++) {
                jsonArray1.put(SelectedStoreFragment.store_id_list.get(i));
            }
            jsonObj.put("storeList", jsonArray1);

            Log.e("storeList", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.radious_stores_list, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final SelectedStoreProductsResponse selectedStoreProductsResponse = gson.fromJson(String.valueOf(response), SelectedStoreProductsResponse.class);
                    selected_stores_list.clear();
                    for (int i = 0; i < selectedStoreProductsResponse.getData().size(); i++) {
                        selected_stores_list.add(selectedStoreProductsResponse.getData().get(i));
                    }
                    itemsAvailabilitySelectedStoresAdapter = new ItemsAvailabilitySelectedStoresAdapter(getContext(), selected_stores_list, lat, lang);
                    recyclerViewAvailableItemsStore.setAdapter(itemsAvailabilitySelectedStoresAdapter);
//                    itemsAvailabilityStoresAdapter.notifyDataSetChanged();
                    if (selected_stores_list.size() == 0) {
                        tvNoStoreFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNoStoreFound.setVisibility(View.INVISIBLE);
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
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

    @Override
    public void onDismissDialogFragment() {
        selectedStoreLowestPrice();
    }
}
