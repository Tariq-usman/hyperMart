package com.system.user.menwain.fragments.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.home_adapters.grid_adapters.ShopItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ShopItemsListAdapter;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.home_adapters.grid_adapters.ExploreItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ExploreItemsListAdapter;
import com.system.user.menwain.adapters.home_adapters.grid_adapters.ExploreShopItemsGridAdapter;
import com.system.user.menwain.adapters.home_adapters.list_adapters.ExploreShopItemsListAdapter;
import com.system.user.menwain.responses.home.ExploreSellAllResponse;
import com.system.user.menwain.responses.home.ExploreShopeSeeAllResponse;
import com.system.user.menwain.responses.home.ShopSeeAllResponse;
import com.system.user.menwain.utils.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllItemsFragment extends Fragment implements View.OnClickListener {
    private String[] productsName = {"Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallaxy S8", "Samsung", "BlockBuster", "Gallexy M30",
            "Gallexy M30s", "Samsung s4", "Gallexy M30", "Gallexy M30s", "Samsung s4"};
    private int[] products = {R.drawable.p, R.drawable.pict, R.drawable.pictu, R.drawable.picturep,
            R.drawable.picturepi, R.drawable.picturepic, R.drawable.p, R.drawable.pict, R.drawable.pictu,
            R.drawable.picturep, R.drawable.picturepi, R.drawable.picturepic};


    private String[] productsName1 = {"Cooking oil", "Chicken", "Meat", "Butter", "Eggs", "Chocolate", "Frouts", "Carrot", "Mango", "Vegetables"};
    private int[] items = {R.drawable.coocking_oil, R.drawable.chikken, R.drawable.meat, R.drawable.butter, R.drawable.eggs, R.drawable.choclate, R.drawable.frouts,
            R.drawable.carrot, R.drawable.mango, R.drawable.vagitables};

    private RecyclerView recyclerViewAllitem;
    private LinearLayoutManager linearLayoutManager;
    private ImageView ivGridListView, ivBack, ivListGridView, ivMenu;
    private boolean isList = false;
    private Intent intent;
    private String val;
    private int val1;
    private int val2;
    Bundle bundle;
    Toolbar toolbar;
    private TextView fragTitle;
    private CardView searchViewAllItems;
    Prefrences prefrences;
    private ExploreShopItemsGridAdapter exploreShopItemsGridAdapter;
    private ExploreItemsGridAdapter exploreItemsGridAdapter;
    private ShopItemsGridAdapter shopItemsGridAdapter;
    private ProgressDialog progressDialog;
    private List<ExploreShopeSeeAllResponse.Datum> explore_shop_grid_list = new ArrayList<>();
    private List<ExploreSellAllResponse.Datum> explore_list = new ArrayList<>();
    private List<ShopSeeAllResponse.Datum> shop_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_items, container, false);
        prefrences = new Prefrences(getContext());
        customProgressDialog(getContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        searchViewAllItems = getActivity().findViewById(R.id.search_view);
        ivListGridView = getActivity().findViewById(R.id.iv_grid_list_view);
        ivListGridView.setVisibility(View.VISIBLE);
        ivListGridView.setImageResource(R.drawable.ic_list_view);
        ivListGridView.setOnClickListener(this);


        ivBack = getActivity().findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);


        bundle = this.getArguments();
        val = bundle.getString("explore", "");
        Log.i("val", String.valueOf(val));
        Log.i("val1", String.valueOf(val1));
        recyclerViewAllitem = view.findViewById(R.id.recycler_view_grid_items);
        if (val.equals("1")) {
            getExploreAndShopeSeeAllData();
            recyclerViewAllitem.setHasFixedSize(true);
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
            exploreShopItemsGridAdapter=new ExploreShopItemsGridAdapter(getContext(), explore_shop_grid_list);
            recyclerViewAllitem.setAdapter(exploreShopItemsGridAdapter);
        } else if (val.equals("2")) {
            getExploreSeeAllData();
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
            exploreItemsGridAdapter = new ExploreItemsGridAdapter(getContext(), explore_list);
            recyclerViewAllitem.setAdapter(exploreItemsGridAdapter);
        } else if (val.equals("3")) {
            getShopSeeAllData();
            recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
            shopItemsGridAdapter=new ShopItemsGridAdapter(getContext(),shop_list);
            recyclerViewAllitem.setAdapter(shopItemsGridAdapter);
        }
        return view;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                prefrences.setHomeFragStatus(0);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(),"Home").addToBackStack(null).commit();
                ivBack.setVisibility(View.INVISIBLE);
                ivListGridView.setVisibility(View.INVISIBLE);

                break;
            case R.id.iv_grid_list_view:
                if (val.equals("1")) {
                    if (isList == false) {
                        isList = true;
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreShopItemsListAdapter(getContext(), explore_shop_grid_list));

                    } else {
                        isList = false;
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreShopItemsGridAdapter(getContext(), explore_shop_grid_list));
                    }
                } else if (val.equals("2")) {
                    if (isList == false) {
                        isList = true;
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreItemsListAdapter(getContext(), explore_list));

                    } else {
                        isList = false;
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ExploreItemsGridAdapter(getContext(), explore_list));
                    }
                } else if (val.equals("3")) {
                    if (isList == false) {
                        isList = true;
                        ivListGridView.setImageResource(R.drawable.ic_grid_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewAllitem.setLayoutManager(linearLayoutManager);
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ShopItemsListAdapter(getContext(),shop_list));

                    } else {
                        isList = false;
                        ivListGridView.setImageResource(R.drawable.ic_list_view);
                        recyclerViewAllitem.setHasFixedSize(true);
                        recyclerViewAllitem.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        recyclerViewAllitem.setAdapter(null);
                        recyclerViewAllitem.setAdapter(new ShopItemsGridAdapter(getContext(), shop_list));
                    }
                }
                break;

        }

    }
    private void getExploreAndShopeSeeAllData() {
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            final Gson gson = new GsonBuilder().create();
            StringRequest request = new StringRequest(Request.Method.GET, URLs.see_all_explore_shop, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ExploreShopeSeeAllResponse seeAllResponse = gson.fromJson(response,ExploreShopeSeeAllResponse.class);
                    explore_shop_grid_list.clear();
                    for (int i = 0; i< seeAllResponse.getData().size();i++){
                        explore_shop_grid_list.add(seeAllResponse.getData().get(i));
                    }
                    exploreShopItemsGridAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("wishError",error.toString());
                    progressDialog.dismiss();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headerMap = new HashMap<>();
                    headerMap.put("Authorization","Bearer " + prefrences.getToken());
                    return headerMap;
                }
            };
            requestQueue.add(request);
        }

    private void getExploreSeeAllData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.see_all_explore, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ExploreSellAllResponse seeAllResponse = gson.fromJson(response,ExploreSellAllResponse.class);
                explore_list.clear();
                for (int i = 0; i< seeAllResponse.getData().size();i++){
                    explore_list.add(seeAllResponse.getData().get(i));
                }
                exploreItemsGridAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError",error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap = new HashMap<>();
                headerMap.put("Authorization","Bearer " + prefrences.getToken());
                return headerMap;
            }
        };
        requestQueue.add(request);
    }
    private void getShopSeeAllData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.see_all_shop, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ShopSeeAllResponse shopSeeAllResponse = gson.fromJson(response,ShopSeeAllResponse.class);
                shop_list.clear();
                for (int i = 0; i< shopSeeAllResponse.getData().size();i++){
                    shop_list.add(shopSeeAllResponse.getData().get(i));
                }
                shopItemsGridAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wishError",error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap = new HashMap<>();
                headerMap.put("Authorization","Bearer " + prefrences.getToken());
                return headerMap;
            }
        };
        requestQueue.add(request);
    }

        public void customProgressDialog(Context context){
            progressDialog = new ProgressDialog(context);
            // Setting Message
            progressDialog.setMessage("Loading...");
            // Progress Dialog Style Spinner
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // Fetching max value
        }
}
