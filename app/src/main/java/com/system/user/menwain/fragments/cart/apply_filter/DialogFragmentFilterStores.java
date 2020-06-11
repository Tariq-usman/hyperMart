package com.system.user.menwain.fragments.cart.apply_filter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.apply_filter.FilterStoresAdapter;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.more.stores.StoresAllBranchesResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.List;

public class DialogFragmentFilterStores extends DialogFragment implements View.OnClickListener {
    public interface OnDismissDialogInterface {
        void onDismissDialogFragment();
    }

    public OnDismissDialogInterface dismissDialog;

    private LinearLayout progressLayout;
    private CheckBox cbSelectAll;
    TextView mApplyFilter;
    ImageView mCloseBtn;
    private RecyclerView recyclerView;
    FilterStoresAdapter filterStoresAdapter;
    Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    public static boolean check_value = false;
    private List<StoresAllBranchesResponse.Storelist.Datum> filter_stores_list = new ArrayList<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dismissDialog = (OnDismissDialogInterface) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(" calss_cast_exception", e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_filter_stores, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog(getContext());

        prefrences = new Preferences(getContext());


        progressLayout = view.findViewById(R.id.progress_layout);
        cbSelectAll = view.findViewById(R.id.cb_select_all);
        mApplyFilter = view.findViewById(R.id.apply_btn_filter_stores);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        recyclerView = view.findViewById(R.id.recycler_view_filter_stores);


        cbSelectAll.setOnClickListener(this);
        mApplyFilter.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        getAllStoreData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filterStoresAdapter = new FilterStoresAdapter(getContext(), filter_stores_list);
        recyclerView.setAdapter(filterStoresAdapter);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_select_all:
                if (cbSelectAll.isChecked()) {
                    cbSelectAll.setChecked(true);
                    check_value = true;
                    filterStoresAdapter = new FilterStoresAdapter(getContext(), filter_stores_list);
                    recyclerView.setAdapter(filterStoresAdapter);
                } else {
                    cbSelectAll.setChecked(false);
                    check_value = false;
                    filterStoresAdapter = new FilterStoresAdapter(getContext(), filter_stores_list);
                    recyclerView.setAdapter(filterStoresAdapter);
                }
                break;
            case R.id.apply_btn_filter_stores:
                prefrences.setOrderStatus(3);
                dismissDialog.onDismissDialogFragment();
                dismiss();
                break;
            case R.id.close_back_view:
                dismiss();
                break;
        }
    }

    private void getAllStoreData() {
        progressLayout.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.get_all_stores_data_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StoresAllBranchesResponse branchesResponse = gson.fromJson(response, StoresAllBranchesResponse.class);
                for (int i = 0; i < branchesResponse.getStorelist().getData().size(); i++) {
                    filter_stores_list.add(branchesResponse.getStorelist().getData().get(i));
                }
                filterStoresAdapter.notifyDataSetChanged();
                progressLayout.setVisibility(View.INVISIBLE);
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.INVISIBLE);
                Log.e("Stores_error", error.toString());
            }
        });
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
