package com.system.user.menwain.fragments.cart.dialog_fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.CartFragment;
import com.system.user.menwain.fragments.more.orders.OrdersFragment;
import com.system.user.menwain.fragments.my_list.AllListsFragment;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;
import com.system.user.menwain.utils.URLs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DialogFragmentSaveList extends DialogFragment implements View.OnClickListener {
    private EditText etListName;
    private ImageView mCart, mFavourite, mHome, mCategory, mMore,mCloseBtn, mBackBtnPay;
    private TextView mConfirm, tvHome, tvCategory, tvCart, tvMore, tvFavourite;
    private List<Integer> list = new ArrayList<>();

    private int mYear, mMonth, mDay;
    private Preferences prefrences;
    private int pay_status;
    private ProgressDialog progressDialog;
    List<AvailNotAvailResponse.Datum.Available> avail_items_list = ItemsAvailabilityStoresAdapter.available_list;
    CartViewModel cartViewModel;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_dialog_purchasing_method, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog(getContext());
        prefrences = new Preferences(getContext());

        cartViewModel = ViewModelProviders.of(DialogFragmentSaveList.this).get(CartViewModel.class);
        mConfirm = view.findViewById(R.id.confirm_btn_purchasing_method);
        etListName = view.findViewById(R.id.tv_date_in_store_purchase);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        mBackBtnPay = getActivity().findViewById(R.id.iv_back);
        mBackBtnPay.setVisibility(View.VISIBLE);

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


        for (int i = 0; i<avail_items_list.size();i++){
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
            pay_status = prefrences.getPayRBtnStatus();
            placeOrderAndAddToWishList();
            if (pay_status == 5) {
                prefrences.setCartFragStatus(0);
                prefrences.setBottomNavStatus(4);
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#004040"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#004040"));
                mFavourite.setImageResource(R.drawable.ic_likeblue);
                tvFavourite.setTextColor(Color.parseColor("#00c1bd"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#004040"));
                mMore.setImageResource(R.drawable.ic_morewhite);
                tvMore.setTextColor(Color.parseColor("#004040"));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).commit();
                mBackBtnPay.setVisibility(View.INVISIBLE);
            } else {
                prefrences.setCartFragStatus(0);
                prefrences.setBottomNavStatus(5);
                prefrences.setMorFragStatus(2);
                prefrences.setMoreOrdersFragStatus(1);
                mHome.setImageResource(R.drawable.ic_housewhite);
                tvHome.setTextColor(Color.parseColor("#004040"));
                mCategory.setImageResource(R.drawable.ic_searchwhite);
                tvCategory.setTextColor(Color.parseColor("#004040"));
                mFavourite.setImageResource(R.drawable.ic_likewhite);
                tvFavourite.setTextColor(Color.parseColor("#004040"));
                mCart.setImageResource(R.drawable.ic_cart_white);
                tvCart.setTextColor(Color.parseColor("#004040"));
                mMore.setImageResource(R.drawable.ic_moreblue);
                tvMore.setTextColor(Color.parseColor("#00c1bd"));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).commit();
                mBackBtnPay.setVisibility(View.INVISIBLE);
            }
            dismiss();

        } else if (id == R.id.close_back_view) {
            prefrences.setCartFragStatus(0);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).commit();
            mBackBtnPay.setVisibility(View.INVISIBLE);
            dismiss();
        }

    }
    private void placeOrderAndAddToWishList() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("total_price", prefrences.getTotalPrice());
            jsonObj.put("discount", 0);
            jsonObj.put("store_id", prefrences.getStoreId());
            jsonObj.put("slot_id",prefrences.getTimeSlotId());
            jsonObj.put("address_id", prefrences.getDeliveryAddressId());
            jsonObj.put("promotion_id", 0);
            jsonObj.put("shipping_method_id", prefrences.getPayRBtnStatus());
            jsonObj.put("shipping_cost", prefrences.getShippingCost());
            jsonObj.put("secret_code", 0);
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
            String formattedDate = df.format(c);
            jsonObj.put("date_time", formattedDate);
            jsonObj.put("delivery_man_id", 0);
            jsonObj.put("preferred_delivery_date",prefrences.getDateTime());
            jsonObj.put("order_status", "pending");
            jsonObj.put("order_dispatch_id", 0);
            jsonObj.put("payment_method_id", prefrences.getPaymentStatus());
            jsonObj.put("wishlist_name", etListName.getText().toString().trim());
            jsonObj.put("other", "next");

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < avail_items_list.size(); i++) {
                JSONObject object = new JSONObject();
                try {

                    object.put("price",avail_items_list.get(i).getAvgPrice());
                    object.put("quantity",avail_items_list.get(i).getId());
                    object.put("product_id",avail_items_list.get(i).getId());
                    object.put("discount",avail_items_list.get(i).getId());
                    jsonArray.put(object);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            jsonObj.put("prodct", jsonArray);
            Log.e("json",jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.place_order_add_wish_list, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cartViewModel.deleteAllCartRecords();
                Log.e("",response.toString());
//                Toast.makeText(getActivity(), "Resopnse", Toast.LENGTH_SHORT).show();
               /* if (pay_status == 5) {
                    prefrences.setCartFragStatus(0);
                    prefrences.setBottomNavStatus(4);
                    mHome.setImageResource(R.drawable.ic_housewhite);
                    tvHome.setTextColor(Color.parseColor("#004040"));
                    mCategory.setImageResource(R.drawable.ic_searchwhite);
                    tvCategory.setTextColor(Color.parseColor("#004040"));
                    mFavourite.setImageResource(R.drawable.ic_likeblue);
                    tvFavourite.setTextColor(Color.parseColor("#00c1bd"));
                    mCart.setImageResource(R.drawable.ic_cart_white);
                    tvCart.setTextColor(Color.parseColor("#004040"));
                    mMore.setImageResource(R.drawable.ic_morewhite);
                    tvMore.setTextColor(Color.parseColor("#004040"));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AllListsFragment()).commit();
                    mBackBtnPay.setVisibility(View.INVISIBLE);
                } else {
                    prefrences.setCartFragStatus(0);
                    prefrences.setBottomNavStatus(5);
                    prefrences.setMorFragStatus(2);
                    prefrences.setMoreOrdersFragStatus(1);
                    mHome.setImageResource(R.drawable.ic_housewhite);
                    tvHome.setTextColor(Color.parseColor("#004040"));
                    mCategory.setImageResource(R.drawable.ic_searchwhite);
                    tvCategory.setTextColor(Color.parseColor("#004040"));
                    mFavourite.setImageResource(R.drawable.ic_likewhite);
                    tvFavourite.setTextColor(Color.parseColor("#004040"));
                    mCart.setImageResource(R.drawable.ic_cart_white);
                    tvCart.setTextColor(Color.parseColor("#004040"));
                    mMore.setImageResource(R.drawable.ic_moreblue);
                    tvMore.setTextColor(Color.parseColor("#00c1bd"));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).commit();
                    mBackBtnPay.setVisibility(View.INVISIBLE);
                }*/
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("order_error",error.networkResponse.data.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap = new HashMap<>();
                headerMap.put("Authorization","Bearer " + prefrences.getToken());
                return headerMap;
            }
        };

        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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


   /* private void pickDate() {
        Calendar calendar = Calendar.getInstance();
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvDateInStorePurchase.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mYear);
        datePickerDialog.setTitle("Select Date..");
        datePickerDialog.show();
    }*/
}
