package com.system.user.menwain.fragments.cart.dialog_fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.DeliveryTimesAdapter;
import com.system.user.menwain.fragments.cart.PaymentFragment;
import com.system.user.menwain.responses.cart.StoreTimeSLotsResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DialogFragmentDeliveryTime extends DialogFragment implements View.OnClickListener, RecyclerClickInterface {
    private int mYear, mMonth, mDay, time_slot;
    TextView mConfirm, mTitleView, tvDate, tvDeliveryPickUp;
    ImageView mCloseBtn;
    private String date_time;
    private RecyclerView recyclerView;
    private DeliveryTimesAdapter timesAdapter;
    private List<StoreTimeSLotsResponse.List> delivery_times_list = new ArrayList<>();
    Prefrences prefrences;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_delivery_time, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        prefrences = new Prefrences(getContext());
        customProgressDialog(getContext());
        tvDeliveryPickUp = view.findViewById(R.id.tv_delivery_pickup);
        mConfirm = view.findViewById(R.id.confirm_btn_delivery_time);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        recyclerView = view.findViewById(R.id.recycler_view_delivery_time);
        tvDate = view.findViewById(R.id.tv_date);

        mConfirm.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        if (prefrences.getPayRBtnStatus() == 2 || prefrences.getPayRBtnStatus() == 4) {
            tvDeliveryPickUp.setText("Please tell us your preferred pickup time");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        timesAdapter = new DeliveryTimesAdapter(getContext(), delivery_times_list, this);
        recyclerView.setAdapter(timesAdapter);
        getStoreTimeSlots();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn_delivery_time:
                submitPreferredDeliveryDate();
                break;
            case R.id.tv_date:
                getDate();
                break;
            case R.id.close_back_view:
                dismiss();
                break;
        }
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        time_slot = delivery_times_list.get(position).getId();
        date_time = tvDate.getText().toString().trim() + " " + delivery_times_list.get(position).getFrom() + ":" + delivery_times_list.get(position).getTo();
        Log.e("time_slot", time_slot + "");

    }

    private void getStoreTimeSlots() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_store_time_slots_url + 1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StoreTimeSLotsResponse timeSLotsResponse = gson.fromJson(response, StoreTimeSLotsResponse.class);
                delivery_times_list.clear();
                for (int i = 0; i < timeSLotsResponse.getList().size(); i++) {
                    delivery_times_list.add(timeSLotsResponse.getList().get(i));
                }
                timesAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        requestQueue.add(request);
    }

    private void submitPreferredDeliveryDate() {
        progressDialog.show();
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URLs.submit_delivery_date_url + 1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prefrences.setDateTime(date_time);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new PaymentFragment()).commit();
                dismiss();
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("time_error", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("slot_id", String.valueOf(time_slot));
                map.put("preferred_delivery_date", tvDate.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(request);
    }

    private void getDate() {
        // Get Current Date
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select Date..");
        datePickerDialog.show();
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
