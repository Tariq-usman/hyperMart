package com.system.user.menwain.fragments.category;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.search.NameSearchAdapter;
import com.system.user.menwain.fragments.others.SearchFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.adapters.category_adapters.SuperCategoryAdapter;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.category_adapters.CategoryProductsAdapter;
import com.system.user.menwain.adapters.category_adapters.CategoryAdapter;
import com.system.user.menwain.responses.category.CategoryResponse;
import com.system.user.menwain.responses.search.SearchByNameResponse;
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

public class CategoryFragment extends Fragment implements RecyclerClickInterface {

    private int cat_id, super_cat_id;
    private RecyclerView recyclerViewCategory, recyclerViewCategoryProducts, recyclerViewSubCategory, recyclerViewSubCategoryProducts;
    private LinearLayoutManager linearLayoutManager;
    private int getPreviousId = SuperCategoryAdapter.passId;
    private ImageView mBackBtn, mSearch, mBarCodeScanner;
    private EditText etSearch;
    private Preferences prefrences;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    private CategoryAdapter categoryAdapter;
    private CategoryProductsAdapter categoryProductsAdapter;
    Bundle bundle;
    private List<CategoryResponse.Category.Datum> catergoryList = new ArrayList<CategoryResponse.Category.Datum>();
    private List<CategoryResponse.Products.Datum_> category_products_list = new ArrayList<CategoryResponse.Products.Datum_>();
    private SuperCategoryFragment fragment;
    private List<SearchByNameResponse.Data.Datum> search_by_name_list = new ArrayList<>();
    private NameSearchAdapter nameSearchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());
        fragment = new SuperCategoryFragment();
        super_cat_id = prefrences.getSuperCatId();

        mSearch = view.findViewById(R.id.iv_search_cat);
        etSearch = view.findViewById(R.id.et_search_cat);
        mBarCodeScanner=view.findViewById(R.id.bar_code_scanner_cat);
        mBackBtn = view.findViewById(R.id.iv_back_cat);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCategoryFragStatus(0);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                SearchFragment fragment = new SearchFragment();
                if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("search", etSearch.getText().toString().trim());
                    etSearch.setText("");
                    fragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                }
            }
        });
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });
        getCategory(super_cat_id);

        recyclerViewCategory = view.findViewById(R.id.recycler_view_caterory);
        recyclerViewCategory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(getContext(), catergoryList, CategoryFragment.this);
        recyclerViewCategory.setAdapter(categoryAdapter);
        //recyclerViewCategory.smoothScrollToPosition(getPreviousId + 1);

        recyclerViewCategoryProducts = view.findViewById(R.id.recycler_view_category_products);
        recyclerViewCategoryProducts.setHasFixedSize(true);
        recyclerViewCategoryProducts.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        categoryProductsAdapter = new CategoryProductsAdapter(getContext(), category_products_list);
        recyclerViewCategoryProducts.setAdapter(categoryProductsAdapter);

        nameSearchAdapter = new NameSearchAdapter(getContext(), search_by_name_list);

        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {
    }

    private void getCategory(int superCatId) {
        dialog.show();
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
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                dialog.dismiss();
            }
        });
        requestQueue.add(request);
    }

    private void searchProductByName(final String name) {
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
                    Toast.makeText(getContext(), getContext().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    recyclerViewCategoryProducts.setAdapter(nameSearchAdapter);
                    nameSearchAdapter.notifyDataSetChanged();
                } else {
                    recyclerViewCategoryProducts.setAdapter(nameSearchAdapter);
                    nameSearchAdapter.notifyDataSetChanged();
                }
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                etSearch.setText("");
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
