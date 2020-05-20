package com.system.user.menwain.fragments.category;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.category_adapters.SuperCategoryAdapter;
import com.system.user.menwain.fragments.others.SearchFragment;
import com.system.user.menwain.responses.category.SuperCategoryResponse;
import com.system.user.menwain.utils.URLs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuperCategoryFragment extends Fragment {
    RecyclerView recyclerViewProductCategory;
    private ImageView mBarCodeScanner,ivSearch;
    private EditText etSearhText;
    private CardView mSearchViewCategory;
    private List<SuperCategoryResponse.SuperCategory.Datum> superCategoryList = new ArrayList<>();
    private SuperCategoryAdapter superCategoryAdapter;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private FirebaseAnalytics mFirebaseAnalytics;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_super_category,container,false);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        ivSearch = view.findViewById(R.id.iv_search_sup_cat);
        etSearhText = view.findViewById(R.id.et_search_sup_cat);
        customDialog(getContext());
        getSuperCategoryData();

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                SearchFragment fragment = new SearchFragment();
                if (etSearhText.getText().toString().trim().isEmpty() || etSearhText.getText().toString().trim() == null) {
                    Toast.makeText(getContext(), "Enter Your desire search..", Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putString("search", etSearhText.getText().toString().trim());
                    etSearhText.setText("");
                    fragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                }
            }
        });
        recyclerViewProductCategory = view.findViewById(R.id.recycler_view_category);
        recyclerViewProductCategory.setHasFixedSize(true);
        recyclerViewProductCategory.setLayoutManager(new GridLayoutManager(getContext(),3, GridLayoutManager.VERTICAL,false));
        superCategoryAdapter = new SuperCategoryAdapter(getContext(), superCategoryList);
        recyclerViewProductCategory.setAdapter(superCategoryAdapter);

        return view;
    }

    private void getSuperCategoryData() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_super_category_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SuperCategoryResponse categoryResponse = gson.fromJson(response,SuperCategoryResponse.class);
                superCategoryList.clear();
                for (int i = 0; i<categoryResponse.getSuperCategory().getData().size();i++){
                    superCategoryList.add(categoryResponse.getSuperCategory().getData().get(i));
                }
                superCategoryAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("category_erroe",error.toString());
                dialog.dismiss();
            }
        });
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
