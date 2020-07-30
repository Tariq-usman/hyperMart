package com.system.user.menwain.fragments.more.orders;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.LoginActivity;
import com.system.user.menwain.adapters.more_adapters.orders_adapters.OrdersCancelledAdapter;
import com.system.user.menwain.others.PaginationListenerLinearLayoutManager;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.more.orders.CancelledOrdersResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.system.user.menwain.others.PaginationListenerGridLayoutManager.PAGE_START;

public class CancelledOrdersFragment extends Fragment {

    private List<CancelledOrdersResponse.Allorders.Datum> canceled_orders_list = new ArrayList<>();
    private RecyclerView recyclerViewOrdersCancelled;
    private LinearLayoutManager linearLayoutManager;
    private OrdersCancelledAdapter ordersCancelledAdapter;
    private Dialog dialog;
    private Preferences prefrences;
    private TextView tvMessage;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int itemCount = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_cancelled, container, false);
        dialog = Utils.dialog(getContext());
        prefrences = new Preferences(getContext());
        getCancelledOrdersData();
        tvMessage = view.findViewById(R.id.tv_message_cancelled_orders);
        tvMessage.setVisibility(View.INVISIBLE);
        recyclerViewOrdersCancelled = view.findViewById(R.id.recycler_view_orders_cancelled);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewOrdersCancelled.setLayoutManager(linearLayoutManager);
        ordersCancelledAdapter = new OrdersCancelledAdapter(getContext(), canceled_orders_list);
        recyclerViewOrdersCancelled.setAdapter(ordersCancelledAdapter);
        recyclerViewOrdersCancelled.addOnScrollListener(new PaginationListenerLinearLayoutManager(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getCancelledOrdersData();
            }

            @Override
            protected boolean isLastPage() {
                return isLastPage;
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }
        });
        return view;
    }

    private void getCancelledOrdersData() {
        dialog.show();
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
                if (canceled_orders_list.size() == 0) {
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText(getString(R.string.no_orders_yet));
                } else {
                    tvMessage.setVisibility(View.INVISIBLE);
                }
                dialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("allO_error", error.toString());
                if (error != null && error.networkResponse != null && error.networkResponse.data != null) {
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText(getString(R.string.authentication_error));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent logInIntnet = new Intent(getContext(), LoginActivity.class);
                            logInIntnet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().startActivity(logInIntnet);
                        }
                    }, 1000);
                } else {
                    try {
                        if (error instanceof TimeoutError) {
                            tvMessage.setVisibility(View.VISIBLE);
                            tvMessage.setText(getString(R.string.network_timeout));
                        } else if (error instanceof AuthFailureError) {
                            tvMessage.setVisibility(View.VISIBLE);
                            tvMessage.setText(getString(R.string.authentication_error));
                        } else if (error instanceof ServerError) {
                            tvMessage.setVisibility(View.VISIBLE);
                            tvMessage.setText(getString(R.string.server_error));
                        } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                            tvMessage.setVisibility(View.VISIBLE);
                            tvMessage.setText(getString(R.string.no_network_found));
                        } else {
                        }
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                headerMap.put("Accept", "application/json");
                return headerMap;
            }
        };
        requestQueue.add(request);
    }

}
