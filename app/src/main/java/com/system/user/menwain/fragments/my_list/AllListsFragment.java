package com.system.user.menwain.fragments.my_list;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.my_lists_adapters.AllListsAdapter;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.my_list.UserWishlistProductsResponse;
import com.system.user.menwain.utils.URLs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllListsFragment extends Fragment {

    RecyclerView recyclerViewAllLists;
    AllListsAdapter allListsAdapter;
    private CardView mSearchViewAllLists;
    ImageView mBackBtn;
    private Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private List<UserWishlistProductsResponse.Product.Datum> orders_list = new ArrayList<>();
    private List<UserWishlistProductsResponse.Product.Datum> filter_orders_list = new ArrayList<>();
    private EditText etSearchList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_lists, container, false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());

        etSearchList = view.findViewById(R.id.et_search_my_list);

        recyclerViewAllLists = view.findViewById(R.id.recycler_view_all_lists);
        recyclerViewAllLists.setHasFixedSize(true);
        recyclerViewAllLists.setLayoutManager(new LinearLayoutManager(getContext()));

        allListsAdapter = new AllListsAdapter(getContext(), orders_list);
        recyclerViewAllLists.setAdapter(allListsAdapter);
        getUserWistList();
        etSearchList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                filter_orders_list.clear();
                for (int i = 0; i < orders_list.size(); i++) {
                    final String text = orders_list.get(i).getWishlistName().toLowerCase();
                    if (text.contains(query)) {
                        filter_orders_list.add(orders_list.get(i));
                    }
                }
                /*if (filter_orders_list.size() == 0) {
                    Toast.makeText(getContext(), getContext().getString(R.string.no_result_found), Toast.LENGTH_SHORT).show();
                } else {*/
                    allListsAdapter = new AllListsAdapter(getContext(), filter_orders_list);
                    recyclerViewAllLists.setAdapter(allListsAdapter);
//                }
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
                allListsAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError", error.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                return headerMap;
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
