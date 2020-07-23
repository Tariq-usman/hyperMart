package com.system.user.menwain.fragments.category;

import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.adapters.category_adapters.SubCategoryAdapter;
import com.system.user.menwain.adapters.category_adapters.SubCategoryProductsAdapter;
import com.system.user.menwain.adapters.category_adapters.SubCategoryProductsFinalAdapter;
import com.system.user.menwain.adapters.category_adapters.SuperCategoryAdapter;
import com.system.user.menwain.adapters.search.NameSearchAdapter;
import com.system.user.menwain.fragments.others.SearchFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.category.SubCategoryProductsFinalResponse;
import com.system.user.menwain.responses.category.SubCategoryResponse;
import com.system.user.menwain.responses.search.SearchByNameResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SubCategoryFragment extends Fragment implements RecyclerClickInterface {

    private int cat_id, super_cat_id, sub_cat_id;
    private RecyclerView recyclerViewSubCategory, recyclerViewSubCategoryProducts, recyclerViewSubCategoryProductsFinal;
    private LinearLayoutManager linearLayoutManager;
//    private int getPreviousId = SuperCategoryAdapter.passId;
    private ImageView mBackBtn, mSearch, mBarCodeScanner;
    private EditText etSearch;
    private TextView tvNoCatAvail;
    private CardView mSearchViewItemsFragment;
    private Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private SubCategoryAdapter subCategoryAdapter;
    private SubCategoryProductsAdapter subCategoryProductsAdapter;
    private SubCategoryProductsFinalAdapter subCategoryProductsFinalAdapter;
    Bundle bundle;
    private List<SubCategoryResponse.Category.Datum> subCatergoryList = new ArrayList<SubCategoryResponse.Category.Datum>();
    private List<SubCategoryResponse.Products.Datum_> subCategory_products_list = new ArrayList<SubCategoryResponse.Products.Datum_>();
    private List<SubCategoryProductsFinalResponse.Products.Datum_> subCategory_products_final_list = new ArrayList<SubCategoryProductsFinalResponse.Products.Datum_>();
    private List<SearchByNameResponse.Data.Datum> search_by_name_list = new ArrayList<>();
    private NameSearchAdapter nameSearchAdapter;
    SearchFragment searchFragment = new SearchFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());
        mBackBtn = view.findViewById(R.id.iv_back_sub_cat);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCategoryFragStatus(1);
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CategoryFragment()).addToBackStack(null).commit();
            }
        });
        tvNoCatAvail = view.findViewById(R.id.tv_no_cat_prod_avail_sub_cat);
        mSearch = view.findViewById(R.id.iv_search_sub_cat);
        etSearch = view.findViewById(R.id.et_search_sub_cat);
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
                        bundle.putString("search", etSearch.getText().toString().trim());
                        etSearch.setText("");
                        searchFragment.setArguments(bundle);
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, searchFragment).commit();
                    }
                    return true;
                }
                return false;
            }
        });
        mBarCodeScanner = view.findViewById(R.id.bar_code_scanner_sub_cat);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (etSearch.getText().toString().trim().isEmpty() || etSearch.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.enter_desire_search), Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("search", etSearch.getText().toString().trim());
                    etSearch.setText("");
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

        cat_id = prefrences.getCatsId();
        super_cat_id = prefrences.getSuperCatId();
        getSubCategory(cat_id);

        recyclerViewSubCategory = view.findViewById(R.id.recycler_view_sub_caterory);
        recyclerViewSubCategory.setHasFixedSize(true);
        recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        subCategoryAdapter = new SubCategoryAdapter(getContext(), subCatergoryList, SubCategoryFragment.this);
        recyclerViewSubCategory.setAdapter(subCategoryAdapter);

        recyclerViewSubCategoryProducts = view.findViewById(R.id.recycler_view_sub_category_roducts);
        recyclerViewSubCategoryProducts.setHasFixedSize(true);
        recyclerViewSubCategoryProducts.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        subCategoryProductsAdapter = new SubCategoryProductsAdapter(getContext(), subCategory_products_list);
        recyclerViewSubCategoryProducts.setAdapter(subCategoryProductsAdapter);

        recyclerViewSubCategoryProductsFinal = view.findViewById(R.id.recycler_view_sub_category_roducts_final);
        recyclerViewSubCategoryProductsFinal.setHasFixedSize(true);
        recyclerViewSubCategoryProductsFinal.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        subCategoryProductsFinalAdapter = new SubCategoryProductsFinalAdapter(getContext(), subCategory_products_final_list);
        recyclerViewSubCategoryProductsFinal.setAdapter(subCategoryProductsFinalAdapter);

        nameSearchAdapter = new NameSearchAdapter(getContext(), search_by_name_list);

        return view;
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        sub_cat_id = subCatergoryList.get(position).getId();
        getFinalProducts(super_cat_id);

    }

    private void getSubCategory(int cat_id) {
        dialog.show();
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
                if (subCatergoryList.size() == 0) {
//                    Toast.makeText(getContext(), getContext().getString(R.string.no_sub_category), Toast.LENGTH_SHORT).show();
                    tvNoCatAvail.setVisibility(View.VISIBLE);
                    tvNoCatAvail.setText(getString(R.string.no_sub_category));

                } else {
                    if (subCategory_products_list.size() == 0) {
//                        Toast.makeText(getContext(), getContext().getString(R.string.no_products), Toast.LENGTH_SHORT).show();
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.no_products));
                    }
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("sub_cat_error", error.toString());
                try {
                    if (error instanceof TimeoutError) {
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.network_timeout));
                    } else if (error instanceof AuthFailureError) {
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.authentication_error));
                    } else if (error instanceof ServerError) {
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.server_error));
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.no_network_found));
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sup_category_id", super_cat_id + "");
                return map;
            }

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

    private void getFinalProducts(int sub_cat_id) {
        dialog.show();
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
                if (subCategory_products_final_list.size() == 0) {
//                    Toast.makeText(getContext(), getContext().getString(R.string.no_products), Toast.LENGTH_SHORT).show();
                    tvNoCatAvail.setVisibility(View.VISIBLE);
                    tvNoCatAvail.setText(getString(R.string.no_products));
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("sub_cat_pro_error", error.toString());
                try {
                    if (error instanceof TimeoutError) {
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.network_timeout));
                    } else if (error instanceof AuthFailureError) {
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.authentication_error));
                    } else if (error instanceof ServerError) {
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.server_error));
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        tvNoCatAvail.setVisibility(View.VISIBLE);
                        tvNoCatAvail.setText(getString(R.string.no_network_found));
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sup_category_id", super_cat_id + "");
                return map;
            }

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

    public void customDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.layout_loading_dialog);
        }
        dialog = builder.create();
    }


}
