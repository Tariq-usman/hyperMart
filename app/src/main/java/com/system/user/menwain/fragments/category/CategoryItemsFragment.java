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
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.category_adapters.CategoryAdapter;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.category_adapters.SubCategoryAdapter;
import com.system.user.menwain.adapters.category_adapters.SelectedItemAdapter;
import com.system.user.menwain.responses.category.CategoryResponse;
import com.system.user.menwain.responses.category.SuperCategoryResponse;
import com.system.user.menwain.utils.URLs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemsFragment extends Fragment implements RecyclerClickInterface {

    private int category_id;
    private RecyclerView recyclerViewProductCategory, recyclerViewItemNames, recyclerViewFilterItems;
    private LinearLayoutManager linearLayoutManager;
    private int getPreviousId = CategoryAdapter.passId;
    private ImageView mBackBtn, mBarCodeScanner;

    private CardView mSearchViewItemsFragment;
    private Prefrences prefrences;
    private ProgressDialog progressDialog;
    private SelectedItemAdapter selectedItemAdapter;
    private SubCategoryAdapter subCategoryAdapter;
    Bundle bundle;
    private List<SuperCategoryResponse.SuperCategory.Datum> catergoryList = new ArrayList<SuperCategoryResponse.SuperCategory.Datum>();
    private List<CategoryResponse.Product> subCatergoryList = new ArrayList<CategoryResponse.Product>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_items, container, false);
        prefrences = new Prefrences(getContext());
        customProgressDialog(getContext());
        bundle = this.getArguments();
        mSearchViewItemsFragment = getActivity().findViewById(R.id.search_view);
        //mSearchViewItemsFragment.setVisibility(View.INVISIBLE);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCategoryFragStatus(0);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryFragment()).addToBackStack(null).commit();
                mBackBtn.setVisibility(View.GONE);
            }
        });

        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        getSuperCategoryData();
        getCategory();

        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_selected_items);
        recyclerViewProductCategory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProductCategory.setLayoutManager(linearLayoutManager);
        selectedItemAdapter = new SelectedItemAdapter(getContext(), catergoryList,CategoryItemsFragment.this);
        recyclerViewProductCategory.setAdapter(selectedItemAdapter);
        recyclerViewProductCategory.smoothScrollToPosition(getPreviousId + 1);

        recyclerViewFilterItems = view.findViewById(R.id.recycler_view_filter_items);
        recyclerViewFilterItems.setHasFixedSize(true);
        recyclerViewFilterItems.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        subCategoryAdapter = new SubCategoryAdapter(getContext(), subCatergoryList);
        recyclerViewFilterItems.setAdapter(subCategoryAdapter);

        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        category_id = catergoryList.get(position).getId();
        getSubCategory();

    }

    private void getSuperCategoryData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_super_category_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SuperCategoryResponse categoryResponse = gson.fromJson(response, SuperCategoryResponse.class);
                catergoryList.clear();
                for (int i = 0; i < categoryResponse.getSuperCategory().getData().size(); i++) {
                    catergoryList.add(categoryResponse.getSuperCategory().getData().get(i));
                }
                selectedItemAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("category_erroe", error.toString());
                progressDialog.dismiss();
            }
        });
        requestQueue.add(request);
    }


    private void getCategory() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_category_url + bundle.getString("id"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CategoryResponse categoryResponse = gson.fromJson(response, CategoryResponse.class);
               /* catergoryList.clear();
                for (int i=0;i<categoryResponse.getCategory().getData().size();i++){
                    catergoryList.add(categoryResponse.getCategory().getData().get(i));
                }
                selectedItemAdapter.notifyDataSetChanged();*/

                subCatergoryList.clear();
                for (int i = 0; i < categoryResponse.getProducts().size(); i++) {
                    subCatergoryList.add(categoryResponse.getProducts().get(i));
                }
                subCategoryAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erroe", error.toString());
                progressDialog.dismiss();
            }
        });
        requestQueue.add(request);
    }

    private void getSubCategory() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_category_url + category_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CategoryResponse categoryResponse = gson.fromJson(response, CategoryResponse.class);
               /* catergoryList.clear();
                for (int i=0;i<categoryResponse.getCategory().getData().size();i++){
                    catergoryList.add(categoryResponse.getCategory().getData().get(i));
                }
                selectedItemAdapter.notifyDataSetChanged();*/

                subCatergoryList.clear();
                for (int i = 0; i < categoryResponse.getProducts().size(); i++) {
                    subCatergoryList.add(categoryResponse.getProducts().get(i));
                }
                subCategoryAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erroe", error.toString());
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
