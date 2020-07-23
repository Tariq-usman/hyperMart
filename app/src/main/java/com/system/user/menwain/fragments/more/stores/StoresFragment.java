package com.system.user.menwain.fragments.more.stores;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
import com.system.user.menwain.adapters.my_lists_adapters.AllListsAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class StoresFragment extends Fragment {
    private RecyclerView recyclerViewProductCategory;
    private StoresAdapter storesAdapter;
    private ImageView ivBackStores;
    private TextView tvTitleStores;
    private EditText mSearchStores;
    private Preferences prefrences;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private List<StoresAllBranchesResponse.Storelist.Datum> stores_list = new ArrayList<>();
    private List<StoresAllBranchesResponse.Storelist.Datum> filter_stores_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);
        prefrences = new Preferences(getContext());
        customDialog(getContext());
        ivBackStores = view.findViewById(R.id.iv_back_stores);
        mSearchStores = view.findViewById(R.id.et_search_store);
        mSearchStores.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white, 0, 0, 0);
        mSearchStores.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        mSearchStores.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        ivBackStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreStoresFragStatus(0);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MoreFragment()).addToBackStack(null).commit();
                //   tvTitleStores.setText("More");
            }
        });

        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_stores);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        storesAdapter = new StoresAdapter(getContext(), stores_list);
        recyclerViewProductCategory.setAdapter(storesAdapter);

        getAllStoreData();

        mSearchStores.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0) {
                    mSearchStores.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    //Assign your image again to the view, otherwise it will always be gone even if the text is 0 again.
                    mSearchStores.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_white, 0, 0, 0);
                }
                String query = s.toString().toLowerCase();
                filter_stores_list.clear();
                for (int i = 0; i < stores_list.size(); i++) {
                    final String text = stores_list.get(i).getName().toLowerCase();
                    if (text.contains(query)) {
                        filter_stores_list.add(stores_list.get(i));
                    }
                }
                /*if (filter_orders_list.size() == 0) {
                    Toast.makeText(getContext(), getContext().getString(R.string.no_result_found), Toast.LENGTH_SHORT).show();
                } else {*/
                storesAdapter = new StoresAdapter(getContext(), filter_stores_list);
                recyclerViewProductCategory.setAdapter(storesAdapter);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void getAllStoreData() {
        dialog.show();
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
                dialog.dismiss();
//                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                Log.e("Stores_error", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Language", prefrences.getLanguage());
                return header;
            }
        };
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
