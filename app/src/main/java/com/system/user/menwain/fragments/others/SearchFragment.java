package com.system.user.menwain.fragments.others;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.search.CodeSearchAdapter;
import com.system.user.menwain.adapters.search.NameSearchAdapter;
import com.system.user.menwain.responses.search.SearchByNameResponse;
import com.system.user.menwain.responses.search.SearchByBarCodeResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private NameSearchAdapter nameSearchAdapter;
    private CodeSearchAdapter  codeSearchAdapter;
    private List<SearchByNameResponse.Data.Datum> search_by_name_list = new ArrayList<>();
    private List<SearchByBarCodeResponse.Data.Datum> search_by_code_list = new ArrayList<>();
    private Bundle bundle;
    private String name;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_search, container, false);
        customDialog(getContext());
        bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("search");
            searchProductByName();
        } else {
            searchProductByBarCode();
        }
        recyclerView = view.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        nameSearchAdapter = new NameSearchAdapter(getContext(), search_by_name_list);
        codeSearchAdapter = new CodeSearchAdapter(getContext(),search_by_code_list);
        return view;
    }

    private void searchProductByBarCode() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.search_product_by_bar_code_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SearchByBarCodeResponse nameResponse = gson.fromJson(response, SearchByBarCodeResponse.class);
                search_by_code_list.clear();
                for (int i = 0; i < nameResponse.getData().getData().size(); i++) {
                    search_by_code_list.add(nameResponse.getData().getData().get(i));
                }
                if (search_by_code_list.size() == 0) {
                    Toast.makeText(getContext(), "No such data found!", Toast.LENGTH_SHORT).show();
                }
                recyclerView.setAdapter(codeSearchAdapter);
                codeSearchAdapter.notifyDataSetChanged();

//                Toast.makeText(getContext(), "Response.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error_response", error.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("barcode", ScanActivity.barCode+"");
                return map;
            }
        };
        requestQueue.add(request);

    }

    private void searchProductByName() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.search_product_by_name_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SearchByNameResponse nameResponse = gson.fromJson(response, SearchByNameResponse.class);
                search_by_name_list.clear();
                for (int i = 0; i < nameResponse.getData().getData().size(); i++) {
                    search_by_name_list.add(nameResponse.getData().getData().get(i));
                }
                if (search_by_name_list.size() == 0) {
                    Toast.makeText(getContext(), "No such data found!", Toast.LENGTH_SHORT).show();
                }
                recyclerView.setAdapter(nameSearchAdapter);
                nameSearchAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error_response", error.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
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
