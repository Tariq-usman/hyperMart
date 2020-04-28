package com.system.user.menwain.fragments.others;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.system.user.menwain.adapters.category_adapters.SearchAdapter;
import com.system.user.menwain.responses.SearchProductByNameResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private List<SearchProductByNameResponse.Data.Datum> search_list = new ArrayList<>();
    private Bundle bundle;
    private String name;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_search,container,false);
        customProgressDialog(getContext());
        bundle = this.getArguments();
        if (bundle!=null){
             name = bundle.getString("search");
            searchProductByName();
        }
        recyclerView = view.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        searchAdapter= new SearchAdapter(getContext(),search_list);
        recyclerView.setAdapter(searchAdapter);
        return view;
    }

    private void searchProductByName() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.search_product_by_name_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SearchProductByNameResponse nameResponse = gson.fromJson(response,SearchProductByNameResponse.class);
                search_list.clear();
                for (int i = 0 ; i <nameResponse.getData().getData().size();i++){
                    search_list.add(nameResponse.getData().getData().get(i));
                }
                if (search_list.size()==0){
                    Toast.makeText(getContext(), "No such data found!", Toast.LENGTH_SHORT).show();
                }
                searchAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error_response", error.toString());
                progressDialog.dismiss();
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
