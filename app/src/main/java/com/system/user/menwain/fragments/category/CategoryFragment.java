package com.system.user.menwain.fragments.category;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.system.user.menwain.adapters.category_adapters.CategoryAdapter;
import com.system.user.menwain.responses.category.SuperCategoryResponse;
import com.system.user.menwain.utils.URLs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    RecyclerView recyclerViewProductCategory;
    private ImageView mBarCodeScanner;
    private CardView mSearchViewCategory;
    private List<SuperCategoryResponse.SuperCategory.Datum> categoryList  = new ArrayList<>();
    private CategoryAdapter categoryAdapter;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_category,container,false);
        mSearchViewCategory = getActivity().findViewById(R.id.search_view);
        mSearchViewCategory.setVisibility(View.VISIBLE);
        customProgressDialog(getContext());
        getSuperCategoryData();

        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_category);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(),3, GridLayoutManager.VERTICAL,false));
        categoryAdapter = new CategoryAdapter(getContext(),categoryList);
        recyclerViewProductCategory.setAdapter(categoryAdapter);

        return view;
    }

    private void getSuperCategoryData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_super_category_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SuperCategoryResponse categoryResponse = gson.fromJson(response,SuperCategoryResponse.class);
                categoryList.clear();
                for (int i = 0; i<categoryResponse.getSuperCategory().getData().size();i++){
                    categoryList.add(categoryResponse.getSuperCategory().getData().get(i));
                }
                categoryAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("category_erroe",error.toString());
                progressDialog.dismiss();
            }
        });
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
