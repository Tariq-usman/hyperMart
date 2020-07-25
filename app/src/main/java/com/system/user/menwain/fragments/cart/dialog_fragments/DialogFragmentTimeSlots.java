package com.system.user.menwain.fragments.cart.dialog_fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.cart_adapters.DeliveryDatesAdapter;
import com.system.user.menwain.fragments.cart.AddCardFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.DeliveryTimesAdapter;
import com.system.user.menwain.responses.cart.StoreTimeSLotsResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DialogFragmentTimeSlots extends DialogFragment implements View.OnClickListener, RecyclerClickInterface {
    private int mYear;
    private int mMonth;
    private int mDay;
    public static int time_slot_id;
    TextView mConfirm, mTitleView, tvDate, tvDeliveryPickUp;
    ImageView mCloseBtn;
    public static String selected_date, selecte_time, date_time;
    private RecyclerView recyclerViewDate, recyclerView;
    private DeliveryTimesAdapter timesAdapter;
    private DeliveryDatesAdapter datesAdapter;
    private List<StoreTimeSLotsResponse.List> delivery_times_list = new ArrayList<StoreTimeSLotsResponse.List>();
    private List<StoreTimeSLotsResponse.List> delivery_dates_list = new ArrayList<StoreTimeSLotsResponse.List>();
    Preferences prefrences;
    private Dialog dialog;
    String current_date;
    long daysDiff;
    private StoreTimeSLotsResponse timeSLotsResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_time_slots, container, false);
        try {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefrences = new Preferences(getContext());
        dialog = Utils.dialog(getContext());
        tvDeliveryPickUp = view.findViewById(R.id.tv_delivery_pickup);
        mConfirm = view.findViewById(R.id.confirm_btn_delivery_time);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        recyclerViewDate = view.findViewById(R.id.recycler_view_delivery_date);
        recyclerView = view.findViewById(R.id.recycler_view_delivery_time);

        getStoreTimeSlots(prefrences.getStoreId());

        mConfirm.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        if (prefrences.getPayRBtnStatus() == 2 || prefrences.getPayRBtnStatus() == 4) {
            tvDeliveryPickUp.setText(R.string.time_slot_dialog_title_p);
        }
        recyclerViewDate.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        datesAdapter = new DeliveryDatesAdapter(getContext(), delivery_dates_list, this);
        recyclerViewDate.setAdapter(datesAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        timesAdapter = new DeliveryTimesAdapter(getContext(), delivery_times_list);
        recyclerView.setAdapter(timesAdapter);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn_delivery_time:
                if (delivery_times_list.size() == 0) {
                    Toast.makeText(getContext(), getContext().getString(R.string.no_slots_available), Toast.LENGTH_SHORT).show();
                } else {
                    submitPreferredDeliveryDate();
                }
                break;
            case R.id.close_back_view:
                dismiss();
                break;
        }
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        selected_date = delivery_dates_list.get(position).getDate();
        delivery_times_list.clear();
        for (int i = 0; i < delivery_dates_list.get(position).getSlots().size(); i++) {
            delivery_times_list.add(delivery_dates_list.get(i));
        }
        if (delivery_times_list.size() == 0) {
            Toast.makeText(getContext(), getContext().getString(R.string.no_slots_available), Toast.LENGTH_SHORT).show();
            timesAdapter.notifyDataSetChanged();
        } else {
            timesAdapter.notifyDataSetChanged();
        }
    }

    private void submitPreferredDeliveryDate() {
        date_time = selected_date + " " + selecte_time;
        dialog.show();
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URLs.submit_delivery_date_url + time_slot_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prefrences.setCartFragStatus(4);
                prefrences.setDateTime(date_time);
                prefrences.setTimeSlotId(time_slot_id);

                if (prefrences.getPaymentStatus() == 1) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AddCardFragment()).commit();
                } else {
                    DialogFragmentSaveList dialogFragmentSaveList = new DialogFragmentSaveList();
                    dialogFragmentSaveList.show(getFragmentManager(), "Purchasing Method");
                }
                dismiss();
                // Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("time_error", error.toString());
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
                headerMap.put("Accept", "application/json");
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("slot_id", String.valueOf(time_slot_id));
                map.put("preferred_delivery_date", String.valueOf(selected_date));
                return map;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getStoreTimeSlots(int selectedStoreId) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.post_preffered_date_url + selectedStoreId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    timeSLotsResponse = gson.fromJson(response, StoreTimeSLotsResponse.class);
                    delivery_dates_list.clear();
                    for (int i = 0; i < timeSLotsResponse.getList().size(); i++) {
                        delivery_dates_list.add(timeSLotsResponse.getList().get(i));
                    }
                    datesAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("time_error", error.toString());
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
                headerMap.put("Accept", "application/json");
                return headerMap;
            }

        };
        requestQueue.add(request);
    }
}
