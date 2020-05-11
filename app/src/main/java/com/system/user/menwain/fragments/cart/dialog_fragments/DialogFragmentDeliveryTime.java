package com.system.user.menwain.fragments.cart.dialog_fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import com.system.user.menwain.fragments.cart.AddCardFragment;
import com.system.user.menwain.fragments.cart.PaymentFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.DeliveryTimesAdapter;
import com.system.user.menwain.responses.cart.StoreTimeSLotsResponse;
import com.system.user.menwain.utils.URLs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    String current_date;
    long daysDiff;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_delivery_time, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        prefrences = new Preferences(getContext());
        customDialog(getContext());
        tvDeliveryPickUp = view.findViewById(R.id.tv_delivery_pickup);
        mConfirm = view.findViewById(R.id.confirm_btn_delivery_time);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        recyclerView = view.findViewById(R.id.recycler_view_delivery_time);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        current_date = df.format(date);
        getStoreTimeSlots(prefrences.getStoreId());

        //String due_date = jobsResponse.getDueDate();

        tvDate = view.findViewById(R.id.tv_date);
        tvDate.setText(current_date);

        mConfirm.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        if (prefrences.getPayRBtnStatus() == 2 || prefrences.getPayRBtnStatus() == 4) {
            tvDeliveryPickUp.setText(R.string.time_slot_dialog_title_p);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        timesAdapter = new DeliveryTimesAdapter(getContext(), delivery_times_list, this);
        recyclerView.setAdapter(timesAdapter);
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

    private void submitPreferredDeliveryDate() {
        dialog.show();
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URLs.submit_delivery_date_url + time_slot, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prefrences.setCartFragStatus(4);
                prefrences.setDateTime(date_time);
                prefrences.setTimeSlotId(time_slot);

                if (prefrences.getPaymentStatus() == 1) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AddCardFragment()).commit();
                } else {
                    DialogFragmentSaveList dialogFragmentSaveList = new DialogFragmentSaveList();
                    dialogFragmentSaveList.show(getFragmentManager(), "Purchasing Method");
                }
                /*getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment())
                        .addToBackStack(null).commit();*/
                //prefrences.setCartFragStatus(0);
                dismiss();
               // Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("time_error", error.toString());
                dialog.dismiss();
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
                        String due_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        daybetween(current_date/*"25/02/2020"*/, due_date /*"28/02/2020"*/, "yyyy-MM-dd");
                        if (daysDiff < 0) {
                            Toast.makeText(getContext(), "Please select a valid date!", Toast.LENGTH_SHORT).show();
                        } else {
                            tvDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            getStoreTimeSlots(prefrences.getStoreId());
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select Date..");
        datePickerDialog.show();
    }
    public long daybetween(String date1, String date2, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date Date1 = null, Date2 = null;
        try {
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        daysDiff = (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
        return (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
    }
    private void getStoreTimeSlots(int selectedStoreId) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.post_preffered_date_url + selectedStoreId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StoreTimeSLotsResponse timeSLotsResponse = gson.fromJson(response, StoreTimeSLotsResponse.class);
                delivery_times_list.clear();
                for (int i = 0; i < timeSLotsResponse.getList().size(); i++) {
                    delivery_times_list.add(timeSLotsResponse.getList().get(i));
                }
                timesAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("time_error", error.toString());
                dialog.dismiss();
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
                map.put("date", tvDate.getText().toString().trim());
                Log.e("selected_date",tvDate.getText().toString().trim());
                return map;
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
