package com.system.user.menwain.fragments.my_list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.system.user.menwain.R;
import com.system.user.menwain.activities.LoginActivity;
import com.system.user.menwain.adapters.my_lists_adapters.AllListsAdapter;
import com.system.user.menwain.others.PaginationListenerLinearLayoutManager;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.my_list.UserWishlistProductsResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.system.user.menwain.others.PaginationListenerGridLayoutManager.PAGE_START;

public class AllListsFragment extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewAllLists;
    AllListsAdapter allListsAdapter;
    private CardView mSearchViewAllLists;
    ImageView mBackBtn;
    private TextView tvMessage;
    private Preferences prefrences;
    private Dialog dialog;
    private List<UserWishlistProductsResponse.Product.Datum> orders_list = new ArrayList<>();
    private List<UserWishlistProductsResponse.Product.Datum> filter_orders_list = new ArrayList<>();
    private EditText etSearchList;
    int order_no;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int itemCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_lists, container, false);
        prefrences = new Preferences(getContext());
        dialog = Utils.dialog(getContext());
        Random r = new Random();

        tvMessage = view.findViewById(R.id.tv_message_mylist);
        etSearchList = view.findViewById(R.id.et_search_my_list);
        etSearchList.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white, 0, 0, 0);
        etSearchList.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        etSearchList.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        recyclerViewAllLists = view.findViewById(R.id.recycler_view_all_lists);
        recyclerViewAllLists.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAllLists.setLayoutManager(linearLayoutManager);
        allListsAdapter = new AllListsAdapter(getContext(), orders_list);
        recyclerViewAllLists.setAdapter(allListsAdapter);
        recyclerViewAllLists.addOnScrollListener(new PaginationListenerLinearLayoutManager(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading=true;
                currentPage++;
                getUserWistList();
            }

            @Override
            protected boolean isLastPage() {
                return isLastPage;
            }

            @Override
            protected boolean isLoading() {
                return isLoading;
            }
        });
        getUserWistList();
        etSearchList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    etSearchList.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    //Assign your image again to the view, otherwise it will always be gone even if the text is 0 again.
                    etSearchList.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white, 0, 0, 0);
                }
                String query = s.toString().toLowerCase();
                filter_orders_list.clear();
                for (int i = 0; i < orders_list.size(); i++) {
                    final String text = orders_list.get(i).getWishlistName().toLowerCase();
                    if (text.contains(query)) {
                        filter_orders_list.add(orders_list.get(i));
                    }
                }
                allListsAdapter = new AllListsAdapter(getContext(), filter_orders_list);
                recyclerViewAllLists.setAdapter(allListsAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void getUserWistList() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_user_wish_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserWishlistProductsResponse wishlistProductsResponse = gson.fromJson(response, UserWishlistProductsResponse.class);
                orders_list.clear();
                for (int i = 0; i < wishlistProductsResponse.getProduct().getData().size(); i++) {
                    orders_list.add(wishlistProductsResponse.getProduct().getData().get(i));
                }
                Collections.reverse(orders_list);
                allListsAdapter.notifyDataSetChanged();
                if (orders_list.size() == 0) {
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText(getString(R.string.no_list_found));
                } else {
                    tvMessage.setVisibility(View.INVISIBLE);
                }
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError", error.toString());
                if (error != null && error.networkResponse != null && error.networkResponse.data != null) {
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText(getString(R.string.authentication_error));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent logInIntnet = new Intent(getContext(), LoginActivity.class);
                            logInIntnet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().startActivity(logInIntnet);
                            dialog.dismiss();
                        }
                    }, 1000);
                } else {
                    if (error instanceof TimeoutError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.network_timeout));
                    } else if (error instanceof AuthFailureError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.authentication_error));
                    } else if (error instanceof ServerError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.server_error));
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        tvMessage.setVisibility(View.VISIBLE);
                        tvMessage.setText(getString(R.string.no_network_found));
                    } else {
                    }
                }
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                headerMap.put("Accept", "application/json");
                return headerMap;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
