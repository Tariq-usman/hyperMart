package com.system.user.menwain.fragments.others;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.system.user.menwain.adapters.search.CodeSearchAdapter;
import com.system.user.menwain.adapters.search.NameSearchAdapter;
import com.system.user.menwain.fragments.category.SuperCategoryFragment;
import com.system.user.menwain.fragments.home.HomeFragment;
import com.system.user.menwain.fragments.more.stores.StoresFragment;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.search.SearchByNameResponse;
import com.system.user.menwain.responses.search.SearchByBarCodeResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private NameSearchAdapter nameSearchAdapter;
    private CodeSearchAdapter codeSearchAdapter;
    private List<SearchByNameResponse.Data.Datum> search_by_name_list = new ArrayList<>();
    private List<SearchByBarCodeResponse.Data.Datum> search_by_code_list = new ArrayList<>();
    private Bundle bundle;
    private String name, barCode;
    private Dialog dialog;
    private ImageView searchBack;
    private Preferences preferences;
    private int PAGE_NO = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int itemCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_search, container, false);
        preferences = new Preferences(getContext());
        dialog = Utils.dialog(getContext());
        bundle = this.getArguments();

        searchBack = view.findViewById(R.id.iv_back_search);
        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int more_status = preferences.getMoreFragStatus();
                int stores_fragment_status = preferences.getMoreStoresFragStatus();
                if (preferences.getBottomNavStatus() == 1) {
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "Home").commit();
                } else if (preferences.getBottomNavStatus() == 2) {
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SuperCategoryFragment()).commit();
                } else if (preferences.getBottomNavStatus() == 5) {
                    if (more_status == 1) {
                        // if (stores_fragment_status == 2) {
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new StoresFragment()).commit();
                        // }
                    }
                }
            }
        });
        recyclerView = view.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        nameSearchAdapter = new NameSearchAdapter(getContext(), search_by_name_list);
        codeSearchAdapter = new CodeSearchAdapter(getContext(), search_by_code_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        if (preferences.getSearchStatus() == 1) {
            recyclerView.setAdapter(nameSearchAdapter);
        } else {
            recyclerView.setAdapter(codeSearchAdapter);
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isLoading = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                if (isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) == totalItemCount
                        /*&& firstVisibleItemPosition >= 0&& totalItemCount >= PAGE_SIZE*/) {
                        isLoading = false;
                        if (preferences.getSearchStatus() == 1) {
//                            name = bundle.getString("search");
                            name = preferences.getSearchByName();
                            searchProductByName(PAGE_NO);
                        } else {
                            barCode = preferences.getSearchByCode();
                            searchProductByBarCode(PAGE_NO);
                        }
                    }
                }
            }
        });

        /*if (bundle != null) {
            name = bundle.getString("search");*/
        if (preferences.getSearchStatus() == 1) {
            name = preferences.getSearchByName();
            searchProductByName(PAGE_NO);
        } else {
            barCode = preferences.getSearchByCode();
            searchProductByBarCode(PAGE_NO);
        }

        return view;
    }

    private void searchProductByBarCode(int page_no) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.search_product_by_bar_code_url + page_no, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    SearchByBarCodeResponse nameResponse = gson.fromJson(response, SearchByBarCodeResponse.class);
//                    search_by_code_list.clear();
                    for (int i = 0; i < nameResponse.getData().getData().size(); i++) {
                        search_by_code_list.add(nameResponse.getData().getData().get(i));
                    }
                    PAGE_NO++;
                    if (search_by_code_list.size() == 0) {
                        Toast.makeText(getContext(), getContext().getString(R.string.no_result_found), Toast.LENGTH_SHORT).show();
                    }
                    codeSearchAdapter.notifyDataSetChanged();
                    ScanActivity.barCode = "";
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ScanActivity.barCode = "";
                Log.e("error_response", error.toString());
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("barcode", barCode);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", preferences.getLanguage());
                header.put("Accept", "application/json");
                return header;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void searchProductByName(int page_no) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.search_product_by_name_url + page_no, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    SearchByNameResponse nameResponse = gson.fromJson(response, SearchByNameResponse.class);
//                    search_by_name_list.clear();
                    for (int i = 0; i < nameResponse.getData().getData().size(); i++) {
                        search_by_name_list.add(nameResponse.getData().getData().get(i));
                    }
                    PAGE_NO++;
                    if (search_by_name_list.size() == 0) {
                        Toast.makeText(getContext(), getContext().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                    nameSearchAdapter.notifyDataSetChanged();
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("name_error_response", error.toString());
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
//                header.put("Authorization", "Bearer " + preferences.getToken());
                header.put("X-Language", preferences.getLanguage());
                header.put("Accept", "application/json");
                return header;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

}
