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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.system.user.menwain.adapters.category_adapters.SubCategoryAdapter;
import com.system.user.menwain.adapters.category_adapters.SubCategoryProductsAdapter;
import com.system.user.menwain.adapters.category_adapters.SubCategoryProductsFinalAdapter;
import com.system.user.menwain.adapters.category_adapters.SuperCategoryAdapter;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.category.SubCategoryProductsFinalResponse;
import com.system.user.menwain.responses.category.SubCategoryResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryItemsFragment extends Fragment implements RecyclerClickInterface {

    private int cat_id, super_cat_id,sub_cat_id;
    private RecyclerView  recyclerViewSubCategory,recyclerViewSubCategoryProducts,recyclerViewSubCategoryProductsFinal;
    private LinearLayoutManager linearLayoutManager;
    private int getPreviousId = SuperCategoryAdapter.passId;
    private ImageView mBackBtn, mBarCodeScanner;

    private CardView mSearchViewItemsFragment;
    private Preferences prefrences;
    private ProgressDialog progressDialog;
    private SubCategoryAdapter subCategoryAdapter;
    private SubCategoryProductsAdapter subCategoryProductsAdapter;
    private SubCategoryProductsFinalAdapter subCategoryProductsFinalAdapter;
    Bundle bundle;
    private List<SubCategoryResponse.Category.Datum> subCatergoryList = new ArrayList<SubCategoryResponse.Category.Datum>();
    private List<SubCategoryResponse.Products.Datum_> subCategory_products_list = new ArrayList<SubCategoryResponse.Products.Datum_>();
    private List<SubCategoryProductsFinalResponse.Products.Datum_> subCategory_products_final_list = new ArrayList<SubCategoryProductsFinalResponse.Products.Datum_>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        prefrences = new Preferences(getContext());
        customProgressDialog(getContext());

        mSearchViewItemsFragment = getActivity().findViewById(R.id.search_view);
        //mSearchViewItemsFragment.setVisibility(View.INVISIBLE);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCategoryFragStatus(1);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryItemsFragment()).addToBackStack(null).commit();
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
        cat_id = prefrences.getCatsId();
        super_cat_id = prefrences.getSuperCatId();
        getSubCategory(cat_id);

        recyclerViewSubCategory = view.findViewById(R.id.recycler_view_sub_caterory);
        recyclerViewSubCategory.setHasFixedSize(true);
        recyclerViewSubCategory.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        subCategoryAdapter = new SubCategoryAdapter(getContext(), subCatergoryList, SubCategoryItemsFragment.this);
        recyclerViewSubCategory.setAdapter(subCategoryAdapter);

        recyclerViewSubCategoryProducts = view.findViewById(R.id.recycler_view_sub_category_roducts);
        recyclerViewSubCategoryProducts.setHasFixedSize(true);
        recyclerViewSubCategoryProducts.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        subCategoryProductsAdapter = new SubCategoryProductsAdapter(getContext(), subCategory_products_list);
        recyclerViewSubCategoryProducts.setAdapter(subCategoryProductsAdapter);

        recyclerViewSubCategoryProductsFinal = view.findViewById(R.id.recycler_view_sub_category_roducts_final);
        recyclerViewSubCategoryProductsFinal.setHasFixedSize(true);
        recyclerViewSubCategoryProductsFinal.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        subCategoryProductsFinalAdapter = new SubCategoryProductsFinalAdapter(getContext(),subCategory_products_final_list );
        recyclerViewSubCategoryProductsFinal.setAdapter(subCategoryProductsFinalAdapter);

        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        sub_cat_id = subCatergoryList.get(position).getId();
        getFinalProducts(super_cat_id);

    }

    private void getSubCategory(int cat_id) {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.get_sub_category_and_products_url + cat_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SubCategoryResponse subCategoryResponse = gson.fromJson(response, SubCategoryResponse.class);
                subCatergoryList.clear();
                for (int i = 0; i < subCategoryResponse.getCategory().getData().size(); i++) {
                    subCatergoryList.add(subCategoryResponse.getCategory().getData().get(i));
                }
                subCategoryAdapter.notifyDataSetChanged();
                subCategory_products_list.clear();
                for (int i = 0; i < subCategoryResponse.getProducts().getData().size(); i++) {
                    subCategory_products_list.add(subCategoryResponse.getProducts().getData().get(i));
                }
                subCategoryProductsAdapter.notifyDataSetChanged();
                if (subCategory_products_list.size()==0){
                    Toast.makeText(getContext(), "No Such Products!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erroe", error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("sup_category_id",super_cat_id+"");
                return map;
            }
        };
        requestQueue.add(request);
    }

    private void getFinalProducts(int sub_cat_id) {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.get_sub_category_products_url + sub_cat_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                recyclerViewSubCategoryProducts.setVisibility(View.GONE);
                recyclerViewSubCategoryProductsFinal.setVisibility(View.VISIBLE);
                SubCategoryProductsFinalResponse finalProducts = gson.fromJson(response, SubCategoryProductsFinalResponse.class);
                subCategory_products_final_list.clear();
                for (int i = 0; i < finalProducts.getProducts().getData().size(); i++) {
                    subCategory_products_final_list.add(finalProducts.getProducts().getData().get(i));
                }
                subCategoryProductsFinalAdapter.notifyDataSetChanged();
                if (subCategory_products_final_list.size()==0){
                    Toast.makeText(getContext(), "No Such Products!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("erroe", error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("sup_category_id",super_cat_id+"");
                return map;
            }
        };
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
