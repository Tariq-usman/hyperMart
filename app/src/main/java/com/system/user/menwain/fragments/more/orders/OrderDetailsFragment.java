package com.system.user.menwain.fragments.more.orders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentTimeSlots;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrderDetailsAdapter;
import com.system.user.menwain.responses.more.orders.OrderDetailsResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderDetailsFragment extends Fragment {
    private List<OrderDetailsResponse.Data.Products> details_list = new ArrayList<OrderDetailsResponse.Data.Products>();
    RecyclerView recyclerViewFavourite;
    OrderDetailsAdapter orderDetailsAdapter;
    private ImageView mBack, mDirection, ivStoreImage;
    private TextView tvStoreName, tvStoreAddress, tvOrderNo, tvPrice, tvDate, tvTime, tvStatus, tvOrderCode,mConfirmBtn;
    private Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    Bundle bundle;
    private int order_id;
    private String order_status;
    public static String store_name = null;
    OrderDetailsResponse detailsResponse;
    public static List<Integer> orders_reorder_list = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());
        order_id = prefrences.getMoreOrderId();
        getOrdersDetails(String.valueOf(order_id));


        mBack = view.findViewById(R.id.iv_back_order_details);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreOrdersFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).addToBackStack(null).commit();
            }
        });
        mConfirmBtn = view.findViewById(R.id.confirm_btn_order_details);
        if (prefrences.getMoreOrdersStatus()==3 || prefrences.getMoreOrdersStatus()==4){
            mConfirmBtn.setVisibility(View.VISIBLE);
        }else {
            mConfirmBtn.setVisibility(View.GONE);
        }
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefrences.setOrderStatus(4);
                prefrences.setWishListName("");
                prefrences.setTotalAmount(detailsResponse.getData().getTotalPrice());
                prefrences.setStoreId(detailsResponse.getData().getStoreId());
                prefrences.setDeliveryAddressId(detailsResponse.getData().getAddressId());
                prefrences.setShippingId(detailsResponse.getData().getShippingMethodId());
                prefrences.setShippingCost(detailsResponse.getData().getShippingCost());
                prefrences.setOrderId(detailsResponse.getData().getId());
                prefrences.setPaymentStatus(detailsResponse.getData().getPaymentMethodId());
                orders_reorder_list.clear();
                for (int i = 0; i < detailsResponse.getData().getProductss().size(); i++) {
                    orders_reorder_list.add(detailsResponse.getData().getProductss().get(i).getProductId());
                }
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DialogFragmentTimeSlots()).addToBackStack(null).commit();
            }
        });

        ivStoreImage = view.findViewById(R.id.mart_logo_view_order_details);
        tvStoreName = view.findViewById(R.id.tv_store_order_details);
        tvStoreAddress = view.findViewById(R.id.tv_store_address_order_details);
        tvOrderNo = view.findViewById(R.id.tv_order_no_order_details);
        tvPrice = view.findViewById(R.id.tv_total_amount_order_details);
        tvDate = view.findViewById(R.id.tv_date_order_details);
        tvTime = view.findViewById(R.id.tv_time_order_details);
        tvStatus = view.findViewById(R.id.tv_status_order_details);
        tvOrderCode = view.findViewById(R.id.tv_order_code_order_details);


        mDirection = view.findViewById(R.id.iv_nav_order_details);
        mDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = tvStoreAddress.getText().toString().trim();
//                String geoUri = "http://maps.google.com/maps?q=loc:" + detailsResponse.getData().getStore().getLatitude() + "," + detailsResponse.getData().getStore().getLongitude();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + "&daddr=" + address));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        recyclerViewFavourite = view.findViewById(R.id.recycler_view_order_details);
        recyclerViewFavourite.setHasFixedSize(true);
        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getContext()));
        orderDetailsAdapter = new OrderDetailsAdapter(getContext(), details_list, prefrences.getMoreOrdersStatusName());
        recyclerViewFavourite.setAdapter(orderDetailsAdapter);
        return view;
    }

    private void getOrdersDetails(String order_id) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_orders_details_url + order_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    detailsResponse = gson.fromJson(response, OrderDetailsResponse.class);
                    findAddress(detailsResponse.getData().getStore().getLatitude(), detailsResponse.getData().getStore().getLongitude());
                    Glide.with(ivStoreImage.getContext()).load(detailsResponse.getData().getStore().getImage()).into(ivStoreImage);
                    store_name = detailsResponse.getData().getStore().getName();
                    tvStoreName.setText(detailsResponse.getData().getStore().getName());
                    tvOrderNo.setText(detailsResponse.getData().getId().toString());
                    tvPrice.setText(detailsResponse.getData().getTotalPrice().toString());
                    String date_time = detailsResponse.getData().getDateTime();
                    String[] split_date_time = date_time.split(" ");
                    tvDate.setText(split_date_time[0]);
                    tvTime.setText(split_date_time[1]);
                    order_status = detailsResponse.getData().getOrderStatus();
                    tvStatus.setText(order_status);
                    tvOrderCode.setText(detailsResponse.getData().getSecretCode().toString());
                    details_list.clear();
                    for (int i = 0; i < detailsResponse.getData().getProductss().size(); i++) {
                        details_list.add(detailsResponse.getData().getProductss().get(i));
                    }
                    orderDetailsAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("allO_error", error.toString());
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
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                headerMap.put("Accept","application/json");
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

    public void findAddress(String latitude, String longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.ENGLISH);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.isEmpty()) {
                addresses = geocoder.getFromLocation(33.684422, 73.047882, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                tvStoreAddress.setText("( "+city+" )");
            } else {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                tvStoreAddress.setText("( "+city+" )");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
