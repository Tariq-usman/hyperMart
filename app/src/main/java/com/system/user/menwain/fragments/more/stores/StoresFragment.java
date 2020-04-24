package com.system.user.menwain.fragments.more.stores;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.more_adapters.StoresAdapter;
import com.system.user.menwain.fragments.more.MoreFragment;
import com.system.user.menwain.responses.more.stores.StoresAllBranchesResponse;
import com.system.user.menwain.utils.URLs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StoresFragment extends Fragment {
    private RecyclerView recyclerViewProductCategory;
    private StoresAdapter storesAdapter;
    private ImageView ivBackStores;
    private TextView tvTitleStores;
    private CardView mSearchStores;
    private Preferences prefrences;
    private ProgressDialog progressDialog;
    private List<StoresAllBranchesResponse.Storelist.Datum> stores_list = new ArrayList<StoresAllBranchesResponse.Storelist.Datum>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);
        prefrences = new Preferences(getContext());
        customProgressDialog(getContext());
        ivBackStores = getActivity().findViewById(R.id.iv_back);
        ivBackStores.setVisibility(View.VISIBLE);
        mSearchStores = getActivity().findViewById(R.id.search_view);
        mSearchStores.setVisibility(View.VISIBLE);
        ivBackStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreStoresFragStatus(0);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).addToBackStack(null).commit();
                ivBackStores.setVisibility(View.INVISIBLE);
                //   tvTitleStores.setText("More");
            }
        });
        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_stores);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        storesAdapter = new StoresAdapter(getContext(), stores_list);
        recyclerViewProductCategory.setAdapter(storesAdapter);

        getAllStoreData();
        return view;
    }

    private void getAllStoreData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.get_all_stores_data_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StoresAllBranchesResponse branchesResponse = gson.fromJson(response, StoresAllBranchesResponse.class);
                for (int i = 0; i < branchesResponse.getStorelist().getData().size(); i++) {
                    stores_list.add(branchesResponse.getStorelist().getData().get(i));
                }
                storesAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Stores_error", error.toString());
            }
        });
        requestQueue.add(stringRequest);
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
