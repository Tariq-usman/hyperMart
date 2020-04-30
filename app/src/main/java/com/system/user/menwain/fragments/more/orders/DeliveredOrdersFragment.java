package com.system.user.menwain.fragments.more.orders;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrdersDeliveredAdapter;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.more.orders.DeleveredOrdersResponse;
import com.system.user.menwain.utils.URLs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveredOrdersFragment extends Fragment {

    private List<DeleveredOrdersResponse.Allorders.Datum> delivered_orders_list = new ArrayList<DeleveredOrdersResponse.Allorders.Datum>();
    private RecyclerView recyclerViewOrdersDelivered;
    private OrdersDeliveredAdapter ordersDeliveredAdapter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Preferences prefrences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_delivered,container,false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());
        getDeliveredOrdersData();

        recyclerViewOrdersDelivered = view.findViewById(R.id.recycler_view_orders_delivered);
        recyclerViewOrdersDelivered.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersDeliveredAdapter = new OrdersDeliveredAdapter(getContext(),delivered_orders_list);
        recyclerViewOrdersDelivered.setAdapter(ordersDeliveredAdapter);
        return view;
    }
    private void getDeliveredOrdersData() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_delivered_orders_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DeleveredOrdersResponse deleveredOrdersResponse = gson.fromJson(response, DeleveredOrdersResponse.class);
                delivered_orders_list.clear();
                for (int i = 0; i < deleveredOrdersResponse.getAllorders().getData().size(); i++) {
                    delivered_orders_list.add(deleveredOrdersResponse.getAllorders().getData().get(i));
                }
                ordersDeliveredAdapter.notifyDataSetChanged();
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

}
