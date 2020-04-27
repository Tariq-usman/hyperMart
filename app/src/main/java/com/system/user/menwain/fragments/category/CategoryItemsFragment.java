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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.category_adapters.SubCategoryAdapter;
import com.system.user.menwain.adapters.category_adapters.SubCategoryProductsAdapter;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.category_adapters.SuperCategoryAdapter;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.category_adapters.CategoryProductsAdapter;
import com.system.user.menwain.adapters.category_adapters.CategoryAdapter;
import com.system.user.menwain.responses.category.CategoryResponse;
import com.system.user.menwain.responses.category.SubCategoryResponse;
import com.system.user.menwain.utils.URLs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryItemsFragment extends Fragment implements RecyclerClickInterface {

    private int cat_id, super_cat_id;
    private RecyclerView recyclerViewCategory, recyclerViewCategoryProducts, recyclerViewSubCategory,recyclerViewSubCategoryProducts;
    private LinearLayoutManager linearLayoutManager;
    private int getPreviousId = SuperCategoryAdapter.passId;
    private ImageView mBackBtn, mBarCodeScanner;

    private CardView mSearchViewItemsFragment;
    private Preferences prefrences;
    private ProgressDialog progressDialog;
    private CategoryAdapter categoryAdapter;
    private SubCategoryAdapter subCategoryAdapter;
    private CategoryProductsAdapter categoryProductsAdapter;
    private SubCategoryProductsAdapter subCategoryProductsAdapter;
    Bundle bundle;
    private List<CategoryResponse.Category.Datum> catergoryList = new ArrayList<CategoryResponse.Category.Datum>();
    private List<SubCategoryResponse.Category.Datum> subCatergoryList = new ArrayList<SubCategoryResponse.Category.Datum>();
    private List<CategoryResponse.Products.Datum_> category_products_list = new ArrayList<CategoryResponse.Products.Datum_>();
    private List<CategoryResponse.Products.Datum_> subCategory_products_list = new ArrayList<CategoryResponse.Products.Datum_>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        prefrences = new Preferences(getContext());
        customProgressDialog(getContext());
            super_cat_id = prefrences.getSuperCatId();

        mSearchViewItemsFragment = getActivity().findViewById(R.id.search_view);
        //mSearchViewItemsFragment.setVisibility(View.INVISIBLE);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCategoryFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SuperCategoryFragment()).addToBackStack(null).commit();
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

//        getSuperCategoryData();
        getCategory(super_cat_id);

        recyclerViewCategory = view.findViewById(R.id.recycler_view_caterory);
        recyclerViewCategory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(getContext(), catergoryList, CategoryItemsFragment.this);
        recyclerViewCategory.setAdapter(categoryAdapter);
        recyclerViewCategory.smoothScrollToPosition(getPreviousId + 1);

        recyclerViewCategoryProducts = view.findViewById(R.id.recycler_view_category_products);
        recyclerViewCategoryProducts.setHasFixedSize(true);
        recyclerViewCategoryProducts.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        categoryProductsAdapter = new CategoryProductsAdapter(getContext(), category_products_list);
        recyclerViewCategoryProducts.setAdapter(categoryProductsAdapter);

        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {
    }
    private void getCategory(int superCatId) {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_category_url + superCatId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CategoryResponse categoryResponse = gson.fromJson(response, CategoryResponse.class);
                catergoryList.clear();
                for (int i = 0; i < categoryResponse.getCategory().getData().size(); i++) {
                    catergoryList.add(categoryResponse.getCategory().getData().get(i));
                }
                categoryAdapter.notifyDataSetChanged();
                category_products_list.clear();
                for (int i = 0; i < categoryResponse.getProducts().getData().size(); i++) {
                    category_products_list.add(categoryResponse.getProducts().getData().get(i));
                }
                categoryProductsAdapter.notifyDataSetChanged();
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
