package com.system.user.menwain.fragments.more.orders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrderDetailsAdapter;
import com.system.user.menwain.responses.more.orders.OrderDetailsResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailsFragment extends Fragment {
    // private int[] products = {R.drawable.productpic, R.drawable.productdisplay, R.drawable.productpic, R.drawable.productdisplay, R.drawable.productpic, R.drawable.productdisplay};

    private List<OrderDetailsResponse.Datum> details_list = new ArrayList<OrderDetailsResponse.Datum>();
    RecyclerView recyclerViewFavourite;
    OrderDetailsAdapter orderDetailsAdapter;
    private ImageView mBack, ivStoreImage;
    private TextView tvStoreName, tvOrderNo, tvPrice, tvDate, tvTime, tvStatus, tvOrderCode;
    private Prefrences prefrences;
    private ProgressDialog progressDialog;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        prefrences = new Prefrences(getContext());
        customProgressDialog(getContext());
        bundle = this.getArguments();

        mBack = getActivity().findViewById(R.id.iv_back);
        mBack.setVisibility(View.VISIBLE);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreOrdersFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OrdersFragment()).addToBackStack(null).commit();
                mBack.setVisibility(View.INVISIBLE);
            }
        });

        ivStoreImage = view.findViewById(R.id.mart_logo_view_order_details);
        tvStoreName = view.findViewById(R.id.tv_store_order_details);
        tvOrderNo = view.findViewById(R.id.tv_order_no_order_details);
        tvPrice = view.findViewById(R.id.tv_total_amount_order_details);
        tvDate = view.findViewById(R.id.tv_date_order_details);
        tvTime = view.findViewById(R.id.tv_time_order_details);
        tvStatus = view.findViewById(R.id.tv_status_order_details);
        tvOrderCode = view.findViewById(R.id.tv_order_code_order_details);
        String order_id = String.valueOf(bundle.getInt("order_id"));
        if (order_id != null || !order_id.isEmpty()) {
            getOrdersDetails(order_id);
        } else {
            Toast.makeText(getContext(), "No order id..", Toast.LENGTH_SHORT).show();
        }

        recyclerViewFavourite = view.findViewById(R.id.recycler_view_order_details);
        recyclerViewFavourite.setHasFixedSize(true);
        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(getContext()));
        orderDetailsAdapter = new OrderDetailsAdapter(getContext(), details_list);
        recyclerViewFavourite.setAdapter(orderDetailsAdapter);
        return view;
    }

    private void getOrdersDetails(String order_id) {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_orders_details_url + order_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                OrderDetailsResponse detailsResponse = gson.fromJson(response, OrderDetailsResponse.class);

                Glide.with(ivStoreImage.getContext()).load(detailsResponse.getData().get(0).getStore().getImage()).into(ivStoreImage);
                tvStoreName.setText(detailsResponse.getData().get(0).getStore().getName());
                tvOrderNo.setText(detailsResponse.getData().get(0).getId().toString());
                tvPrice.setText(detailsResponse.getData().get(0).getTotalPrice().toString());
                String date_time = detailsResponse.getData().get(0).getDateTime();
                String[] split_date_time = date_time.split(" ");
                tvDate.setText(split_date_time[0]);
                tvTime.setText(split_date_time[1]);
                tvStatus.setText(detailsResponse.getData().get(0).getOrderStatus());
                details_list.clear();
                for (int i = 0; i < detailsResponse.getData().size(); i++) {
                    details_list.add(detailsResponse.getData().get(i));
                }
                orderDetailsAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("allO_error", error.toString());
                progressDialog.dismiss();
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
