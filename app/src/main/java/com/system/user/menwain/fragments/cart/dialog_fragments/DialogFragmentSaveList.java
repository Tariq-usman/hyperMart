package com.system.user.menwain.fragments.cart.dialog_fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.cart_adapters.AvailableItemsListAdapter;
import com.system.user.menwain.adapters.cart_adapters.AvailableItemsListRadiusAdapter;
import com.system.user.menwain.adapters.cart_adapters.AvailableItemsListSelectedStoreRadiusAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilitySelectedStoresAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresRadiusAdapter;
import com.system.user.menwain.fragments.cart.OrderSuccessfulFragment;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.CartFragment;
import com.system.user.menwain.responses.cart.AvailNotAvailRadiusResponse;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;
import com.system.user.menwain.responses.cart.SelectedStoreProductsResponse;
import com.system.user.menwain.utils.URLs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DialogFragmentSaveList extends DialogFragment implements View.OnClickListener {
    private EditText etListName;
    private ImageView mCart, mFavourite, mHome, mCategory, mMore, mCloseBtn, mBackBtnPay;
    private TextView mConfirm, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    private List<Integer> list = new ArrayList<>();

    private int mYear, mMonth, mDay;
    private Preferences preferences;
    private int pay_status;
    int order_no;
    int max = 100000;
    int min = 10000;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private List<AvailNotAvailResponse.Datum.Available> avail_items_list = ItemsAvailabilityStoresAdapter.available_list;
    private List<AvailNotAvailRadiusResponse.Datum.Available> avail_items_list_radius = ItemsAvailabilityStoresRadiusAdapter.available_list;
    private List<SelectedStoreProductsResponse.Datum.Available> selected_store_avail_items_list_radius = ItemsAvailabilitySelectedStoresAdapter.available_list;
    private List<Integer> quantity_list = AvailableItemsListAdapter.quantity_list;
    private List<Integer> amount_lit = AvailableItemsListAdapter.amount_list;
    private List<Integer> quantity_list_radius = AvailableItemsListRadiusAdapter.quantity_list;
    private List<Integer> amount_lit_radius = AvailableItemsListRadiusAdapter.amount_list;
    private List<Integer> selected_store_quantity_list_radius = AvailableItemsListSelectedStoreRadiusAdapter.quantity_list;
    private List<Integer> selected_store_amount_lit_radius = AvailableItemsListSelectedStoreRadiusAdapter.amount_list;
    private CartViewModel cartViewModel;
    private View view;
    private OrderSuccessfulFragment fragment;
    private FragmentTransaction transaction;
    private Bundle bundle;
    private SimpleDateFormat dateFormat;
    String date_time;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog_save_list, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog(getContext());
        bundle = new Bundle();
        Date current_time = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        date_time = dateFormat.format(current_time);

        Random r = new Random();
        order_no = r.nextInt(max - min) + min;

        preferences = new Preferences(getContext());
        fragment = new OrderSuccessfulFragment();
        transaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();

        cartViewModel = ViewModelProviders.of(DialogFragmentSaveList.this).get(CartViewModel.class);
        mConfirm = view.findViewById(R.id.confirm_btn_purchasing_method);
        etListName = view.findViewById(R.id.tv_date_in_store_purchase);
        mCloseBtn = view.findViewById(R.id.close_back_view);

        mHome = getActivity().findViewById(R.id.home_view);
        tvHome = getActivity().findViewById(R.id.tv_home_view);
        mCategory = getActivity().findViewById(R.id.category_view);
        tvCategory = getActivity().findViewById(R.id.tv_category_view);

        mCart = getActivity().findViewById(R.id.cart);
        tvCart = getActivity().findViewById(R.id.tv_cart);

        mFavourite = getActivity().findViewById(R.id.favourite_view);
        tvFavourite = getActivity().findViewById(R.id.tv_favourite_view);

        mMore = getActivity().findViewById(R.id.more);
        tvMore = getActivity().findViewById(R.id.tv_more);

        mCloseBtn.setOnClickListener(this);
        mConfirm.setOnClickListener(this);


        for (int i = 0; i < avail_items_list.size(); i++) {
            list.add(avail_items_list.get(i).getAvgPrice());
            list.add(2);
            list.add(avail_items_list.get(i).getId());
            list.add(2);
        }
        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.confirm_btn_purchasing_method) {
            pay_status = preferences.getPayRBtnStatus();
            placeOrderAndAddToWishList();
            dismiss();

        } else if (id == R.id.close_back_view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).commit();
            mBackBtnPay.setVisibility(View.INVISIBLE);
            dismiss();
        }

    }

    private void placeOrderAndAddToWishList() {
        Log.e("list_size", avail_items_list.size() + "");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("total_price", preferences.getTotalAmount() + preferences.getShippingCost());
            jsonObj.put("discount", 0);
            jsonObj.put("store_id", preferences.getStoreId());
            jsonObj.put("slot_id", preferences.getTimeSlotId());
            jsonObj.put("address_id", preferences.getDeliveryAddressId());
            jsonObj.put("promotion_id", 0);
            jsonObj.put("shipping_method_id", preferences.getPayRBtnStatus());
            jsonObj.put("shipping_cost", preferences.getShippingCost());
            jsonObj.put("secret_code", order_no);
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
            String formattedDate = df.format(c);
            jsonObj.put("date_time", formattedDate);
            jsonObj.put("delivery_man_id", 0);
            jsonObj.put("preferred_delivery_date", preferences.getDateTime());
            jsonObj.put("order_status", "pending");
            jsonObj.put("order_dispatch_id", 0);
            jsonObj.put("payment_method_id", preferences.getPaymentStatus());
            if (etListName.getText().toString().trim().isEmpty() || etListName.getText().toString().trim() == "") {
                jsonObj.put("wishlist_name", date_time);
            } else {
                jsonObj.put("wishlist_name", etListName.getText().toString().trim());
            }
            jsonObj.put("other", "next");

            JSONArray jsonArray = new JSONArray();
            if (avail_items_list.size() > 0) {
                for (int i = 0; i < avail_items_list.size(); i++) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("price", amount_lit.get(i));
                        object.put("quantity", quantity_list.get(i));
                        object.put("product_id", avail_items_list.get(i).getId());
                        object.put("discount", 0);
                        jsonArray.put(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (avail_items_list_radius.size()>0){
                for (int i = 0; i < avail_items_list_radius.size(); i++) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("price", amount_lit_radius.get(i));
                        object.put("quantity", quantity_list_radius.get(i));
                        object.put("product_id", avail_items_list_radius.get(i).getId());
                        object.put("discount", 0);
                        jsonArray.put(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else {
                for (int i = 0; i < selected_store_avail_items_list_radius.size(); i++) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("price", selected_store_amount_lit_radius.get(i));
                        object.put("quantity", selected_store_quantity_list_radius.get(i));
                        object.put("product_id", selected_store_avail_items_list_radius.get(i).getId());
                        object.put("discount", 0);
                        jsonArray.put(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            jsonObj.put("prodct", jsonArray);
            Log.e("json", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.place_order_add_wish_list, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                cartViewModel.deleteAllCartRecords();
                preferences.setCartFragStatus(0);
                bundle.putString("order_no", String.valueOf(order_no));
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).commit();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("order_error", error.networkResponse.data.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + preferences.getToken());
                return headerMap;
            }
        };

        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
