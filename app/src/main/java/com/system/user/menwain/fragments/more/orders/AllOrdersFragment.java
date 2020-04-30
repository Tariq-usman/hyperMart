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
import com.system.user.menwain.adapters.more_adapters.orders_adapters.AllOrdersAdapter;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.more.orders.AllOrdersResponse;
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

public class AllOrdersFragment extends Fragment {

    private List<AllOrdersResponse.Allorders.Datum> all_orders_list = new ArrayList<>();
    private RecyclerView recyclerViewOrders;
    private AllOrdersAdapter allOrdersAdapter;
    private Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_orders, container, false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());

        getAllOrdersData();
        recyclerViewOrders = view.findViewById(R.id.recycler_view_orders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        allOrdersAdapter = new AllOrdersAdapter(getContext(), all_orders_list);
        recyclerViewOrders.setAdapter(allOrdersAdapter);

        return view;
    }

    private void getAllOrdersData() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_all_orders_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AllOrdersResponse allOrdersResponse = gson.fromJson(response, AllOrdersResponse.class);
                all_orders_list.clear();
                for (int i = 0; i < allOrdersResponse.getAllorders().getData().size(); i++) {
                    all_orders_list.add(allOrdersResponse.getAllorders().getData().get(i));
                }
                allOrdersAdapter.notifyDataSetChanged();
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
