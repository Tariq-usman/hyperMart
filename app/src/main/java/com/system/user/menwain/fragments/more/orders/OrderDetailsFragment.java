package com.system.user.menwain.fragments.more.orders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private TextView tvStoreName, tvStoreAddress, tvOrderNo, tvPrice, tvDate, tvTime, tvStatus, tvOrderCode;
    private Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    Bundle bundle;
    private int order_id;
    private String order_status;
    public static String store_name = null;
    OrderDetailsResponse detailsResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());
        bundle = this.getArguments();
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
        orderDetailsAdapter = new OrderDetailsAdapter(getContext(), details_list, order_status);
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
                details_list.clear();
                for (int i = 0; i < detailsResponse.getData().getProductss().size(); i++) {
                    details_list.add(detailsResponse.getData().getProductss().get(i));
                }
                orderDetailsAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("allO_error", error.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                return headerMap;
            }
        };
        requestQueue.add(request);
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
