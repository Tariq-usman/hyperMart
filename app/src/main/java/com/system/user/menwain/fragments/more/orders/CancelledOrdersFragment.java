package com.system.user.menwain.fragments.more.orders;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrdersCancelledAdapter;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.more.orders.CancelledOrdersResponse;
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

public class CancelledOrdersFragment extends Fragment {

    private List<CancelledOrdersResponse.Allorders.Datum> canceled_orders_list = new ArrayList<>();
    private RecyclerView recyclerViewOrdersDelivered;
    private OrdersCancelledAdapter ordersCancelledAdapter;
    private ProgressDialog progressDialog;
    private Preferences prefrences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_cancelled, container, false);
        customProgressDialog(getContext());
        prefrences = new Preferences(getContext());
        getCancelledOrdersData();
        recyclerViewOrdersDelivered = view.findViewById(R.id.recycler_view_orders_cancelled);
        recyclerViewOrdersDelivered.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersCancelledAdapter = new OrdersCancelledAdapter(getContext(),canceled_orders_list);
        recyclerViewOrdersDelivered.setAdapter(ordersCancelledAdapter);
        return view;
    }

    private void getCancelledOrdersData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_cancelled_orders_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CancelledOrdersResponse cancelledOrdersResponse = gson.fromJson(response, CancelledOrdersResponse.class);
                canceled_orders_list.clear();
                for (int i = 0; i < cancelledOrdersResponse.getAllorders().getData().size(); i++) {
                    canceled_orders_list.add(cancelledOrdersResponse.getAllorders().getData().get(i));
                }
                ordersCancelledAdapter.notifyDataSetChanged();
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
