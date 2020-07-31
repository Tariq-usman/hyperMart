package com.system.user.menwain.fragments.category;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.search.NameSearchAdapter;
import com.system.user.menwain.fragments.others.SearchFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.PaginationListenerGridLayoutManager;
import com.system.user.menwain.others.PaginationListenerLinearLayoutManager;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.adapters.category_adapters.SuperCategoryAdapter;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.category_adapters.CategoryProductsAdapter;
import com.system.user.menwain.adapters.category_adapters.CategoryAdapter;
import com.system.user.menwain.responses.category.CategoryResponse;
import com.system.user.menwain.responses.search.SearchByNameResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

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

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.system.user.menwain.others.PaginationListenerGridLayoutManager.PAGE_START;

public class CategoryFragment extends Fragment implements RecyclerClickInterface {
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int itemCount = 0;
    private int cat_id, super_cat_id;
    private RecyclerView recyclerViewCategory, recyclerViewCategoryProducts, recyclerViewSubCategory, recyclerViewSubCategoryProducts;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private ImageView mBackBtn, mSearch, mBarCodeScanner;
    private EditText etSearch;
    private Preferences prefrences;
    private Dialog dialog;
    private CategoryAdapter categoryAdapter;
    private CategoryProductsAdapter categoryProductsAdapter;
    Bundle bundle;
    private List<CategoryResponse.Category.Datum> catergoryList = new ArrayList<CategoryResponse.Category.Datum>();
    private List<CategoryResponse.Products.Datum_> category_products_list = new ArrayList<CategoryResponse.Products.Datum_>();
    private SuperCategoryFragment fragment;
    private List<SearchByNameResponse.Data.Datum> search_by_name_list = new ArrayList<>();
    private NameSearchAdapter nameSearchAdapter;
    SearchFragment searchFragment = new SearchFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        prefrences = new Preferences(getContext());
        dialog = Utils.dialog(getContext());
        fragment = new SuperCategoryFragment();
        super_cat_id = prefrences.getSuperCatId();

        mSearch = view.findViewById(R.id.iv_search_cat);
        etSearch = view.findViewById(R.id.et_search_cat);
        etSearch.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Bundle bundle = new Bundle();
                    if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                        Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                    } else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        prefrences.setSearchByName(etSearch.getText().toString().trim());
                        prefrences.setSearchStatus(1);
                        etSearch.setText("");
//                        searchFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, searchFragment).commit();
                    }
                    return true;
                }
                return false;
            }
        });
        mBarCodeScanner = view.findViewById(R.id.bar_code_scanner_cat);
        mBackBtn = view.findViewById(R.id.iv_back_cat);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCategoryFragStatus(0);
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                } else {
                    prefrences.setSearchByName(etSearch.getText().toString().trim());
                    prefrences.setSearchStatus(1);
                    searchFragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, searchFragment).commit();
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

        recyclerViewCategoryProducts = view.findViewById(R.id.recycler_view_category_products);
        recyclerViewCategoryProducts.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewCategoryProducts.setLayoutManager(gridLayoutManager);
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
                Log.e("category_error", error.toString());
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
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", prefrences.getLanguage());
                return header;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
}
